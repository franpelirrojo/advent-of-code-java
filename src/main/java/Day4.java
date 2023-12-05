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
		private final Node root = new Node(0);

		public Tree(HashMap<Integer, Integer> pointers) {
			Node.setPointers(pointers);
			for (int k = 1; k < pointers.size(); k++){
				if (pointers.get(k) != 0){
					Node node = new Node(k, pointers.get(k)); //build the next node
					node.setParent(root);
					root.addChild(node);
				}
			}
		}

		public int partTwo(){
			root.getChieldsValues().forEach((k, v) -> System.out.println(k + ": " + v));
			return 0;
		}
	}

	class Node {
		private int value;
		private Node parent;
		private ArrayList<Node> chields = new ArrayList<>();
		private static HashMap<Integer, Integer> pointers;

		public Node(int value){
			this.value = value;
		}

		public Node(int value, int connections) {
			this.value = value;

			for (int k = value + 1; k <= value + connections; k++){ //build the next node
				if (!pointers.get(k).equals(0)){
					Node chield = new Node(k, pointers.get(k));
					chield.setParent(this);
					addChild(chield);
				}
			}
		}

		public int getValue() {
			return value;
		}

		public HashMap<Integer, Integer> getChieldsValues() {
			HashMap<Integer, Integer> values = new HashMap<>();
			if (!chields.isEmpty()){
				for (Node child : chields){
					HashMap<Integer, Integer> childValues = child.getChieldsValues();
					childValues.forEach((k, v) ->{
						if (values.containsKey(k)){
							values.put(k, values.get(k) + v);
						}
					});
				}
			}

			values.put(getValue(), values.get(getValue()) == null ? 1 : values.get(getValue()) + 1);
			return values;
		}

		public void addChild(Node node){
			chields.add(node);
		}

		public static void setPointers(HashMap<Integer, Integer> pointers) {
			Node.pointers = pointers;
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
		System.out.println(tree.partTwo());
		System.out.println(result);
	}

	public static void main(String[] args) throws IOException {
		Day4 day4 = new Day4();
		day4.firstPart();
	}
}

