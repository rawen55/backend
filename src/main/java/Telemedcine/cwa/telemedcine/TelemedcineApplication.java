package Telemedcine.cwa.telemedcine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = "Telemedcine.cwa.telemedcine.repositories") 
@EntityScan(basePackages = "Telemedcine.cwa.telemedcine.model")  
@SpringBootApplication
public class TelemedcineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelemedcineApplication.class, args);
	}

}
