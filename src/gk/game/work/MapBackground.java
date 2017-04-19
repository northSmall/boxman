package gk.game.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * map background
 * @author user
 *
 */
public class MapBackground {
	/* game num */
	private int level;
	/* man xAxis */
	private int manX;
	/* man yAxis */
	private int manY;
	private int[][] mapPic = new int[20][20];
	FileReader fileReader;
	BufferedReader br;
	StringBuilder sBuilder = new StringBuilder();
	int count = 0;

	public MapBackground(int game) {
		level = game;
		String buffer;
		try {
			File file = new File("game_maps\\" + level + ".map");
			fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);
			while ((buffer = br.readLine()) != null) {
				sBuilder.append(buffer);
			}
			String res = sBuilder.toString();
			int length = res.length();
			int[] array = new int[length];
			for (int i = 0; i < res.length(); i++) {
				//read bytes of the map file,and put them in the arrays.
				array[i] = res.getBytes()[i] - 48;
			}
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					mapPic[i][j] = array[count];
					if (mapPic[i][j] == 5) {
						//sets the man's initial xAxis and yAxis
						manX = j;
						manY = i;
					}
					count++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[][] getMap() {
		return mapPic;
	}

	public int getManX() {
		return manX;
	}

	public int getManY() {
		return manY;
	}
}
