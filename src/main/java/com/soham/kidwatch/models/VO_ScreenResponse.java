package com.soham.kidwatch.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.soham.kidwatch.config.AppConfig;

public class VO_ScreenResponse {

	private String data;
	private int width;
	private int height;
	private LocalDateTime now;
	public VO_ScreenResponse(String data) {
		super();
		this.data = data;
		now=LocalDateTime.now();
		this.width=AppConfig.getWidth();
		this.height=AppConfig.getHeight();
		
	}
	@JsonGetter("image")
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public LocalDateTime getNow() {
		return now;
	}
	public void setNow(LocalDateTime now) {
		this.now = now;
	}
	
	
	
}
