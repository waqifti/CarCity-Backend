package com.carcity.CarCity.Backend.dataentities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum JobState {
	NEW_JOB_SCHEDULED_LATER,
	NEW_JOB_WANTS_SERVICE_NOW,
	JOB_ASSIGNED_TO_SP,
	CANCEL_BY_CUSTOMER;
//
//	private static final List<JobState> VALUES =
//			Collections.unmodifiableList(Arrays.asList(values()));
//	private static final int SIZE = VALUES.size();
//	private static final Random RANDOM = new Random();
//
//	public static JobState randomState()  {
//		return VALUES.get(RANDOM.nextInt(SIZE));
//	}
}
