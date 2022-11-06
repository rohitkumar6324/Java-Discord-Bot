package com.rohit.javabot;

import com.rohit.javabot.events.MessageEvents;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JavabotApplication implements CommandLineRunner {

	@Autowired
	private Environment env;

	@Autowired
	private ApplicationContext context;

	@Bean("jda-builder")
	public JDABuilder ReaderDiscordApi(){
		return JDABuilder.createDefault(env.getProperty("bot.api"));
	}

	public void run(String... args) throws InterruptedException {
		context.getBean(MessageEvents.class).begin();
	}


	public static void main(String[] args) throws InterruptedException {
		SpringApplication springApplication = new SpringApplication(JavabotApplication.class);
		springApplication.run(args);
	}
}
