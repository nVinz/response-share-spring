package my.nvinz.responseshare.controller;

import my.nvinz.responseshare.threads.ActiveRequestsThread;
import my.nvinz.responseshare.threads.MainThread;
import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.dataservice.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(maxAge = 3600)
public class APIController {

    @Autowired
    private APIService APIService;
    @Autowired
    private ActiveRequestsThread activeRequestsThread;

    @Autowired
    public APIController(MainThread mainThread) {
        this.APIService = mainThread.getAPIService();
    }

    /***
     * Test REST request to determine ip
     * @param request test param
     */
    @RequestMapping(path = "/ip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getIp(HttpServletRequest request) {
        System.out.println(request.getRemoteAddr());
    }

    /***
     * REST GET response from request
     * @param id Request id
     * @param requestBody request
     * @return HTTP response or error
     */
    @RequestMapping(value = "/{id}/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRequestRqSchema(@PathVariable("id") String id, @RequestBody String requestBody) {

        Request storedRequest = APIService.getRequestById(id);

        if (storedRequest != null) {
            if (!APIService.isRequestBodyValid(requestBody)) {
                return new ResponseEntity<String>("{ \"ERROR\": \"Request to '" + id + "' is not JSON or XML\" }", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (APIService.validateRequestBodyToSchema(storedRequest, requestBody)) {
                APIService.updateCallData(storedRequest);
                return new ResponseEntity<>(activeRequestsThread.getResponse(storedRequest), HttpStatus.OK);
            }
            return new ResponseEntity<String>("{ \"PARSING ERROR\": \"'" + id + "' - request/response schema is empty or not valid\" }", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("{ \"ERROR\": \"Request '" + id + "' not found\" }", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
