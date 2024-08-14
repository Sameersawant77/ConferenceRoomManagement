package com.ConferenceRoomManagement.Repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ConferenceRoomManagement.Entities.User;
import com.ConferenceRoomManagement.Utils.HibernateUtil;

public class UserDAO {
	public User getUserById(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userId);
        }
    }
	
public User getUserByUsernameAndPassword(String username,String password) {
		
		Transaction tx = null;
		User user = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			String hql = "FROM User WHERE username = :username AND password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("password", password);
			
			user = (User) query.uniqueResult();
		
			tx.commit();
			
        }catch(Exception e) {
        	e.printStackTrace();
        }
		
		return user;
	}

}
