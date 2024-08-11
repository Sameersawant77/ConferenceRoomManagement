package com.ConferenceRoomManagement.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "HourlySlots")
public class HourlySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private int slotId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;

    @Column(name = "slot_start_time", nullable = false)
    private LocalTime slotStartTime;

    @Column(name = "slot_end_time", nullable = false)
    private LocalTime slotEndTime;
    
    public enum SlotStatus {
        available,
        booked
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SlotStatus status;

	public HourlySlot() {}

	public HourlySlot(int slotId, Room room, LocalDate slotDate, LocalTime slotStartTime, LocalTime slotEndTime,
			SlotStatus status) {
		super();
		this.slotId = slotId;
		this.room = room;
		this.slotDate = slotDate;
		this.slotStartTime = slotStartTime;
		this.slotEndTime = slotEndTime;
		this.status = status;
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDate getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(LocalDate slotDate) {
		this.slotDate = slotDate;
	}

	public LocalTime getSlotStartTime() {
		return slotStartTime;
	}

	public void setSlotStartTime(LocalTime slotStartTime) {
		this.slotStartTime = slotStartTime;
	}

	public LocalTime getSlotEndTime() {
		return slotEndTime;
	}

	public void setSlotEndTime(LocalTime slotEndTime) {
		this.slotEndTime = slotEndTime;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(SlotStatus status) {
		this.status = status;
	}

}
