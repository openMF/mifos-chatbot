package org.mifos.chatbot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class MifosChatbotApplication {
	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext();
		SpringApplication.run(MifosChatbotApplication.class, args);
	}
}
