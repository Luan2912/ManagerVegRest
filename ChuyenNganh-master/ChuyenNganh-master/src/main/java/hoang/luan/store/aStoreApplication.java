package hoang.luan.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class aStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(aStoreApplication.class, args);
	}

}
