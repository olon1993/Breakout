import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.media.AudioClip;

public class Game {
	
	// Game Options
	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;
	public static final int LEVEL_WIDTH = 18;
	public static final int LEVEL_HEIGHT = 9;
	public static boolean isSounding = true;
	
	// Game Variables
	public static GameState gmState;
	public static ArrayList<Block> activeBlocks;
	public static Block[][] level;
	public static Paddle paddle;
	public static Ball ball;
	public static String levelName;
	public static String levelTitle;
	public String levelCounter;
	
	// Sound Variables
	public String backgroundMusicPath = "audio/background_music.wav";
	public String hitSoundPath = "audio/blip.wav";
	public AudioClip backgroundMusic;
	public AudioClip hitSound;
	
	public enum GameState {
		INITIALIZED,	// The initial state of the game, merely created
		LOADING, 		// The game objects and level are being loaded
		READY, 			// The game objects and level are finished loading
		PLAYING, 		// The game is actively being played, no win or loss
		PAUSED, 		// The game is inactive, still no win or loss
		WIN, 			// The level and or game has been completed
		GAMEOVER, 		// The player has lost the game
		EXIT			// The application is closing
	}
	
	public Game(String[] args) {
		gmState = GameState.INITIALIZED;
		Thread gamethread = new Thread( new GameLoop() );
		gamethread.start();
		Application.launch(GUI.class, args);
	}
	
	public static void main(String[] args) {
		new Game(args);
	}
	
	/*
	 * This class handles all of the game logic.
	 */
	private class GameLoop implements Runnable{
		
		public void run() {
			while(true) {
				switch(gmState) {
					case INITIALIZED:
						gameInit();
						break;
					case LOADING:
						loadLevel(levelCounter);
						gmState = GameState.READY;
						break;
					case READY:
						paddle.init();
						ball.init();
						break;
					case PLAYING:
						paddle.move();
						ball.move();
						if(ball.detectCollision()) {
							hitSound.play();
							checkWin();
						}
						break;
					case PAUSED:
						break;
					case WIN:
						levelCounter = String.valueOf((Integer.parseInt(levelCounter) + 1));
						gmState = GameState.LOADING;
						break;
					case GAMEOVER:
						break;
					case EXIT:
						System.exit(0);
						break;
				}
				
				try {
					Thread.sleep(60);
				} catch (Exception e) {
					System.out.println("Exception in GameLoop.run :\n"  + e.getMessage());
				}
			}
		}

		/*
		 * Read from file to get level information
		 */
		public void loadLevel(String levelNumber) {
			if(Integer.parseInt(levelNumber) < 10) {
				levelNumber = "0" + levelNumber;
			}
			
			String levelPath = "src/levels/lv" + levelNumber + ".txt";
			try {
				File file = new File(levelPath);
				BufferedReader bfr = new BufferedReader( new FileReader(file) );
				String input;
				String[] bufferedInput;
				
				// The first two lines contain the name and title of the level
				levelName = bfr.readLine();
				levelTitle = bfr.readLine();
				
				int i = 0;
				while( (input = bfr.readLine()) != null ) {
					bufferedInput = input.split(",");
					
					for(int j = 0; j < LEVEL_WIDTH; j++) {
						level[i][j] = new Block(Integer.parseInt(bufferedInput[j]), j, i);
						
						// Add active blocks to the activeBlocks arraylist
						// so there are fewer blocks to search through for 
						// collisions
						if(level[i][j].getIsActive()) {
							activeBlocks.add(level[i][j]);
						}
					}
					i++;
				}
				
				bfr.close();
				
			} catch (Exception e) {
				System.err.println("Error in loadLevel: \n" + e.getMessage());
			}
		}
		
		public void checkWin() {
			if(activeBlocks.isEmpty()) {
				gmState = GameState.WIN;
			}
			
		}
		
	////////////////////////////////////////////////////////////////////
	// 								INIT 							  //
	////////////////////////////////////////////////////////////////////
		
		public void gameInit() {
			if(GUI.gcState == GUI.GraphicsState.READY) {
				gmState = GameState.LOADING;
				levelCounter = "1";
				level = new Block[LEVEL_HEIGHT][LEVEL_WIDTH];
				activeBlocks = new ArrayList<Block>();
				
				URL url = getClass().getResource(backgroundMusicPath);
				backgroundMusic = new AudioClip(url.toString());
				backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
				backgroundMusic.play();
				
				url = getClass().getResource(hitSoundPath);
				hitSound = new AudioClip(url.toString());
				
				Game.paddle = new Paddle();
				Game.ball = new Ball();
			}
		}
	}
}
