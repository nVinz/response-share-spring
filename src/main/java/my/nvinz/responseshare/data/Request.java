package my.nvinz.responseshare.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import my.nvinz.responseshare.data.enums.RequestLanguageType;
import my.nvinz.responseshare.data.enums.RequestMethodType;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "rs_request")
public class Request {

    @Id
    private String id;

    // Settings
    private String description;
    @Column(nullable = true)
    private String responseIP;
    @Column(nullable = true)
    private Integer delay;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "mq_data_id")
    private Template mqData;

    @Enumerated(EnumType.STRING)
    private RequestMethodType requestMethod;

    @Enumerated(EnumType.STRING)
    private RequestLanguageType requestLanguage;

    @Column(columnDefinition="TEXT")
    private String requestSchema;

    @Column(columnDefinition="TEXT")
    private String responseSchema;

    @Column(columnDefinition="TEXT")
    private String hardcodedResponse;

    // Data
    @Column(nullable = true)
    private Boolean active;
    @Column(nullable = true)
    private String Status;
    @Column(nullable = true)
    private ZonedDateTime lastCall;
    @Column(nullable = true)
    private Integer callCount;
    @Column(nullable = true)
    private Integer errors;
    @Column(columnDefinition="TEXT")
    private String log;


    public void update(Request request) {
        if (request.getDescription() != null) this.setDescription(request.getDescription());
        if (request.getTemplate() != null) this.setTemplate(request.getTemplate());
        if (request.getRequestSchema() != null) this.setRequestSchema(request.getRequestSchema());
        if (request.getResponseSchema() != null) this.setResponseSchema(request.getResponseSchema());
        if (request.getHardcodedResponse() != null) this.setHardcodedResponse(request.getHardcodedResponse());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseIP() {
        return responseIP;
    }

    public void setResponseIP(String responseIP) {
        this.responseIP = responseIP;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Template getMqData() {
        return mqData;
    }

    public void setMqData(Template mqData) {
        this.mqData = mqData;
    }

    public RequestMethodType getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethodType requestMethod) {
        this.requestMethod = requestMethod;
    }

    public RequestLanguageType getRequestLanguage() {
        return requestLanguage;
    }

    public void setRequestLanguage(RequestLanguageType requestLanguage) {
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

    public String getHardcodedResponse() {
        return hardcodedResponse;
    }

    public void setHardcodedResponse(String hardcodedResponse) {
        this.hardcodedResponse = hardcodedResponse;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getLastCall() {
        return lastCall;
    }

    public void setLastCall(ZonedDateTime lastCall) {
        this.lastCall = lastCall;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Integer getErrors() {
        return errors;
    }

    public void setErrors(Integer errors) {
        this.errors = errors;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
