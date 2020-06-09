package my.nvinz.responseshare.dataservice;

import my.nvinz.responseshare.data.Request;

import java.util.List;

public interface RequestService {

    /***
     * Gets all requests from DB
     * @return list of Requests
     */
     List<Request> getAllRequests();

    /***
     * Gets specified Request from DB
     * @param id request name
     * @return Request if it exists, else null
     */
     Request getRequestById(String id);
}
