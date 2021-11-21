package com.piggymetrics.notification.service;

import com.piggymetrics.notification.domain.NotificationType;
import com.piggymetrics.notification.domain.Recipient;
import com.piggymetrics.notification.repository.RecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final RecipientRepository repository;

    public RecipientServiceImpl(final RecipientRepository repository) {
        this.repository = repository;
    }

    @Override
	public Recipient findByAccountName(String accountName) {
		Assert.hasLength(accountName, "accountName must have length");
		return repository.findByAccountName(accountName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Recipient save(String accountName, Recipient recipient) {

		recipient.setAccountName(accountName);
		recipient.getScheduledNotifications().values()
				.forEach(settings -> {
					if (settings.getLastNotified() == null) {
						settings.setLastNotified(new Date());
					}
				});

		repository.save(recipient);

		log.info("recipient {} settings has been updated", recipient);

		return recipient;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Recipient> findReadyToNotify(NotificationType type) {
		switch (type) {
			case BACKUP:
				return repository.findReadyForBackup();
			case REMIND:
				return repository.findReadyForRemind();
			default:
				throw new IllegalArgumentException(String.format("type with value %s is not valid", type));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void markNotified(NotificationType type, Recipient recipient) {
		recipient.getScheduledNotifications().get(type).setLastNotified(new Date());
		repository.save(recipient);
	}
}
