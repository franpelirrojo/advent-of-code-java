/*
---FIRST PART---
The Elf would first like to know which games would have been possible if the bag
contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded
with that configuration. However, game 3 would have been impossible because at one point the
Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because
the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have
been possible, you get 8.

Determine which games would have been possible if the bag had been loaded with only 12 red cubes,
13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
---SECOND PART---
As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest
number of cubes of each color that could have been in the bag to make the game possible? The power of a set
of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum
set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five
powers produces the sum 2286.

For each game, find the minimum set of cubes that must have been present.
What is the sum of the power of these sets? */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class day2 {
	private static final int RED = 12;
	private static final int GREEN = 13;
	private static final int BLUE = 14;

	public static void secondPart() {
		BufferedReader reader;
		int powerSum = 0;

		try {
			reader = new BufferedReader(new FileReader("day2Input.txt"));

			while (reader.ready()) {
				String[] line = reader.readLine().split(":");
				String[] shots = line[1].split(";");

				int red = 0;
				int green = 0;
				int blue = 0;
				for (String shot : shots) {
					String[] balls = shot.split(",");
					for (String ball : balls) {
						String[] content = ball.split("\s");
						switch (content[2]) {
							case "red" -> red = Math.max(red, Integer.parseInt(content[1]));
							case "green" -> green = Math.max(green, Integer.parseInt(content[1]));
							case "blue" -> blue = Math.max(blue, Integer.parseInt(content[1]));
						}
					}

				}

				powerSum += red * green * blue;
			}

			System.out.println(powerSum);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void firstPart() {
		BufferedReader reader;
		boolean valid;
		int red = 0;
		int green = 0;
		int blue = 0;
		int idSum = 0;

		try {
			reader = new BufferedReader(new FileReader("day2Input.txt"));

			while (reader.ready()) {
				valid = true;
				String[] line = reader.readLine().split(":");
				String[] shots = line[1].split(";");

				String id = line[0].split("\s")[1];

				for (String shot : shots) {
					String[] balls = shot.split(",");
					for (String ball : balls) {
						String[] content = ball.split("\s");
						switch (content[2]) {
							case "red" -> red = Integer.parseInt(content[1]);
							case "green" -> green = Integer.parseInt(content[1]);
							case "blue" -> blue = Integer.parseInt(content[1]);
						}
					}

					if (red > RED || green > GREEN || blue > BLUE) {
						valid = false;
					}
				}

				if (valid){
					idSum += Integer.parseInt(id);
				}
			}

			System.out.println(idSum);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		secondPart();
	}
}
