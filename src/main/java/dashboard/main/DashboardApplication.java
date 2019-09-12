package dashboard.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {
	"dashboard.controllers",
	"dashboard.services",
	"dashboard.exceptions"
})
@EntityScan("dashboard.entities")
@EnableJpaRepositories("dashboard.repositories")
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

}
