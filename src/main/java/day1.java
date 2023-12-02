import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
---FIRST PART---
The newly-improved calibration document consists of lines of text; each line originally contained
a specific calibration value that the Elves now need to recover. On each line, the calibration value
can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
---SECOND PART---
Your calculation isn't quite right. It looks like some of the digits are actually spelled out with letters: one,
two, three, four, five, six, seven, eight, and nine also count as valid "digits".
*/
public class day1 {
	public static void firstPart() {
		BufferedReader reader;
		int start = 0;
		int end = 0;
		boolean first;
		boolean second;
		int sum = 0;

		try {
			reader = new BufferedReader(new FileReader(".\\day1Input.txt"));

			while (reader.ready()){
				first = false;
				second = false;
				String line = reader.readLine();

				for (char character : line.toCharArray()){
					if (Character.isDigit(character)) {
						if (!first){
							start = Character.getNumericValue(character);
							first = true;
						}else {
							end = Character.getNumericValue(character);
							second = true;
						}
					}
				}


				if (second){
					sum += (start * 10) + end;
				}else {
					sum += (start * 10) + start;
				}
			}

			System.out.println(sum);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static int parseNumber(String letters){
		int number = -1;
		switch (letters){
			case "one" -> number = 1;
			case "two" -> number = 2;
			case "three" -> number = 3;
			case "four" -> number = 4;
			case "five" -> number = 5;
			case "six" -> number = 6;
			case "seven" -> number = 7;
			case "eight" -> number = 8;
			case "nine" -> number = 9;
		}

		return number;
	}

	public static int checkForNumber(String letters){
		int number = -1;
		for (int k = 0; k < letters.length(); k++){
			String sufix = letters.substring(k);
			number = parseNumber(sufix);
			if (number != -1){
				return number;
			}
		}

		return number;
	}

	public static void secondPart(){
		BufferedReader reader;
		int start;
		int end;
		int sum = 0;
		boolean first;
		boolean second;

		try {
			reader = new BufferedReader(new FileReader(".\\day1Input.txt"));

			while (reader.ready()){
				start = 0;
				end = 0;
				first = false;
				second = false;
				String line = reader.readLine();
				StringBuilder stringBuilder = new StringBuilder();

				for (char character : line.toCharArray()){
					if (Character.isDigit(character)) {
						if (!first){
							start = Character.getNumericValue(character);
							first = true;
						}else {
							end = Character.getNumericValue(character);
							second = true;
						}
					}else {
						stringBuilder.append(character);
						int number = checkForNumber(stringBuilder.toString());
						if (number != -1){
							if (!first){
								start = number;
								first = true;
							}else {
								end = number;
								second = true;
							}
							stringBuilder.delete(0, stringBuilder.length() - 1); //save de last character to allow overlap
						}
					}
				}

				if (second){
					sum += (start * 10) + end;
				}else {
					sum += (start * 10) + start;
				}
			}

			System.out.println(sum);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		secondPart();
	}
}
