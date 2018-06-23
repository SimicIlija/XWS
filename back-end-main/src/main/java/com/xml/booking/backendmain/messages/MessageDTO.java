package com.xml.booking.backendmain.messages;

import javax.validation.constraints.NotNull;

public class MessageDTO {
	
	@NotNull
	private Long master;
	
	@NotNull
	private Long receiver;
	
	@NotNull
	private String content;
	
	@NotNull
	private Long reservation;
	
	public MessageDTO() {}

	public MessageDTO(Long master, String content) {
		this.master = master;
		this.content = content;
	}

	public Long getMaster() {
		return master;
	}

	public void setMaster(Long master) {
		this.master = master;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public Long getReservation() {
		return reservation;
	}

	public void setReservation(Long reservation) {
		this.reservation = reservation;
	}
	
}
