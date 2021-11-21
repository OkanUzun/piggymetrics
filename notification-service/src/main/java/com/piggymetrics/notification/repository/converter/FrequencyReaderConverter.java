package com.piggymetrics.notification.repository.converter;

import com.piggymetrics.notification.domain.Frequency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class FrequencyReaderConverter implements Converter<Integer, Frequency> {

	@Override
	public Frequency convert(@NonNull Integer days) {
		return Frequency.withDays(days);
	}
}
