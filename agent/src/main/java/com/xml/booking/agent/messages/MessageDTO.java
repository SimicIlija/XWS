package com.xml.booking.agent.messages;

import javax.validation.constraints.NotNull;

public class MessageDTO {
	
	@NotNull
	private Long master;
	
	@NotNull
	private String content;

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

}
