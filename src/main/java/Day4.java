import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day4 {
	/*
	PART TWO
	 */
	class Tree{
		private ArrayList<Node> nodes = new ArrayList<>();;
		private final Node root = new Node(0);

		public Tree(HashMap<Integer, Integer> pointers) {
			Node.setCache(pointers);
			for (int k = 1; k < pointers.size(); k++){
				if (pointers.get(k) != 0){
					Node node = new Node(k + 1, pointers.get(k + 1)); //build the next node
					node.setParent(root);
					root.addChield(node);
					add(node);
				}
			}
		}

		public ArrayList<Integer> partTwo(){
			ArrayList<Integer> frequency = root.getChieldsValues();
		}

		private void add(Node node){
			nodes.add(node);
		}
	}

	class Node {
		private int value;
		private Node parent;
		private ArrayList<Node> chields = new ArrayList<>();
		private static HashMap<Integer, Integer> cache;

		public Node(int value){
			this.value = value;
		}

		public Node(int value, int conections) {
			this.value = value;

			for (int k = value + 1; k <= value + conections; k++){ //build the next node
				if (!cache.get(k).equals(0)){
					Node chield = new Node(k, cache.get(k));
					chield.setParent(this);
					addChield(chield);
				}
			}
		}

		public int getValue() {
			return value;
		}

		public ArrayList<Integer> getChieldsValues() {
			ArrayList<Integer> values = new ArrayList<>();
			if (!chields.isEmpty()){
				for (Node chield : chields){
				}
			} else {
				return null;
			}
		}

		public void addChield(Node node){
			chields.add(node);
		}

		public static void setCache(HashMap<Integer, Integer> cache) {
			Node.cache = cache;
		}

		public void setParent(Node parent) {
			this.parent = parent;
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

		Tree tree = new Tree(pointers);

		System.out.println(result);
	}

	public static void main(String[] args) throws IOException {
		Day4 day4 = new Day4();
		day4.firstPart();
	}
}

