import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Application;

public class Game {
	
	private Block[][] levelLayout;
	
	public static void main(String[] args) {
		Application.launch(GUI.class, args);
	}
	
	/*
	 * Read from file to get level information
	 */
	public static void loadLevel(int level) {
		String levelPath = "levels/lv";
		if(level < 10) {
			levelPath += "0";
		}
		levelPath += level + ".txt";
		
		try {
			
		} catch (Exception e) {
			System.err.println("Error in loadLevel: \n" + e.getMessage());
		}
	}
	
}
