package com.soham.kidwatch.services;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.image.MultiResolutionImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.soham.kidwatch.config.AppConfig;
import com.soham.kidwatch.models.VO_ScreenResponse;

@Service
public class ScreenCaptureService {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AppConfig appConfig;

	private Robot robot;

	public ScreenCaptureService() throws AWTException {
		super();
		this.robot = new Robot();
		logger.debug("initlized Capture::");

	}

	protected Image captureScreen() {
		Image nativeResImage;
		MultiResolutionImage mrImage = robot.createMultiResolutionScreenCapture(appConfig.recordingWindow());
		List<Image> resolutionVariants = mrImage.getResolutionVariants();
		if (resolutionVariants.size() > 1) {
			nativeResImage = resolutionVariants.get(1);
		} else {
			nativeResImage = resolutionVariants.get(0);
		}
		logger.debug("Captured Screen::");

		return nativeResImage;

	}

	public void streamCapture(SseEmitter emitter) {

		
			while (true) {
				try {
					logger.debug("Initiating capture::");
					Image image=captureScreen();
					String strBase64Image = ImageUtils.toBae64(image);
					logger.debug("Length of caoture={}", null != strBase64Image ? strBase64Image.length() : -1);
					
					emitter.send(new VO_ScreenResponse(strBase64Image));
					delay();
				} catch (Throwable e) {
					e.printStackTrace();
					logger.error("Error streaming::", e);
					throw new RuntimeException(e);
				}
			}
		

	}

	private void delay() {
		try {
			TimeUnit.MILLISECONDS.sleep(appConfig.getScreenCaptureIntervalMillis());
		} catch (InterruptedException e) {
			logger.error("Error during capture interval::Ignore", e);
		}

	}

}
