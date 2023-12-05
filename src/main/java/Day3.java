import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
---FIRST PART---
The engine schematic (your puzzle input) consists of a visual representation of the engine.
There are lots of numbers and symbols you don't really understand, but apparently any number
adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum.
(Periods (.) do not count as a symbol.)

In this schematic, two numbers are not part numbers because they are not adjacent to a symbol:
114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a
part number; their sum is 4361.
Of course, the actual engine schematic is much larger. What is the sum of all of the
part numbers in the engine schematic?
---SECOND PART---
The missing part wasn't the only issue - one of the gears in the engine is wrong.
A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio
is the result of multiplying those two numbers together. This time, you need to
find the gear ratio of every gear and add them all up so that the engineer can
figure out which gear needs to be replaced. What is the sum of all of
the gear ratios in your engine schematic?
 */

public class Day3 {
	public static ArrayList<Integer> parseNumber(ArrayList<int[]> numbers,
											   ArrayList<ArrayList<Integer>> numberList,
											   ArrayList<ArrayList<int[]>> numberPosition){

		ArrayList<Integer> gear = new ArrayList<>();

		for (int[] number : numbers){
			int lineConunter = 0;
			for (ArrayList<int[]> line : numberPosition){
				if (number[0] == lineConunter){
					int numberCounter = 0;
					for (int[] position : line){
						if (position[1] == number[1]){
							gear.add(numberList.get(lineConunter).get(numberCounter));
						}

						numberCounter++;
					}
				}
				lineConunter++;
			}
		}

		return gear;
	}

	public static  ArrayList<int[]> validateGear(int[] position, ArrayList<ArrayList<Integer>> map){
		int start = position[0] - 1;
		int end = position[0] + 1;
		int pointer = position[1] - 1;
		int endPointer = position[1] + 1;
		ArrayList<int[]> numbers = new ArrayList<>();

		if (position[0] == 0){
			start = position[0];
		}

		if (position[0] == map.size()){
			end = position[0];
		}

		if (position[1] == 0){
			pointer = position[1];
		}

		if (position[1] == map.get(position[0]).size()){
			endPointer = position[1];
		}

		int counter = 0;
		for (int i = start; i <= end; i++){
			int prior = 0;
			int lastK = 0;
			for (int k = pointer; k <= endPointer; k++){
				int mapedPosition = map.get(i).get(k);
				if (mapedPosition != -1 && prior == -1){
					counter ++;
					numbers.add(new int[]{i, k-1});
					prior = mapedPosition;
				} else if (mapedPosition == -1){
					prior = -1;
				}

				lastK = k;
			}

			if (prior == -1) { //when a number is out of lenght from the window
				boolean stop = false;
				while (!stop) {
					if (lastK == map.get(i).size()) {
						numbers.add(new int[]{i, lastK});
						counter++;
						stop = true;
					}else{
						int mapedPosition = map.get(i).get(lastK);
						if (mapedPosition != -1) {
							counter++;
							numbers.add(new int[]{i, lastK - 1});
							stop = true;
						}
					}
					lastK++;
				}
			}
		}

		if (counter == 2){
			System.out.println(Arrays.toString(numbers.get(0)) + " " + Arrays.toString(numbers.get(1)));
			return numbers;
		}else {
			return null;
		}
	}

	public static boolean validatePosition(int[] position, ArrayList<ArrayList<Integer>> map, int line){
		int start = line - 1;
		int end = line + 1;
		int pointer = position[0] - 1;
		int endPointer = position[1] + 1;

		if (line == 0){
			start = 0;
		}

		if (line == map.size()){
			end = line;
		}

		if (position[0] == 0 || pointer == -1){
			pointer = position[0];
		}

		if (position[1] == map.get(line).size()){
			endPointer = position[1];
		}

		for (int i = start; i <= end; i++){
			for (int k = pointer; k <= endPointer; k++){
				int point = map.get((i == map.size() ? i - 1: i)).get(k);//leave room for improvement
				if (point != 0 && point != -1){
					return true;
				}
			}
		}

		return false;
	}

	public static void firstPart() throws IOException {
		int engineSum = 0;
		int gearSum = 0;
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(".\\day3Input.txt"));

		while (reader.ready()) {
			lines.add(reader.readLine());
		}

		ArrayList<ArrayList<Integer>> numberList = new ArrayList<>();
		ArrayList<ArrayList<int[]>> numberPosition = new ArrayList<>();
		ArrayList<ArrayList<Integer>> tokenization = new ArrayList<>();
		ArrayList<int[]> gearPosition = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {
			StringBuilder number = new StringBuilder();
			String line = lines.get(i);
			numberList.add(new ArrayList<>());
			numberPosition.add(new ArrayList<>());
			tokenization.add(new ArrayList<>());

			int lastK = 0;
			int startPosition = 0;
			boolean inDigit = false;
			for (int k = 0; k < line.length(); k++) {
				char character = line.charAt(k);
				if (Character.isDigit(character)) {
					number.append(character);
					tokenization.get(i).add(-1);
					if (!inDigit) {
						startPosition = k;
						inDigit = true;
					}
				} else {
					if (character == '.') {
						tokenization.get(i).add(0);
					} else if (character == '*'){
						tokenization.get(i).add(2);
						gearPosition.add(new int[]{i, k}); //gear position PART TWO
					} else {
						tokenization.get(i).add(1);
					}

					if (!number.isEmpty()) {
						numberList.get(i).add(Integer.parseInt(number.toString()));
						numberPosition.get(i).add(new int[]{startPosition, k - 1}); //end Position
						number.delete(0, number.length());
						inDigit = false;
					}
					
					lastK = k;
				}
			}
			
			if (!number.isEmpty()){
				numberList.get(i).add(Integer.parseInt(number.toString()));
				numberPosition.get(i).add(new int[]{startPosition, lastK - 1}); //end Position
			}
		}

		for (int i = 0; i < numberPosition.size(); i++){
			ArrayList<int[]> line = numberPosition.get(i);
			for (int k = 0; k < line.size(); k++) {
				int[] position = line.get(k);
				if (validatePosition(position, tokenization, i)){
					engineSum +=  numberList.get(i).get(k);
				}
			}
		}  //this to blocks can be a function PART TWO

		for (int i = 0; i < gearPosition.size(); i++){
				int[] position = gearPosition.get(i);
				ArrayList<int[]> gearNumber = validateGear(position, tokenization);
				if (gearNumber != null){
					ArrayList<Integer> gear = parseNumber(gearNumber, numberList, numberPosition);
					gearSum += (gear.get(0)*gear.get(1));
				}
		}

		System.out.println("EngineSum: " + engineSum);
		System.out.println("GearSum: " + gearSum);
	}

	public static void main(String[] args) throws IOException {
		firstPart();
	}
}