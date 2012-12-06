package edu.umich.eecs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.umich.eecs.LatitudeLongitudeDistance;

public class LatLngDistanceTest {

	@Test
	public void test1() {
		double lat1 = 46.220020044539844;
		double lng1 = 7.31418305146581;
		assertEquals(0.0, LatitudeLongitudeDistance.distanceInMeters(lat1, lng1, lat1, lng1), 0.0000001);
	}
	
	@Test
	public void test2() {
		double lat1 = 46.220020044539844;
		double lng1 = 7.31418305146581;
		
		double lat2 = 46.225020044539844;
		double lng2 = 7.31418305146581;
		
		// Checked with google maps
		assertEquals(627.546, LatitudeLongitudeDistance.distanceInMeters(lat1, lng1, lat2, lng2), 0.001);
	}


}
