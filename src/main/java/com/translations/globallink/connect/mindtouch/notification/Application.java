package com.translations.globallink.connect.mindtouch.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.err.println("application.properties is missing.");
			System.err.println("Bye!");
		} else {
			String filePath = "-Dspring.config.location=" + args[0];
			SpringApplication.run(Application.class, filePath);
		}
	}

}