package EurekaRegister.EurekaServer_Pawscare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerPawscareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerPawscareApplication.class, args);
	}

}
