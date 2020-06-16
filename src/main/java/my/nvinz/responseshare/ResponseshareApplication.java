package my.nvinz.responseshare;

import my.nvinz.responseshare.threads.MainThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ResponseshareApplication {

	@Autowired
	private MainThread mainThread;

	public static void main(String[] args) {
		SpringApplication.run(ResponseshareApplication.class, args);
	}

	@PostConstruct
	private void postConstruct() {
		System.out.println("Threads starting...");
		mainThread.start();
	}

}
