package com.ConferenceRoomManagement.Repository;


import java.util.List;
import org.hibernate.Session;
import com.ConferenceRoomManagement.Entities.Room;


public class RoomDAO {

    public List<Room> getRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Room", Room.class).list();
        }
    }

    public Room getRoomById(int roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, roomId);
        }
    }

    public int getRoomIdByName(String roomName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (Integer) session.createQuery("SELECT roomId FROM Room WHERE roomName = :roomName")
                                    .setParameter("roomName", roomName)
                                    .uniqueResult();
        }
    }
}


