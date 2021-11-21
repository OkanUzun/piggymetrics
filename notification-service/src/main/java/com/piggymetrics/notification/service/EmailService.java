package com.piggymetrics.notification.service;

import com.piggymetrics.notification.domain.NotificationType;
import com.piggymetrics.notification.domain.Recipient;

import javax.mail.MessagingException;

public interface EmailService {

	void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException;

}
