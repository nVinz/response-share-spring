package my.nvinz.responseshare.threads;

import my.nvinz.responseshare.dataservice.APIService;
import my.nvinz.responseshare.dataservice.InternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainThread extends Thread {

    @Autowired
    private InternalService internalService;
    @Autowired
    private APIService apiService;
    @Autowired
    private ActiveRequestsThread activeRequestsThread;

    // TODO check for Requests updates
    @Override
    public void run(){
        activeRequestsThread.start();
        System.out.println("Threads started.");

        while (true) {
            try {
                sleep(600000);

                // if ActiveRequestsThread id dead, but new request became active
                if (!activeRequestsThread.isAlive()) {
                    if (activeRequestsThread.isAnyActive()) {
                        activeRequestsThread.start();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public InternalService getInternalService() {
        return internalService;
    }

    public APIService getAPIService() {
        return apiService;
    }

    public ActiveRequestsThread getActiveRequestsThread() {
        return activeRequestsThread;
    }
}
