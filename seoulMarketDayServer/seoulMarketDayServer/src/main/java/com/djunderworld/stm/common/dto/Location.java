package com.djunderworld.stm.common.dto;

/**
 * 
 * GPS 정보 도메인 클래스
 * 
 * 경도, 위도 정보
 * 
 * @author dongjooKim
 */
public class Location {
	private double longitude;
	private double latitude;

	public Location() {
		super();
	}

	public Location(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
