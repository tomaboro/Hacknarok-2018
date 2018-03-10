package pl.hacknarok.positivedevs.runit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class RunitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunitApplication.class, args);
	}
}
