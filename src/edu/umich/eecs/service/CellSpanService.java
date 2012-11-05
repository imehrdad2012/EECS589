package edu.umich.eecs.service;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


import edu.umich.eecs.dto.Cell;
import edu.umich.eecs.dto.CellSpan;
import edu.umich.eecs.dto.CellSpanCompoundKey;

public class CellSpanService {

	private SessionFactory sessionFactory;
	
	
	// setup configuration and session factory
	public void setUp(){  
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	
	}
	
	//tear down session factory
	public void tearDown() {
		
		try {
			if ( sessionFactory != null ) {
				sessionFactory.close();
			}
		} catch (Exception e) {
			
		}
		
	}
	
	// get session from session factory
	public Session fireTransaction() {
		Session session =sessionFactory.openSession();
		session.beginTransaction();
		return session;
	}
	
	//commit transaction and close session
	public void commitTransaction(Session session){
		session.getTransaction().commit();
		session.close();
		
	}
	//
	public void saveCP(CellSpan cp){
		
		Session s=fireTransaction();
		s.save(cp);
		commitTransaction(s);
	}
	
	public  List< CellSpan> getCPByPersonID(int pid){
		
		  Session s= fireTransaction();
		   Query query=s.createQuery("from CellSpan where key.personid=:pid ");
		   query.setInteger("pid", pid);
		   List <CellSpan> lcp=(List<CellSpan>)query.list();
		   commitTransaction(s);
	  return lcp; 
	}
	
	
	
	
	
	
}

