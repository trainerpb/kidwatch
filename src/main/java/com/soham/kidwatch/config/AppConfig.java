package com.soham.kidwatch.config;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soham.kidwatch.services.ImageUtils;

@Configuration
public class AppConfig {
	private static int width,height;
	
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
	@Bean
	public Rectangle recordingWindow() {
		Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
		width=size.width;
		height=size.height;
		return new Rectangle(size);
	}
	@Value("${capture.interval:60000}")
	private long screenCaptureIntervalMillis;
	public long getScreenCaptureIntervalMillis() {
		return screenCaptureIntervalMillis;
	}
	public void setScreenCaptureIntervalMillis(long screenCaptureIntervalMillis) {
		this.screenCaptureIntervalMillis = screenCaptureIntervalMillis;
	}
	public static  String _URL_SELF=null;
	public static int PORT=6000;
	public static String getURLStreamApi(HttpServletRequest request) {
		return request.getServletContext().getContextPath()+"/streamscreen";
	}
	public static String getURLStreamScreen(HttpServletRequest request) {
		return request.getContextPath()+"/stream";
	}
	public static String getURL_SelfContext(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = ImageUtils.getLocalAddresses().getHostAddress(); //request.getServerName()
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();  // includes leading forward slash

		String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;
		return resultPath;
	}
}
