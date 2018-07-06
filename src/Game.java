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
	public static final int SCORETEXT_X = 570;
	public static final int LIVESTEXT_X = 300;
	public static final int POINTS_PER_HIT = 10;
	public static final int INITIAL_LIVES = 3;
	public static final int SCORE_PER_LIFE = 3000;
	
	// Game Variables
	public static GameState gmState;
	public static GameState prePausedState;
	public static ArrayList<Block> activeBlocks;
	public static Block[][] level;
	public static Paddle paddle;
	public static Ball ball;
	public static String levelName;
	public static String levelTitle;
	public static int score;
	public static int livesRemaining;
	public int nextLifeScore;
	public String levelCounter;
	
	// Sound Variables
	public String backgroundMusicPath = "audio/background_music.wav";
	public String hitSoundPath = "audio/blip.wav";
	public String oneupSoundPath = "audio/oneup.wav";
	public String lifelostPath = "audio/lifelost.wav";
	public String gameoverPath = "audio/gameover.wav";
	public AudioClip backgroundMusic;
	public AudioClip hitSound;
	public AudioClip oneup;
	public AudioClip lifelost;
	public AudioClip gameover;
	
	public enum GameState {
		INITIALIZED,	// The initial state of the game, merely created
		LOADING, 		// The game objects and level are being loaded
		READY, 			// The game objects and level are finished loading
		PLAYING, 		// The game is actively being played, no win or loss
		PAUSED, 		// The game is inactive, still no win or loss
		WIN, 			// The level and or game has been completed
		LOSE,			// the player has lost a life
		GAMEOVER, 		// The player has lost the game
		RECAP,			// Post game over, scores are recorded
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
						
						// If there was a collision add points to score
						if(ball.detectCollision()) {
							score += POINTS_PER_HIT;
							
							// If score is above the score required to gain
							// a new life then the player gets a new life
							// and the score required to gain a new life 
							// increases
							if(score > nextLifeScore * SCORE_PER_LIFE) {
								livesRemaining += 1;
								nextLifeScore++;
								oneup.play();
							}
							
							hitSound.play();
							checkWin();
						}
						break;
						
					case PAUSED:
						break;
						
					case WIN:
						activeBlocks.clear();
						levelCounter = String.valueOf((Integer.parseInt(levelCounter) + 1));
						gmState = GameState.LOADING;
						break;
						
					case LOSE:
						lifelost.play();
						gmState = GameState.READY;
						break;
						
					case GAMEOVER:
						backgroundMusic.stop();
						gameover.play();
						gmState = GameState.RECAP;
						break;
						
					case RECAP:
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
			
			// Win if all the blocks have been broken
			if(activeBlocks.isEmpty()) {
				gmState = GameState.WIN;
			} else {
				
				// Win if all remaining blocks are stone blocks and
				// thereby unbreakable
				boolean allStone = true;
				for(int i = 0; i < activeBlocks.size(); i++) {
					if(activeBlocks.get(i).getHP() > 0) {
						allStone = false;
					}
				}
				if(allStone) {
					gmState = GameState.WIN;
				}
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
				score = 0;
				livesRemaining = INITIAL_LIVES;
				nextLifeScore = 1;
				
				URL url = getClass().getResource(backgroundMusicPath);
				backgroundMusic = new AudioClip(url.toString());
				backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
				backgroundMusic.play();
				
				url = getClass().getResource(hitSoundPath);
				hitSound = new AudioClip(url.toString());
				
				url = getClass().getResource(oneupSoundPath);
				oneup = new AudioClip(url.toString());
				
				url = getClass().getResource(lifelostPath);
				lifelost = new AudioClip(url.toString());
				
				url = getClass().getResource(gameoverPath);
				gameover = new AudioClip(url.toString());
				
				Game.paddle = new Paddle();
				Game.ball = new Ball();
			}
		}
	}
}
