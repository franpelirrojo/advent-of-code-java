package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5Part2 {
	private long location = 0;

	static class Task implements Runnable{
		private long seedStart;
		private long seedEnd;
		private BufferedReader reader;

		public Task(long seedStart, long seedEnd) {
			this.seedStart = seedStart;
			this.seedEnd = seedEnd;
		}

		@Override
		public void run() {
			long out = 0;
			long seedLocation = Long.MAX_VALUE;

			for (long i = seedStart; i < (seedStart + seedEnd); i++){
				try {
					long item = i;
					reader = new BufferedReader(new FileReader(".\\day5Input.txt"));

					short counter = 0;
					while (reader.ready()) {
						String line = reader.readLine();

						if (line.matches("(\s*(\\d+)\s*)+")) {
							long[] map = parseIntLine(line);

							long range = map[2];
							long source = map[1];
							long destination = map[0];

							if (item >= source && item < source + range) {
								out = destination + (item - source);
							}
						} else if (line.matches("[a-z]+-to-[a-z]+\smap:")) {
							if (counter > 0) {
								item = out;
							}
							counter++;
						}
					}

					seedLocation = Math.min(seedLocation, out);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

		}
	}

	public void partTwo() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\day5Input.txt"));
		String firstLine = reader.readLine();
		long[] seedRange = parseIntLine(firstLine);

		for (int k = 0; k < seedRange.length;k += 2) {
			new Thread(new Task(seedRange[k], seedRange[k + 1])).start();
		}
	}

	public static long[] parseIntLine(String line){
		ArrayList<String> seedsStr = new ArrayList<>(List.of(line.split("\s")));
		seedsStr = (ArrayList<String>) seedsStr.stream()
				.filter(k -> k.matches("\\d*"))
				.collect(Collectors.toList()); //I love java...

		long [] seeds = new long[seedsStr.size()];
		for (String seedStr : seedsStr){
			long seed = Long.parseLong(seedStr);
			seeds[seedsStr.indexOf(seedStr)] = seed;
		}

		return seeds;
	}
}
