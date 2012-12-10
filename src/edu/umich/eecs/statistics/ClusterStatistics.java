package edu.umich.eecs.statistics;

import java.util.List;

import edu.umich.eecs.data.Constants;
import edu.umich.eecs.dto.AreaDensity;
import edu.umich.eecs.dto.Cluster;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.service.AreaDensityService;
import edu.umich.eecs.service.ClusterService;

/**
 * This class is used for computing various
 * statistics related to clustering algorithms.
 * @author Mehrdad
 *
 */



public class ClusterStatistics {

	
/**
 * This method is used for extracting quality and size of
 * all clusters in a dataset.
 * @param dataset
 */
	public void sizeQuality(DataSetType dataset){
		String name="";
		switch (dataset) {
		case RealityMining:
			name="Reality";
			break;
		case SampledRealityMining:
			name="SampledReality";
			break;
			
		case NokiaChallenge:
			name="NokiaChallange";
			break;
	
		}
		
		StatisticFile quality= new StatisticFile("minQualThre="+Constants.minimumQualityValue+",maxClusterSize="+Constants.maximumClusterSize+",Quality="+name+".txt");
		StatisticFile size= new StatisticFile("minQualThre="+Constants.minimumQualityValue+",maxClusterSize="+Constants.maximumClusterSize+",Size="+name+".txt");

		ClusterService cs= new ClusterService();
		List<Cluster> clist=cs.getAllClusters(dataset);
		for(Cluster c:clist){
			if(c.getQuality()==Double.POSITIVE_INFINITY){
				quality.writeDouble(0.0);
				quality.writeString("\n");
				
			}
			else{
				quality.writeDouble(c.getQuality());
				quality.writeString("\n");

			}
			size.writeInt(c.getCells().size());
			size.writeString("\n");

		}
		quality.tearDown();
		size.tearDown();
		
	}
	
	public void clusterDensity(DataSetType dataset){
		String name="";
		switch (dataset) {
		case RealityMining:
			name="Reality";
			break;
		case SampledRealityMining:
			name="SampledReality";
			break;
			
		case NokiaChallenge:
			name="NokiaChallange";
			break;
	
		}
		AreaDensityService srv= new AreaDensityService();
		
		StatisticFile area= new StatisticFile(dataset.asInt()+".txt");
		
		for(Double ad: srv.getAllDensity(dataset)){
			area.writeDouble(ad);
			area.writeString("\n");
		}
		
		area.tearDown();
		//
	}
	
	
	
	
	public static void main(String[] args) {
		ClusterStatistics css= new ClusterStatistics();
		/*css.sizeQuality(DataSetType.NokiaChallenge);
		css.sizeQuality(DataSetType.RealityMining);
		css.sizeQuality(DataSetType.SampledRealityMining);
		System.out.println("Quality and Size Metrics are Generated For All Dataset");
		*/
		
		
		css.clusterDensity(DataSetType.NokiaChallenge);
		css.clusterDensity(DataSetType.RealityMining);
		css.clusterDensity(DataSetType.SampledRealityMining);
		System.out.println("Area Density is Created!....");

		
	}
	
	
	
}
