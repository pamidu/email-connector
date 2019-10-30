package com.procursys.connector.email.sendgrid.platform.common;

public interface Constants {
	
	public static String HTTP_METHOD_GET = "GET";

	public static String HTTP_METHOD_POST = "POST";

	public static String HTTP_METHOD_PUT = "PUT";

	public static String HTTP_METHOD_DELETE = "DELETE";

	public static int HTTP_STATUS_SUCCESS_STATUS_CODE = 200;

	public static String HTTP_STATUS_SUCCESS_STATUS_DESCRIPTION = "Success";

	public static int HTTP_STATUS_BAD_REQUEST_STATUS_CODE = 400;

	public static String HTTP_STATUS_BAD_REQUEST_STATUS_DESCRIPTION = "Bad Request";
	
    public static String PROCURSYS_PLACEHOLDER_NAME = "name";

    public static String PROCURSYS_SEND_EMAIL_ENDPOINT = "mail/send";
	
	public static String PROCURSYS_FROM_EMAIL_NAME = "Procursys";
	
	public static String PROCURSYS_FROM_EMAIL_ID = "test@example.com";
	
	public static String PARTNER_ID_HEADER = "partner-id";

	public static String PARTNER_ID_DESCRIPTION = "Partner ID";

	public static String PARTNER_ID_DATATYPE = "string";

	public static String PARTNER_ID_PARAMTYPE = "header";

}
