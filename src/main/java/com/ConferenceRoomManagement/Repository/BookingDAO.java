package com.ConferenceRoomManagement.Repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ConferenceRoomManagement.Entities.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BookingDAO {

    public boolean isSlotAvailable(int roomId, LocalDate bookingDate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            long count = (Long) session.createQuery("SELECT COUNT(b) FROM Booking b WHERE b.roomId = :roomId " +
                            "AND b.bookingDate = :bookingDate " +
                            "AND ((b.startTime < :endDateTime AND b.endTime > :startDateTime))")
                    .setParameter("roomId", roomId)
                    .setParameter("bookingDate", bookingDate)
                    .setParameter("startDateTime", startDateTime)
                    .setParameter("endDateTime", endDateTime)
                    .uniqueResult();

            return count == 0;
        }
    }

    public void bookSlot(int roomId, LocalDate bookingDate, LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Booking booking = new Booking();
            booking.setRoomId(roomId);
            booking.setStartTime(startDateTime);
            booking.setEndTime(endDateTime);
            booking.setBookingDate(bookingDate);
            booking.setUserId(userId);
            booking.setStatus(Booking.BookingStatus.confirmed);
            session.save(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
    
    public Booking getBookingDetails(int roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            String hql = "FROM Booking b WHERE b.roomId = :roomId AND b.startTime < :endTime AND b.endTime > :startTime AND b.bookingDate = :date";
            
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("roomId", roomId);
            query.setParameter("startTime", LocalDateTime.of(date, startTime));
            query.setParameter("endTime", LocalDateTime.of(date, endTime));
            query.setParameter("date", date);

            return query.uniqueResult();
        }
    }
    
    public List<Booking> getUserBookingsForDate(int userId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Booking b WHERE b.userId = :userId AND b.bookingDate = :date";
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("userId", userId);
            query.setParameter("date", date);
            return query.list();
        }
    }
}

