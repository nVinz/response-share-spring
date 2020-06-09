package my.nvinz.responseshare.dataservice;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.datarepository.RequestRepository;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.everit.json.schema.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param id request name
     * @return Request if it exists, else null
     */
    @Override
    public Request getRequestById(String id) {
        return internalService.getRequestById(id);
    }

    /***
     * Checks if given request valid to schema (JSON)
     * @param id Request name
     * @param stringRequest Request's request
     * @return existence
     */
    public boolean validateRqToJsonSchema(String id, String stringRequest) {
        Request storedRequest = getRequestById(id);
        JSONObject jsonRequest = new JSONObject(stringRequest);
        JSONObject requestRawSchema = new JSONObject(new JSONTokener(storedRequest.getRequestSchema()));
        Schema requestSchema = SchemaLoader.load(requestRawSchema);

        try {
            requestSchema.validate(jsonRequest);
            return true;
        } catch (ValidationException e) {
            System.out.println("[VALIDATION ERROR]: [" + id + "] - request schema: " + e.getMessage());
            return false;
        }
    }

    /***
     * Creates response from json schema
     * @param id Response id
     * @return json response
     */
    public JSONObject createRsJson(String id) {
        // Get Response from DB
        Request storedRequest = getRequestById(id);
        // Create Map of all json attributes from Request's response json schema
        Map<String, Object> elementsMap = parseRsSchemaJson(storedRequest);
        // Create json with attributes and values
        return setRsValuesJson(elementsMap);
    }

    /***
     * Fills json object with all attributes and values from response schema
     * @param elementsMap Map from parseRsSchemaJson(Request request)
     * @return json response
     */
    private JSONObject setRsValuesJson(Map<String, Object> elementsMap) {
        Iterator<Map.Entry<String, Object>> fieldsIterator = elementsMap.entrySet().iterator();
        JSONObject response = new JSONObject();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, Object> field = fieldsIterator.next();

            // Has nested attribute(s)
            if (field.getValue() instanceof Map) {
                response.put(field.getKey(), setRsValuesJson((Map<String, Object>) field.getValue()));
            }
            // Array if example values in attribute
            else if (field.getValue() instanceof List) {
                List<Object> exampleValues = (List) field.getValue();
                int index = new Random().nextInt(exampleValues.size());
                response.put(field.getKey(), exampleValues.get(index));
            }
            // Simple attribute
            else {
                response.put(field.getKey(), field.getValue());
            }
        }

        return response;
    }

    /***
     * Parses response json schema from existing request
     * and creates Map with attribute-value key set
     * @param request Request in DB
     * @return map of all json schema values
     * HashMap in map values for complex values
     */
    private Map<String, Object> parseRsSchemaJson(Request request) {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        Map<String, Object> elementsMap = new HashMap<>();

        try {
            JsonNode rootNode = objectMapper.readTree(request.getResponseSchema());
            elementsMap = getAttributesJsonSchema(rootNode.get("properties"));
        } catch (Exception e) {
            System.out.println("[PARSING ERROR]: [" + request.getId() + "] - response schema: " + e.getMessage());
        }

        return elementsMap;
    }

    /***
     * Creates Map with all attributes and values from json schema
     * @param rootNode main json node
     * @return Map with attribute-value key set
     */
    private Map<String, Object> getAttributesJsonSchema(JsonNode rootNode) {
        Map<String, Object> elementsMap = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();

            // Array of attributes
            if (field.getValue().get("type").toString().contains("array")) {
                List<Map<String, Object>> objectArray = new ArrayList<>();
                JsonNode itemsNode = field.getValue().get("items").get("properties");
                objectArray.add(getAttributesJsonSchema(itemsNode));
                elementsMap.put(field.getKey(), objectArray);
            }
            // Complex attribute
            else if (field.getValue().get("type").toString().contains("object")) {
                elementsMap.put(field.getKey(), getAttributesJsonSchema(field.getValue().get("properties")));
            }
            // Simple attribute
            else {
                if (field.getValue().get("examples") != null) {
                    List<String> examples = Arrays.asList(
                            field.getValue().get("examples").toString()
                                .replace("[", "")
                                .replace("]", "")
                                .split(","));
                    elementsMap.put(field.getKey(), examples);
                }
                else {
                    elementsMap.put(field.getKey(), field.getValue().get("default"));
                }
            }
        }

        return elementsMap;
    }
}
