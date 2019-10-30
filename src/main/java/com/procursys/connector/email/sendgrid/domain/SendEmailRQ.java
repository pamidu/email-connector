package com.procursys.connector.email.sendgrid.domain;

import java.util.Map;

import com.procursys.connector.email.sendgrid.platform.common.PlatformEventName;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "SendEmailRequest")
public class SendEmailRQ {

	private PlatformEventName platformEventName;

	private Map<String, String> eventAttributes;

	public PlatformEventName getPlatformEventName() {
		return platformEventName;
	}

	public void setPlatformEventName(PlatformEventName platformEventName) {
		this.platformEventName = platformEventName;
	}

	public Map<String, String> getEventAttributes() {
		return eventAttributes;
	}

	public void setEventAttributes(Map<String, String> eventAttributes) {
		this.eventAttributes = eventAttributes;
	}

}
