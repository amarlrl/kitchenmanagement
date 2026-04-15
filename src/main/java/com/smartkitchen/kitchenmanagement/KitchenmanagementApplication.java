package com.smartkitchen.kitchenmanagement;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KitchenmanagementApplication {

	//no need of this actually still if u want to do any changes tehn use  this to make any configration changes
	@Bean
	public JavaMailSender mailSender(Environment env) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(env.getProperty("spring.mail.host", "localhost"));
		sender.setPort(Integer.parseInt(env.getProperty("spring.mail.port", "25")));
		sender.setUsername(env.getProperty("spring.mail.username", ""));
		sender.setPassword(env.getProperty("spring.mail.password", ""));

		Properties props = sender.getJavaMailProperties();
		props.put("mail.transport.protocol", env.getProperty("spring.mail.protocol", "smtp"));
		props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth", "false"));
		props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", "false"));

		String trust = env.getProperty("spring.mail.properties.mail.smtp.ssl.trust", "");
		if (trust != null && !trust.isBlank()) {
			props.put("mail.smtp.ssl.trust", trust);
		}

		return sender;
	}

	public static void main(String[] args) {
		SpringApplication.run(KitchenmanagementApplication.class, args);
	}

}
