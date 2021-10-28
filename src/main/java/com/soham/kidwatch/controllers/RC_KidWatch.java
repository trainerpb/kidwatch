package com.soham.kidwatch.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.soham.kidwatch.services.ScreenCaptureService;

@RestController
public class RC_KidWatch {
	@Autowired
	ScreenCaptureService screenCaptureService;

	@GetMapping("/streamscreen")
	@CrossOrigin(origins = "*")
	public SseEmitter streamScreen() {
		SseEmitter emitter = new SseEmitter();
		ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
		sseMvcExecutor.execute(() -> {
			try {
				screenCaptureService.streamCapture(emitter);
			} catch (Exception ex) {
				ex.printStackTrace();
				emitter.completeWithError(ex);
			}finally {
				sseMvcExecutor.shutdown();
			}
		});
		return emitter;
	}

}
