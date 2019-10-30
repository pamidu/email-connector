package com.procursys.connector.email.sendgrid.platform.common;

public enum ProcursysExceptionCodes {

	EMAIL_SEND_FAILED("Email send failed");

	private final String explanation;

	private ProcursysExceptionCodes(String reasonPhrase) {
		this.explanation = reasonPhrase;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getDescription() {
		return explanation;
	}
}
