package my.nvinz.responseshare.data;

import javax.persistence.*;

@Entity
@Table(name = "rs_template")
public class Template {

    @Id
    @Column(name="template_id")
    private String templateId;

    @ManyToOne
    @JoinColumn(name = "request_method_id")
    private RequestMethod requestMethod;

    @ManyToOne
    @JoinColumn(name = "request_language_id")
    private RequestMethod requestLanguage;

    private String description;
}
