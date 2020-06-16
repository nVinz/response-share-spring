package my.nvinz.responseshare.dataservice;

import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.datarepository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InternalService implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    /***
     * Gets all requests from DB
     * @return list of Requests
     */
    @Override
    public List<Request> getAllRequests() {
        List<Request> requests = new LinkedList<>();
        requestRepository.findAll().forEach(requests::add);
        return requests;
    }

    /***
     * Gets specified Request from DB
     * @param id Request name
     * @return Request if it exists, else null
     */
    @Override
    public Request getRequestById(String id) {
        return requestRepository.findById(id).orElse(null);
    }

    /***
     * Gets amount of all Requests in DB
     * @return count
     */
    public long getRequestCount() {
        return requestRepository.count();
    }

    /***
     * Gets active requests from DB
     * @return list of active Requests
     */
    public List<Request> getActiveRequests() {
        return getAllRequests().stream()
                .filter(Objects::nonNull)
                .filter(Request::getActive)
                .collect(Collectors.toList());
    }

    /***
     * Gets all Requests names from DB
     * @return list of Requests
     */
    public List<String> getAllRequestsNames() {
        List<String> names = new LinkedList<>();
        requestRepository.findAll().forEach(request -> names.add(request.getId()));
        return names;
    }

    /***
     * Adds or updates Request in DB
     * @param request if exists in db then updates it
     * else creates new
     */
    public void addRequest(Request request) {
        Request storedRequest = getRequestById(request.getId());
        if (storedRequest == null) {
            requestRepository.save(request);
        }
        else {
            storedRequest.update(request);
            requestRepository.save(storedRequest);
        }
    }

    /***
     * Updates only request schema
     * @param id Request name
     * @param schema new schema to save in DB
     */
    public void addRequestRqSchema(String id, String schema) {
        Request request = getRequestById(id);
        Objects.requireNonNull(request);

        request.setRequestSchema(schema);

        addRequest(request);
    }

    /***
     * Updates only response schema
     * @param id Request name
     * @param schema new schema to save in DB
     */
    public void addRequestRsSchema(String id, String schema) {
        Request request = getRequestById(id);
        Objects.requireNonNull(request);

        request.setResponseSchema(schema);

        addRequest(request);
    }

    /***
     * Removeы Request if it exists in DB
     * @param id Request name
     * TODO check if it exists instead of try/catch
     */
    public void deleteRequestById(String id) {
        try {
            requestRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            System.out.println("Request not found in db: " + exception.getMessage());
        }
    }


}
