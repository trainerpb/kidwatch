package com.soham.kidwatch.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.WriterException;
import com.soham.kidwatch.services.ImageUtils;

@Controller
public class MvcController {

	@GetMapping("/home")
	public ModelAndView homeView(HttpServletRequest request) throws WriterException, IOException {
		String url=com.soham.kidwatch.config.AppConfig.getURL_SelfContext(request)+"/stream";
		
		
		ModelAndView mv= new ModelAndView("home");
		mv.addObject("qr", ImageUtils.generateQRcodeBase64(url));
		return mv;
	}
	
	@GetMapping("/stream")
	public ModelAndView streamView() {
		return new ModelAndView("stream");
	}
}
