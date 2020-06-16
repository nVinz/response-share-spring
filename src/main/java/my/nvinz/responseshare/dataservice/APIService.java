package my.nvinz.responseshare.dataservice;

import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.datarepository.RequestRepository;
import my.nvinz.responseshare.tools.JsonUtils;
import my.nvinz.responseshare.tools.XmlUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class APIService implements RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private InternalService internalService;

    /***
     * Gets all requests from DB
     * @return list of Requests
     */
    @Override
    public List<Request> getAllRequests() {
        return internalService.getAllRequests();
    }

    /***
     * Gets specified Request from DB
     * @param id Request name
     * @return Request if it exists, else null
     */
    @Override
    public Request getRequestById(String id) {
        return internalService.getRequestById(id);
    }

    /***
     * Update data when Request called
     * Also set status to Active
     * @param storedRequest Request id in DB
     */
    public void updateCallData(Request storedRequest){
        ZonedDateTime currentTime = ZonedDateTime.now();

        storedRequest.setLastCall(currentTime);
        storedRequest.setActive(true);

        if (storedRequest.getCallCount() != null) {
            storedRequest.setCallCount(storedRequest.getCallCount() + 1);
        }
        else {
            storedRequest.setCallCount(1);
        }

        internalService.addRequest(storedRequest);
    }

    /***
     * Checks if given request is JSON language
     * @param requestBody request body
     * @return is JSON or not
     */
    public boolean isRequestBodyValid(String requestBody) {
        if (JsonUtils.isJson(requestBody)) {
            return true;
        }
        else if (XmlUtils.isXml(requestBody)) {
            return true;
        }
        return false;
    }

    /***
     * Checks if given request valid to schema (JSON)
     * @param storedRequest Request in DB
     * @param requestBody request to Request
     * @return is scheme valid or not
     */
    public boolean validateRequestBodyToSchema(Request storedRequest, String requestBody) {
        return JsonUtils.validateRequestBodyToSchema(storedRequest, requestBody);
    }

    /***
     * Creates response from json schema
     * @param storedRequest Request id DB
     * @return json response
     */
    public JSONObject createRsJson(Request storedRequest) {
        // If Request doesn't have a response schema id DB
        if (storedRequest.getResponseSchema() == null) {
            JSONObject response = new JSONObject();
            response.put("ERROR", "response schema is empty");
            return response;
        }
        // Create Map of all json attributes from Request's response json schema
        Map<String, Object> elementsMap = JsonUtils.parseRsSchemaJson(storedRequest);
        // Create json with attributes and values
        return JsonUtils.setRsValuesJson(elementsMap);
    }
}
