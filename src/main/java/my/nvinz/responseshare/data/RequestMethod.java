package my.nvinz.responseshare.data;

import my.nvinz.responseshare.data.enums.RequestMethodType;

import javax.persistence.*;

@Entity
@Table(name = "rs_request_method")
public class RequestMethod {

    @Id
    @Column(name="request_method_id")
    private String requestMethodId;

    @Enumerated(EnumType.STRING)
    private RequestMethodType requestMethodType;

}
