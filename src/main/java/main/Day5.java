package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
---FIRST PART---
The almanac (your puzzle input) lists all of the seeds that need to be planted.
It also lists what type of soil to use with each kind of seed, what type of fertilizer
to use with each kind of soil, what type of water to use with each kind of fertilizer,
and so on. Every type of seed, soil, fertilizer and so on is identified with a number,
but numbers are reused by each category

The rest of the almanac contains a list of maps which describe how to convert
numbers from a source category into numbers in a destination category.
Rather than list every source number and its corresponding destination number
one by one, the maps describe entire ranges of numbers that can be converted.
Each line within a map contains three numbers: the destination range start,
the source range start, and the range length.

What is the lowest location number that corresponds to any of the initial seed numbers?
 */
public class Day5 {
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

	public static void firstPart() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\day5Input.txt"));
		String firstLine = reader.readLine();
		long[] seeds =parseIntLine(firstLine);

		long out = 0;
		long location;
		long smal =  Long.MAX_VALUE;
		for (long seed : seeds){
			reader = new BufferedReader(new FileReader(".\\day5Input.txt"));

			short counter = 0;
			while (reader.ready()) {
				String line = reader.readLine();

				if (line.matches("(\s*(\\d+)\s*)+")){
					long[] map = parseIntLine(line);

					long range = map[2];
					long source = map[1];
					long destination = map[0];

					if (seed >= source && seed < source + range){
						out = destination + (seed - source);
					}
				}else if (line.matches("[a-z]+-to-[a-z]+\smap:")){
					if (counter > 0){
						seed = out;
					}

					counter++;
				}
			}

			location = out;
			smal = Math.min(location, smal);
		}

		System.out.println(smal);
	}

	public static void main(String[] args) throws IOException {
		firstPart();
	}
}
