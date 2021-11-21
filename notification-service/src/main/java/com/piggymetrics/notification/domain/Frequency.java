package com.piggymetrics.notification.domain;

import java.util.stream.Stream;

public enum Frequency {

	WEEKLY(7), MONTHLY(30), QUARTERLY(90);

	private final int days;

	Frequency(int days) {
		this.days = days;
	}

	public int getDays() {
		return days;
	}

	public static Frequency withDays(int days) {
		return Stream.of(Frequency.values())
				.filter(f -> f.getDays() == days)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(String.format("%s is not valid day value", days)));
	}
}
