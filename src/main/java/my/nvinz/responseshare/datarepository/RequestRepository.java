package my.nvinz.responseshare.datarepository;

import my.nvinz.responseshare.data.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, String> {
}
