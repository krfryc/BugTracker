package pl.kfryc.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import pl.kfryc.bugtracker.config.StorageProperties;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(StorageProperties.class)
public class BugTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackerApplication.class, args);
	}

}
