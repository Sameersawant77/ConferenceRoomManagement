package com.ConferenceRoomManagement.Repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ConferenceRoomManagement.Entities.Room;
import com.ConferenceRoomManagement.Entities.Room.RoomStatus;
import com.ConferenceRoomManagement.Utils.HibernateUtil;

public class RoomDAO {

    public List<Room> getRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Room", Room.class).list();
        }
    }
    
    public List<Room> getActiveRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Room WHERE status = :status", Room.class)
                          .setParameter("status", RoomStatus.active)
                          .list();
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

    public int addRoom(String roomName, int capacity, String status, String amenities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Room room = new Room();
            room.setRoomName(roomName);
            room.setCapacity(capacity);
            if (status.equals("active")) {
                room.setStatus(RoomStatus.active);
            } else {
                room.setStatus(RoomStatus.inactive);
            }
            room.setAmenities(amenities); 
            session.save(room);
            tx.commit();
            return 1;
        }
    }

    public int deleteRoom(int roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Room r = session.get(Room.class, roomId);
            if (r != null) {
                session.delete(r);
                tx.commit();
                return 1; 
            } else {
                return 0; 
            }
        }
    }

    public int updateRoom(int roomId, String roomName, int capacity, String status, String amenities) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                room.setRoomName(roomName);
                room.setCapacity(capacity);
                if (status.equals("active")) {
                    room.setStatus(RoomStatus.active);
                } else {
                    room.setStatus(RoomStatus.inactive);
                }
                room.setAmenities(amenities); 
                session.update(room);
                tx.commit();
                return 1; 
            } else {
                return 0; 
            }
        }
    }
}
