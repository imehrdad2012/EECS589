package edu.umich.eecs.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Constants {
	public static final int durationThresholdInMs = 10 * 60 * 1000;
	public static final int transitionThresholdInMs = 10 * 60 * 10000;
	public static final int oscillationThreshold = 3;
	public static final int maximumClusterSize=10;
	public static final int minimumQualityValue=10;
	public static final int maximumClusterBufferSize=100;
	public static final ArrayList<Integer> totalCountryNokia= new ArrayList<>();
	
	 static {
		 Collections.addAll(totalCountryNokia,228,293,295,
				 424,232,234,427,432,240,244,310,262,520,
				 457,202,460,268,206,204,208,270,274,404,
				 405,214,216,730,219,220,605,222,604	  
				 
				 );
	 }
	 
}


