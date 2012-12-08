package edu.umich.eecs.service;

import java.util.List;

import org.hibernate.Session;

import edu.umich.eecs.dto.AreaDensity;


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
		


}