import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class day3 {
	public static boolean validatePosition(int[] position, ArrayList<ArrayList<Integer>> validPosition, int line){
		int start = line - 1;
		int end = line + 1;
		int pointer = position[0] - 1;
		int endPointer = position[1] + 1;

		if (line == 0){
			start = 0;
		}

		if (line == validPosition.size()){
			end = line;
		}

		if (position[0] == 0 || pointer == -1){
			pointer = position[0];
		}

		if (position[1] == validPosition.get(line).size()){
			endPointer = position[1];
		}

		for (int i = start; i <= end; i++){
			for (int k = pointer; k <= endPointer; k++){
				if (validPosition.get((i == validPosition.size() ? i - 1: i)).get(k) == 1){ //leave room for improvement
					return true;
				}
			}
		}

		return false;
	}

	public static void firstPart() throws IOException {
		int engineSum = 0;
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(".\\day3Input.txt"));

		while (reader.ready()) {
			lines.add(reader.readLine());
		}

		ArrayList<ArrayList<Integer>> numberList = new ArrayList<>();
		ArrayList<ArrayList<int[]>> numberPosition = new ArrayList<>();
		ArrayList<ArrayList<Integer>> validPosition = new ArrayList<>();

		for (int i = 0; i < lines.size(); i++) {
			StringBuilder number = new StringBuilder();
			String line = lines.get(i);
			numberList.add(new ArrayList<>());
			numberPosition.add(new ArrayList<>());
			validPosition.add(new ArrayList<>());

			int lastK = 0;
			int startPosition = 0;
			boolean inDigit = false;
			for (int k = 0; k < line.length(); k++) {
				char character = line.charAt(k);
				if (Character.isDigit(character)) {
					number.append(character);
					validPosition.get(i).add(-1);
					if (!inDigit) {
						startPosition = k;
						inDigit = true;
					}
				} else {
					if (character != '.') {
						validPosition.get(i).add(1);
					} else {
						validPosition.get(i).add(0);
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
				if (validatePosition(position, validPosition, i)){
					engineSum += numberList.get(i).get(k);
				}
			}
		}

		System.out.println(engineSum);
	}

	public static void main(String[] args) throws IOException {
		firstPart();
	}
}
