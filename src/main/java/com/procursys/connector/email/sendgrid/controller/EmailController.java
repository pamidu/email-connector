package com.procursys.connector.email.sendgrid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.procursys.connector.email.sendgrid.application.EventApplicationService;
import com.procursys.connector.email.sendgrid.domain.SendEmailRQ;
import com.procursys.connector.email.sendgrid.platform.common.HTTPResponseHandler;
import com.procursys.connector.email.sendgrid.platform.common.PlatformLoggingKey;
import com.procursys.connector.email.sendgrid.platform.common.RequestMappings;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.HTTP_METHOD_POST;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PARTNER_ID_HEADER;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PARTNER_ID_DESCRIPTION;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PARTNER_ID_DATATYPE;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.PARTNER_ID_PARAMTYPE;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.HTTP_STATUS_SUCCESS_STATUS_CODE;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION;
import static com.procursys.connector.email.sendgrid.platform.common.Constants.HTTP_STATUS_BAD_REQUEST_STATUS_CODE;

import io.swagger.annotations.ApiResponse;
@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController extends HTTPResponseHandler {

	@Autowired
	EventApplicationService eventApplicationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

	@ApiOperation(httpMethod = HTTP_METHOD_POST, value = "Send Email", nickname = "Send Email")
	@ApiImplicitParams({
			@ApiImplicitParam(name = PARTNER_ID_HEADER, value = PARTNER_ID_DESCRIPTION, dataType = PARTNER_ID_DATATYPE, paramType = PARTNER_ID_PARAMTYPE, required = true) })
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_STATUS_SUCCESS_STATUS_CODE, message = HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION, response = Void.class),
			@ApiResponse(code = HTTP_STATUS_BAD_REQUEST_STATUS_CODE, message = HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION) })
	@RequestMapping(value = RequestMappings.SEND_EMAIL, method = RequestMethod.POST)
	public void sendEmail(HttpServletResponse response, HttpServletRequest request,
			@RequestBody @Valid SendEmailRQ sendEmailRequest) {
		String partnerId = request.getHeader(PARTNER_ID_HEADER);

		Map<String, Object> logData = new HashMap<String, Object>();
		logData.put(PlatformLoggingKey.PARTNER_ID.name(), partnerId);
		logData.put(PlatformLoggingKey.PLATFORM_EVENT_NAME.name(), sendEmailRequest.getPlatformEventName().name());
		LOGGER.info("Received 'Send Email' Request. {}", logData);

		setStatusHeadersToSuccess(response);
		eventApplicationService.sendEmail(partnerId, sendEmailRequest.getPlatformEventName(),
				sendEmailRequest.getEventAttributes());

		LOGGER.info("Returning 'Send Email' Response.");

	}
}
