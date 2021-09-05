import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.voilaf")
public class CustomSpringBootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomSpringBootStarterApplication.class, args);
	}

}
