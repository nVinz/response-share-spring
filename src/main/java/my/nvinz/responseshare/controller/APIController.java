package my.nvinz.responseshare.controller;

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
     * @param stringRequest request
     * @return HTTP response or error
     */
    @RequestMapping(value = "/{id}/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRequestRqSchema(@PathVariable("id") String id, @RequestBody String stringRequest) {
        if (APIService.validateRqToJsonSchema(id, stringRequest)) {
            return new ResponseEntity<String>(APIService.createRsJson(id).toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("{ \"PARSING ERROR\": \"" + id + " - response schema\" }", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
