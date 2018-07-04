import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;

public class GUI extends Application{
	
	// Image Variables
	private final String path = "graphics/breakout_bg.png";
	private final URL url = getClass().getResource(path);
	private Image background = new Image(url.toString());
	
	// GUI Variables
	public static GraphicsState gcState;
	private Canvas canvas;
	private GraphicsContext gc;
	private StackPane root;
	
	public enum GraphicsState {
		INITIALIZED, 	// The initial state of the GUI, merely created
		READY			// The GUI is ready for use
	}
	
	public void start(Stage stage) {
		guiInit();
		sceneInit(stage);
		new AnimationTimer() {
				@Override
				public void handle(long now) {
					paintGame();
				}
		}.start();
	}
	
	/*
	 * Paints the images on the screen
	 */
	public void paintGame() {
		gc.clearRect(0, 0, Game.CANVAS_WIDTH, Game.CANVAS_HEIGHT);
		gc.drawImage(background, 0, 0);
		
		// Only draw the game images when the game is ready to be played
		if(Game.gmState == Game.GameState.READY || 
		   Game.gmState == Game.GameState.PLAYING ||
		   Game.gmState == Game.GameState.PAUSED) {
			gc.drawImage(Game.paddle.getImage(), Game.paddle.getLocX(), Game.paddle.getLocY());
			gc.drawImage(Game.ball.getImage(), Game.ball.getLocX(), Game.ball.getLocY());
			for( int i = 0; i < Game.activeBlocks.size(); i++ ) {
				gc.drawImage(Game.activeBlocks.get(i).getImage(), 
							 Game.activeBlocks.get(i).getLocX(), 
							 Game.activeBlocks.get(i).getLocY());
			}
		}
	}
	
	public void stop() {
		Game.gmState = Game.GameState.EXIT;
	}
	
	////////////////////////////////////////////////////////////////////
	// 								INIT 							  //
	////////////////////////////////////////////////////////////////////
	
	/*
	 * Constructor that prepares the GUI for use and calls the
	 * loadGraphics method.
	 */
	public void guiInit() {
		gcState = GraphicsState.INITIALIZED;
		canvas = new Canvas(Game.CANVAS_WIDTH, Game.CANVAS_HEIGHT);
		gc = canvas.getGraphicsContext2D();
	}
	
	/*
	 * Constructs the scene
	 */
	public void sceneInit(Stage stage) {
		stage = new Stage();
		stage.setTitle("Breakout!");
		
		root = new StackPane();
		root.getChildren().add(canvas);
		
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(e -> {
			switch(e.getCode()) {
				case SPACE:
					if(Game.gmState == Game.GameState.READY ||
					   Game.gmState == Game.GameState.PLAYING) {
						Game.gmState = Game.GameState.PAUSED;
						System.out.println("PAUSED");
					} else if(Game.gmState == Game.GameState.PAUSED) {
						Game.gmState = Game.GameState.PLAYING;
						System.out.println("PLAYING");
					}
					break;
				case UP:
					if(Game.gmState != Game.GameState.PAUSED) {
						Game.ball.launch();
					}
					break;
				case LEFT:
					if(Game.gmState != Game.GameState.PAUSED) {
						Game.paddle.move(-1);
					}
					break;
				case RIGHT:
					if(Game.gmState != Game.GameState.PAUSED) {
						Game.paddle.move(1);
					}
					break;
				default:
					break;
			}
		});

		gcState = GraphicsState.READY;
		stage.setScene(scene);
		stage.show();
	}
}
