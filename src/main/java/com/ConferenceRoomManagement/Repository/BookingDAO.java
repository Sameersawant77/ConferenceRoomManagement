package com.ConferenceRoomManagement.Repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ConferenceRoomManagement.Entities.Booking;
import com.ConferenceRoomManagement.Entities.Booking.BookingStatus;
import com.ConferenceRoomManagement.Utils.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BookingDAO {

	public boolean isSlotAvailable(int roomId, LocalDate bookingDate, LocalDateTime startDateTime, LocalDateTime endDateTime) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        long count = (Long) session.createQuery(
	            "SELECT COUNT(b) FROM Booking b WHERE b.roomId = :roomId " +
	            "AND b.bookingDate = :bookingDate " +
	            "AND b.status != 'cancelled' " +
	            "AND ((b.startTime < :endDateTime AND b.endTime > :startDateTime))")
	            .setParameter("roomId", roomId)
	            .setParameter("bookingDate", bookingDate)
	            .setParameter("startDateTime", startDateTime)
	            .setParameter("endDateTime", endDateTime)
	            .uniqueResult();

	        return count == 0;
	    }
	}

	public void bookSlot(int roomId, LocalDate bookingDate, LocalDateTime startDateTime, LocalDateTime endDateTime, int userId, String email) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    	Transaction transaction = session.beginTransaction();

	        // Check for an existing cancelled booking
	        String hql = "FROM Booking b WHERE b.roomId = :roomId AND b.bookingDate = :bookingDate " +
	                     "AND b.startTime = :startTime AND b.endTime = :endTime AND b.userId = :userId " +
	                     "AND b.status = 'cancelled'";
	        Query<Booking> query = session.createQuery(hql, Booking.class);
	        query.setParameter("roomId", roomId);
	        query.setParameter("bookingDate", bookingDate);
	        query.setParameter("startTime", startDateTime);
	        query.setParameter("endTime", endDateTime);
	        query.setParameter("userId", userId);

	        Booking existingBooking = query.uniqueResult();

	        if (existingBooking != null) {
	            existingBooking.setStatus(BookingStatus.confirmed);
	            session.update(existingBooking);
	        } else {
	            Booking booking = new Booking();
	            booking.setRoomId(roomId);
	            booking.setStartTime(startDateTime);
	            booking.setEndTime(endDateTime);
	            booking.setBookingDate(bookingDate);
	            booking.setUserId(userId);
	            booking.setEmail(email);
	            booking.setStatus(BookingStatus.confirmed);
	            session.save(booking);
	        }

	        transaction.commit();
	    } 
	}
    
    public Booking getBookingDetails(int roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            String hql = "FROM Booking b WHERE b.roomId = :roomId AND b.status != 'cancelled' AND b.startTime < :endTime AND b.endTime > :startTime AND b.bookingDate = :date";
            
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("roomId", roomId);
            query.setParameter("startTime", LocalDateTime.of(date, startTime));
            query.setParameter("endTime", LocalDateTime.of(date, endTime));
            query.setParameter("date", date);

            return query.uniqueResult();
        }
    }
    
    public List<Booking> getBookingsByDate(LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Booking b WHERE b.bookingDate = :date AND b.status != 'cancelled'";
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("date", date);
            return query.list();
        }
    }
    
    public List<Booking> getUserBookingsForDate(int userId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Booking b WHERE b.userId = :userId AND b.bookingDate = :date AND b.status != 'cancelled'";
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("userId", userId);
            query.setParameter("date", date);
            return query.list();
        }
    }
    
    public List<Booking> getAllBookings() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Booking", Booking.class).list();
        }
    }
    
    public List<Booking> getUserBookings(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Booking b WHERE b.userId = :userId";
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }
    
    public Booking getBookingById(int bookingId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Booking b WHERE b.bookingId = :booking_id";
            Query<Booking> query = session.createQuery(hql, Booking.class);
            query.setParameter("booking_id", bookingId);
            return query.uniqueResult();
        }
    }
    
    public boolean cancelBooking(int bookingId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Booking booking = session.get(Booking.class, bookingId);
            if (booking != null) {
                booking.setStatus(BookingStatus.cancelled);
                session.update(booking);
                transaction.commit();
                return true;
            }
            return false;
        }
    }
    
    public boolean reBook(int bookingId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Booking booking = session.get(Booking.class, bookingId);
            if (booking != null) {
                booking.setStatus(BookingStatus.confirmed);
                session.update(booking);
                transaction.commit();
                return true;
            }
            return false;
        }
    }
    
    public void deleteOldBookings() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	transaction = session.beginTransaction();
            String hql = "DELETE FROM Booking b WHERE b.bookingDate < :today";
            Query query = session.createQuery(hql);
            query.setParameter("today", LocalDate.now());
            query.executeUpdate();
            transaction.commit();
        }
    }
}

