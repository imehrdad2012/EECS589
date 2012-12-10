package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.umich.eecs.dto.AreaDensity;
import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellKey;
import edu.umich.eecs.dto.DataSetType;
import edu.umich.eecs.dto.OscillatingCellTowerPair;


public class AreaDensityService  extends Service{
	
	
	
		public void saveAD(AreaDensity ad){
			Session s=fireTransaction();
			s.saveOrUpdate(ad);
			commitTransaction(s);
		}
		
		public void saveListToAD(List<AreaDensity> locations){
			Session s=fireTransaction();
			for(AreaDensity l: locations){
				s.saveOrUpdate(l);
			} 
			commitTransaction(s);
		}
		
		public List<Double> getAllDensity(DataSetType dataset) {
			  Session s= fireTransaction();
			  Query query=s.createQuery("select density from AreaDensity  where dataset =:dataset");
			  query.setInteger("dataset", dataset.asInt());
			   List <Double> alldensity=(List<Double>)query.list();
			  return alldensity;	
		}
		
		


}