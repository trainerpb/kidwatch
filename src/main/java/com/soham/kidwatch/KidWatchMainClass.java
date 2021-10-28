package com.soham.kidwatch;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.soham.kidwatch.config.AppConfig;

@SpringBootApplication
public class KidWatchMainClass {
	private static Logger logger=LoggerFactory.getLogger(KidWatchMainClass.class);
	public static void main(String[] args) {
		SpringApplication.run(KidWatchMainClass.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws InterruptedException, IOException {

		Runtime.getRuntime().exec("cmd /c start " + getURL()).waitFor();
	}

	public static String getURL() {
		AppConfig.PORT = getAvailablePort();
		
		String url = String.format("http://localhost:%d/home",AppConfig.PORT);
		AppConfig._URL_SELF=url;
		return url;
	}

	public static int getAvailablePort() {
		int port = 8080;
		while (port < 60000) {
			try(Socket s = new Socket("localhost", port)) {
				
				
				
				return port;
			} catch (Exception e) {
				port++;
				logger.error("Ignore error::",e);
				
			}
			
		}
		
		return -1;
	}
}
