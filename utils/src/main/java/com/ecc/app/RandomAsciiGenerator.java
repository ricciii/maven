package com.ecc.app;
import java.util.Random;

public class RandomAsciiGenerator {
	private final int defaultNumOfSymbols = 3;

	public RandomAsciiGenerator() {}

	public String generate() {
		int i=0;
		String string = new String("");
		Random random = new Random();
		while(i<defaultNumOfSymbols) {
			string +=(String.valueOf((char) (32 + random.nextInt(94))));
			i++;
		}
		return string;
	}
}