package my.nvinz.responseshare.data;

import javax.persistence.*;

@Entity
@Table(name = "rs_request")
public class Request {

    @Id
    private String id;

    private boolean active;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "request_method_id")
    private RequestMethod requestMethod;

    @ManyToOne
    @JoinColumn(name = "request_language_id")
    private RequestMethod requestLanguage;

    @Column(columnDefinition="TEXT")
    private String requestSchema;

    @Column(columnDefinition="TEXT")
    private String responseSchema;

    @Column(columnDefinition="TEXT")
    private String hardcodeResponse;

    public void update(Request request) {
        if (request.getTemplate() != null) this.setTemplate(request.getTemplate());
        if (request.getRequestSchema() != null) this.setRequestSchema(request.getRequestSchema());
        if (request.getResponseSchema() != null) this.setResponseSchema(request.getResponseSchema());
        if (request.getHardcodeResponse() != null) this.setHardcodeResponse(request.getHardcodeResponse());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public RequestMethod getRequestLanguage() {
        return requestLanguage;
    }

    public void setRequestLanguage(RequestMethod requestLanguage) {
        this.requestLanguage = requestLanguage;
    }

    public String getRequestSchema() {
        return requestSchema;
    }

    public void setRequestSchema(String requestSchema) {
        this.requestSchema = requestSchema;
    }

    public String getResponseSchema() {
        return responseSchema;
    }

    public void setResponseSchema(String responseSchema) {
        this.responseSchema = responseSchema;
    }

    public String getHardcodeResponse() {
        return hardcodeResponse;
    }

    public void setHardcodeResponse(String hardcodeResponse) {
        this.hardcodeResponse = hardcodeResponse;
    }
}
