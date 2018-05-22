package ua.provectus.public_transport;

import org.omg.PortableServer.POA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.provectus.public_transport.model.*;
import ua.provectus.public_transport.parser.Parser;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
