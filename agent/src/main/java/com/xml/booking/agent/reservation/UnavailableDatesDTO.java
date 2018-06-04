package com.xml.booking.agent.reservation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;

public class UnavailableDatesDTO {
	
	@NotNull
	private Long accId;
	
	@NotNull
	@Size(min = 1)
	private ArrayList<ArrayList<Long>> dates;

	public UnavailableDatesDTO() {}

	public UnavailableDatesDTO(Long accId, ArrayList<ArrayList<Long>> dates) {
		this.accId = accId;
		this.dates = dates;
	}

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public ArrayList<ArrayList<Long>> getDates() {
		return dates;
	}

	public void setDates(ArrayList<ArrayList<Long>> dates) {
		this.dates = dates;
	}

}
