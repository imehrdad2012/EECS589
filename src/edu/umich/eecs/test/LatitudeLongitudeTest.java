package edu.umich.eecs.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import edu.umich.eecs.LatitudeLongitude;

public class LatitudeLongitudeTest {

	@Test
	public void test1() {
		double lat1 = 46.220020044539844;
		double lng1 = 7.31418305146581;
		assertEquals(0.0, LatitudeLongitude.distanceInMeters(new LatitudeLongitude(lat1, lng1),
				new LatitudeLongitude(lat1, lng1)), 0.0000001);
	}
	
	@Test
	public void test2() {
		double lat1 = 46.220020044539844;
		double lng1 = 7.31418305146581;
		
		double lat2 = 46.225020044539844;
		double lng2 = 7.31418305146581;
		
		// Checked with google maps
		assertEquals(555.6692, LatitudeLongitude.distanceInMeters(
				new LatitudeLongitude(lat1, lng1),
				new LatitudeLongitude(lat2, lng2)), 0.001);
	}
	
	@Test
	public void testAverage() {
		Collection<LatitudeLongitude> points = new ArrayList<>();
		
		LatitudeLongitude p1 = new LatitudeLongitude(46.220020044539844, 7.31418305146581);
		LatitudeLongitude p2 = new LatitudeLongitude(46.225020044539844, 7.31418305146581);
		
		points.add(p1); points.add(p2);
		System.out.println(LatitudeLongitude.average(points));
	}


}
