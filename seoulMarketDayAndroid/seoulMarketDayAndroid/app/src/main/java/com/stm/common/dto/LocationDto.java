package com.stm.common.dto;

import java.io.Serializable;

/**
 * GPS 정보 data transfer object 클래스
 *
 * 경도, 위도, 현재위치 정보
 */
public class LocationDto implements Serializable{
    public static final String METER_UNIT ="M";
    public static final String KILO_METER_UNIT ="KM";

    private double latitude;
    private double longitude;
    private String currentAddress;

    public LocationDto() {
    }

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationDto locationDto = (LocationDto) o;

        if (Double.compare(locationDto.latitude, latitude) != 0) return false;
        if (Double.compare(locationDto.longitude, longitude) != 0) return false;
        return currentAddress != null ? currentAddress.equals(locationDto.currentAddress) : locationDto.currentAddress == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currentAddress != null ? currentAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", currentAddress='" + currentAddress + '\'' +
                '}';
    }
}
