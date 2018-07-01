import javafx.application.Application;

public class Game {
	
	// Game Options
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final int LEVEL_WIDTH = 18;
	public static final int LEVEL_HEIGHT = 9;
	
	// Game Variables
	public static Block[] blocks;
	public static Block[][] level;
	public static Paddle paddle;
	public static Ball ball;
	public static boolean isReady;
	
	public Game(String[] args) {
		isReady = false;
		GameLoop gameloop = new GameLoop();
		Thread gamethread = new Thread(gameloop);
		gamethread.start();
		Application.launch(GUI.class, args);
	}
	
	public static void main(String[] args) {
		new Game(args);
	}
	
	private class GameLoop implements Runnable{
		
		public void run() {
			gameInit();
		}
		
		public void gameInit() {
			paddle = new Paddle();
			ball = new Ball();
			blocks = new Block[] {
					new Block("Red", 1),
					new Block("Yellow", 2),
					new Block("Purple", 3),
					new Block("Blue", 4),
					new Block("Green", 5),
					new Block("Rock", -1)
			};
			
			//loadLevel(1);
			isReady = true;
		}
		
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
