package my.nvinz.responseshare.data;

import my.nvinz.responseshare.data.enums.RequestLanguageType;
import my.nvinz.responseshare.data.enums.RequestMethodType;

import javax.persistence.*;

@Entity
@Table(name = "rs_template")
public class Template {

    @Id
    @Column(name="template_id")
    private String templateId;

    @Enumerated(EnumType.STRING)
    private RequestMethodType requestMethod;

    @Enumerated(EnumType.STRING)
    private RequestLanguageType requestLanguage;

    private String description;
}
