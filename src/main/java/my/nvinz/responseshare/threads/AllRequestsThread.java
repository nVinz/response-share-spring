package my.nvinz.responseshare.threads;

import my.nvinz.responseshare.data.Request;
import my.nvinz.responseshare.dataservice.InternalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

public class AllRequestsThread extends Thread {

    @Autowired
    private MainThread mainThread;
    @Autowired
    private ActiveRequestsThread activeRequestsThread;
    private InternalService internalService;
    private List<Request> allRequests = new LinkedList<>();

    @PostConstruct
    private void postConstruct() {
        internalService = mainThread.getInternalService();
    }

    @Override
    public void run() {
        while (mainThread.isAlive()) {

        }
    }
}
