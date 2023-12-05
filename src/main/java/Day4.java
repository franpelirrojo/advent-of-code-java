import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
	/*
	PART TWO
	 */
	class Tree{
		private final Node root = new Node(0);

		public Tree(HashMap<Integer, Integer> pointers) {
			Node.setPointers(pointers);
			for (int k = 1; k < pointers.size(); k++){
					Node node = new Node(k, pointers.get(k)); //build the next node
					root.addChild(node);
			}
		}

		public int partTwo(){
			int cardSum = 0;
			HashMap<Integer, Integer> rootMap = root.getChieldsValues();
			for (Integer key : rootMap.keySet()){
				cardSum += rootMap.get(key);
			}

			return cardSum;
		}
	}

	class Node {
		private int value;
		private ArrayList<Node> chields;
		private static HashMap<Integer, Integer> pointers;

		public Node(int value){
			this.value = value;
			chields =new ArrayList<>();
		}

		public Node(int value, int connections) {
			this.value = value;
			Node child;

			if (connections != 0){
				chields = new ArrayList<>();
				for (int k = value + 1; k <= value + connections; k++){ //build the next node
					 child = new Node(k, pointers.get(k));
					 addChild(child);
				}
			}
		}

		public int getValue() {
			return value;
		}

		public HashMap<Integer, Integer> getChieldsValues() {
			HashMap<Integer, Integer> valuesSum = new HashMap<>();
			if (chields != null){
				for (Node child : chields){
					HashMap<Integer, Integer> childValues = child.getChieldsValues();
					childValues.forEach((k, v) ->{
						if (valuesSum.containsKey(k)){
							valuesSum.put(k, valuesSum.get(k) + v);
						}else {
							valuesSum.put(k, v);
						}
					});
				}
			}

			valuesSum.put(getValue(), valuesSum.get(getValue()) == null ? 1 : valuesSum.get(getValue()) + 1);
			return valuesSum;
		}

		public void addChild(Node node){
			chields.add(node);
		}

		public static void setPointers(HashMap<Integer, Integer> pointers) {
			Node.pointers = pointers;
		}
	}

	public static ArrayList<Integer> parseNumbers(String stringNumbers){
		String[] numbersSplit = stringNumbers.split("\s");
		ArrayList<Integer> numbers = new ArrayList<>();
		for (String s : numbersSplit) {
			if (s.matches("\\d+")) {
				int number = Integer.parseInt(s);
				numbers.add(number);
			}
		}

		return numbers;
	}

	public void firstPart() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\day4Input.txt"));
		HashMap<Integer, Integer> pointers = new HashMap<>();
		int result = 0;

		int card = 1;
		while (reader.ready()) {
			String[] cardSplited = reader.readLine().split(":");
			String stringPrize = cardSplited[1].split("\\|")[0];
			String stringCandidates = cardSplited[1].split("\\|")[1];

			ArrayList<Integer> prize = parseNumbers(stringPrize);
			ArrayList<Integer> candidates = parseNumbers(stringCandidates);

			int counter = 0;
			float puntuation = 0.5f;
			for (Integer prizeNumber : prize) {
				for (Integer candidate : candidates) {
					if (prizeNumber.equals(candidate)) {
						puntuation = puntuation * 2;
						counter++;
					}
				}
			}

			pointers.put(card, counter);
			result += puntuation;
			card++;
		}

		Tree tree = new Tree(pointers); //PART TWO

		System.out.println(result);
		System.out.println(tree.partTwo()); //PART TWO
	}

	public static void main(String[] args) throws IOException {
		Day4 day4 = new Day4();
		day4.firstPart();
	}
}

