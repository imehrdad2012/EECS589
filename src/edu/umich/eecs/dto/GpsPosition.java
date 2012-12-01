package edu.umich.eecs.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GpsPosition {
	
	@Column(name="GPS_LATITUDE", nullable = true)
	private double latitude = 0;
	@Column(name="GPS_LONGITUDE", nullable = true)
	private double longitude = 0;
	@Column(name="GPS_STDDEV", nullable = true)
	private double stdDev = 0;
	//
	// This is the number of times this specific cell was seen in the GSM data
	// and we found appropriate GPS data for it.
	//
	@Column(name="GPS_SIGHTINGS", nullable = true)
	private int countSightings = 0;
	
	public GpsPosition(double latitude, double longitude, double stdDev) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.stdDev = stdDev;
	}
	
	GpsPosition() {}
	
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
	public double getStdDev() {
		return stdDev;
	}
	public void setStdDev(double stdDev) {
		this.stdDev = stdDev;
	}

	public int getCountSightings() {
		return countSightings;
	}

	public void setCountSightings(int countSightings) {
		this.countSightings = countSightings;
	}
	
	
	
	
}
