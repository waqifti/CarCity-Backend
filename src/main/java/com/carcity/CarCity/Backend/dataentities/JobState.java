package com.carcity.CarCity.Backend.dataentities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum JobState {
	State1,
	State2,
	State3,
	State4,
	State5,
	State6,
	State7;

	private static final List<JobState> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static JobState randomState()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
