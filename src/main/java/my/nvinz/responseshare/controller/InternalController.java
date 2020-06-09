package my.nvinz.responseshare.controller;

import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.dataservice.InternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
@CrossOrigin(maxAge = 3600)
public class InternalController {

    @Autowired
    InternalService internalService;

    @RequestMapping(path = "/requests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Request> getAllRequests() {
        return internalService.getAllRequests();
    }

    @RequestMapping(path = "/requests_names", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllRequestsNames() {
        return internalService.getAllRequestsNames();
    }

    @RequestMapping(path = "/requests/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Request getRequestById(@PathVariable("id") String id) {
        return internalService.getRequestById(id);
    }


    @RequestMapping(value = "/requests", method = RequestMethod.POST)
    public @ResponseBody void addRequest(@RequestBody Request request) {
        internalService.addRequest(request);
    }

    @RequestMapping(value = "/requests/{id}/rq_schema", method = RequestMethod.POST)
    public @ResponseBody void addRequestRqSchema(@PathVariable("id") String id, @RequestBody String schema) {
        internalService.addRequestRqSchema(id, schema);
    }

    @RequestMapping(value = "/requests/{id}/rs_schema", method = RequestMethod.POST)
    public @ResponseBody void addRequestRsSchema(@PathVariable("id") String id, @RequestBody String schema) {
        internalService.addRequestRsSchema(id, schema);
    }


    @RequestMapping(value = "/requests/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteRequestById(@PathVariable("id") String id) {
        internalService.deleteRequestById(id);
    }


}
