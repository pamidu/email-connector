package com.procursys.connector.email.sendgrid.application;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.procursys.connector.email.sendgrid.platform.common.InternalErrorException;
import com.procursys.connector.email.sendgrid.platform.common.PlatformEventKey;
import com.procursys.connector.email.sendgrid.platform.common.PlatformEventName;
import com.procursys.connector.email.sendgrid.platform.common.ProcursysExceptionCodes;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import static com.procursys.connector.email.sendgrid.platform.common.Constants.PROCURSYS_FROM_EMAIL_NAME;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PROCURSYS_SEND_EMAIL_ENDPOINT;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PROCURSYS_FROM_EMAIL_ID;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PROCURSYS_PLACEHOLDER_NAME;

@Service
public class EventApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventApplicationService.class);

	@Value("${sendgrid.api.key}")
	private String apiKey;

	@Value("${sendgrid.template.welcome.user.email}")
	private String welcomeTemplate;

	public void sendEmail(String partnerId, PlatformEventName eventName, Map<String, String> eventAttributes) {
		SendGrid sg = new SendGrid(apiKey);

		Request request = new Request();
		Mail dynamicTemplate = buildTemplate(eventName, eventAttributes);
		try {
			request.setMethod(Method.POST);
			request.setEndpoint(PROCURSYS_SEND_EMAIL_ENDPOINT);
			request.setBody(dynamicTemplate.build());
			Response response = sg.api(request);
			LOGGER.info("Procursys api response statusCode: {}", response.getStatusCode());
			LOGGER.info("Procursys api response body: {}", response.getBody());
			LOGGER.info("Procursys api response headers: {}", response.getHeaders());
		} catch (IOException ex) {
			LOGGER.error("IOException occurred while sending email", ex);
			throw new InternalErrorException(ProcursysExceptionCodes.EMAIL_SEND_FAILED.toString(),
					ProcursysExceptionCodes.EMAIL_SEND_FAILED.getDescription(), ex);
		} catch (Exception ex) {
			LOGGER.error("Exception occurred while sending email", ex);
			throw new InternalErrorException(ProcursysExceptionCodes.EMAIL_SEND_FAILED.toString(),
					ProcursysExceptionCodes.EMAIL_SEND_FAILED.getDescription(), ex);
		}
	}

	private Mail buildTemplate(PlatformEventName eventName, Map<String, String> eventAttributes) {
		Mail mail = new Mail();

		Email fromEmail = new Email();
		fromEmail.setName(PROCURSYS_FROM_EMAIL_NAME);
		fromEmail.setEmail(PROCURSYS_FROM_EMAIL_ID);
		mail.setFrom(fromEmail);

		if ((PlatformEventName.USER_WELCOME.equals(eventName))) {
			configureWelcomeUserEmail(mail, eventAttributes);
		}

		return mail;
	}

	private void configureWelcomeUserEmail(Mail mail, Map<String, String> eventAttributes) {
		Personalization personalization = new Personalization();
		personalization.addTo(new Email(eventAttributes.get(PlatformEventKey.EMAIL.name())));
		personalization.addDynamicTemplateData(PROCURSYS_PLACEHOLDER_NAME,
				eventAttributes.get(PlatformEventKey.NAME.name()));
		mail.setTemplateId(welcomeTemplate);

		mail.addPersonalization(personalization);
	}
}
