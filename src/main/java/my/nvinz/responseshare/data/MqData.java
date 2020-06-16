package my.nvinz.responseshare.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rs_mq_data")
public class MqData {

    @Id
    @Column(name="mq_data_id")
    private String id;

    private Integer listenerCount;

    private String ip;
    private String port;

    private String mqInName;
    private String mqOutName;
}
