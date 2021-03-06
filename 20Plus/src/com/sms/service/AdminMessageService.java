package com.sms.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bean.ForgotAdminPassword;
import com.msg.service.config.MessageServiceConfig;

public class AdminMessageService {
	static Logger logger = Logger.getLogger(SendMessage.class);
	public void sendPasswordToAdmin(ForgotAdminPassword forgotAdminPassword,String adminpassword) {
		String adminMsg = "Hi \n Admin Id : "+forgotAdminPassword.getadminId()
				+ "Password : "+adminpassword;
		// Construct data
		try {
					String apiKey = "apikey=" + URLEncoder.encode(MessageServiceConfig.getValue("textlocal_apikey"), "UTF-8");
					String message = "&message=" + URLEncoder.encode(adminMsg, "UTF-8");
					String sender = "&sender=" + URLEncoder.encode(MessageServiceConfig.getValue("sender_key"), "UTF-8");
					String numbers = "&numbers=" + URLEncoder.encode("+91"+forgotAdminPassword.getMobileNo(), "UTF-8");
					
					// Send data
					String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
					URL url = new URL(data);
					URLConnection conn = url.openConnection();
					conn.setDoOutput(true);
					// Get the response
					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line;
					String sResult="";
					while ((line = rd.readLine()) != null) {
					// Process line...
						sResult=sResult+line+" ";
					}
					rd.close();
				
				} catch (Exception e) {
					logger.warn("------>>> getting sms data in SendMessage  <<<----- exception: "+e.getMessage()+new Date());
				}
	}
}
