package my.nvinz.responseshare.data;

import my.nvinz.responseshare.data.enums.RequestLanguageType;

import javax.persistence.*;

@Entity
@Table(name = "rs_request_language")
public class RequestLanguage {

    @Id
    @Column(name="request_language_id")
    private String requestLanguageId;

    @Enumerated(EnumType.STRING)
    private RequestLanguageType requestLanguage;

}
