package edu.umich.eecs;

import java.util.Collection;

import edu.umich.eecs.dto.GpsPosition;

public class LatitudeLongitude {
	private double latitude;
	private double longitude;
	public LatitudeLongitude(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LatitudeLongitude(GpsPosition gps) {
		this.latitude = gps.getLatitude();
		this.longitude = gps.getLongitude();
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
	
	public String toString() {
		return "(" + latitude + " " + longitude + ")";
	}
	
	/**
	 * Courtesy of Sean at http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double distanceInMeters(LatitudeLongitude p1, LatitudeLongitude p2) {
		double lat1 = p1.getLatitude();
		double lng1 = p1.getLongitude();
		double lat2 = p2.getLatitude();
		double lng2 = p2.getLongitude();
		
		double earthRadius = 6367.5 * 1000; // in km times 10^3
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
				* Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;		
	}
	
	/**
	 * http://stackoverflow.com/questions/6671183/calculate-the-center-point-of-multiple-latitude-longitude-coordinate-pairs
	 * @return
	 */
	public static LatitudeLongitude average(Collection<LatitudeLongitude> points) {
		/**
		 * Convert lat/lon to Cartesian coordinates for each location.
			X = cos(lat) * cos(lon)
			Y = cos(lat) * sin(lon)
			Z = sin(lat)
			
			Compute average x, y and z coordinates.
			x = (x1 + x2 + ... + xn) / n
			y = (y1 + y2 + ... + yn) / n
			z = (z1 + z2 + ... + zn) / n
			
			Convert average x, y, z coordinate to latitude and longitude.
			Lon = atan2(y, x)
			Hyp = sqrt(x * x + y * y)
			Lat = atan2(z, hyp)
		 */
		double avgX = 0;
		double avgY = 0;
		double avgZ = 0;
		for(LatitudeLongitude point : points) {
			double lat = Math.toRadians(point.getLatitude());
			double lon = Math.toRadians(point.getLongitude());
			
			double x = Math.cos(lat) * Math.cos(lon);
			double y = Math.cos(lat) * Math.sin(lon);
			double z = Math.sin(lat);
			
			avgX += x; avgY += y; avgZ += z;
		}
		
		avgX /= points.size(); avgY /= points.size(); avgZ /= points.size();
		
		double averageLon = Math.atan2(avgY, avgX);
		double hyp = Math.sqrt(avgX * avgX + avgY * avgY);
		double averageLat = Math.atan2(avgZ, hyp);
		
		return new LatitudeLongitude(Math.toDegrees(averageLat), Math.toDegrees(averageLon));
	}
}
