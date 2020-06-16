package my.nvinz.responseshare.threads;

import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.dataservice.InternalService;
import my.nvinz.responseshare.tools.CyclicCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ActiveRequestsThread extends Thread {

    @Autowired
    private MainThread mainThread;
    private InternalService internalService;
    private List<Request> activeRequests = new LinkedList<>();
    private Map<String, CyclicCollection<String>> responsesPool = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        internalService = mainThread.getInternalService();
    }

    @Override
    public void run() {
        while(isAnyActive()) {
            activeRequests = getActiveRequests();
            responsesPool = createResponsesPool(activeRequests);

            try {
                sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * Create a List of all active requests
     * @return it
     */
    private List getActiveRequests() {
        return internalService.getActiveRequests();
    }

    /***
     * Check if there is any active request id DB
     * @return existence of it
     */
    public boolean isAnyActive() {
        return getActiveRequests().size() > 0;
    }

    /***
     *
     * @param request
     * @return
     */
    public String getResponse(Request request) {
        if (responsesPool.containsKey(request.getId())) {
           CyclicCollection<String> requestResponses = responsesPool.get(request.getId());
            return requestResponses.iterator().next();
        }
        else {
            return null;
        }
    }

    /***
     *
     * @param requests
     * @return
     */
    private Map createResponsesPool(List<Request> requests) {
        Map<String, CyclicCollection<String>> responses = new HashMap<>();

        requests.forEach(request -> responses.put(request.getId(), createResponses(request, 3)));

        return responses;
    }

    /***
     * Creates cycled collection of responses in Strings
     * @param request Request from DB
     * @return collection of responses
     */
    private CyclicCollection createResponses(Request request, int count) {
        List<String> responsesList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            responsesList.add(mainThread.getAPIService().createRsJson(request).toString());
        }

        return new CyclicCollection(responsesList);
    }
}
