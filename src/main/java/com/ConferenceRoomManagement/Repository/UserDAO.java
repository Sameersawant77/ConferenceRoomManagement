package com.ConferenceRoomManagement.Repository;

import org.hibernate.Session;

import com.ConferenceRoomManagement.Entities.User;

public class UserDAO {
	public User getUserById(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userId);
        }
    }
}
