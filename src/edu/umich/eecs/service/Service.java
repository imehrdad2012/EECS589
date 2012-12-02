package edu.umich.eecs.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
/**
 * This class has responsibility to setup, tear down,and fire and 
 * commit transactions.
 * Every class wants to work with hibernate should extends this class.
 * @author Mehrdad
 *
 */


public class Service {
	

	private SessionFactory sessionFactory;
	
	public Service() {
		setUp();
	}
	
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
	
	

}
