package dashboard.main;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages= {
	"dashboard.controllers",
	"dashboard.services",
	"dashboard.exceptions",
	"dashboard.main"
})
@EntityScan("dashboard.entities")
@EnableJpaRepositories("dashboard.repositories")
@EnableTransactionManagement
@MapperScan(basePackages = {"dashboard.repositories"}, annotationClass = Mapper.class)
public class DashboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}
}
