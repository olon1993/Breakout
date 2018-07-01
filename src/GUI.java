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
	
	// Image variables
	private final String path = "graphics/breakout_bg.png";
	private final URL url = getClass().getResource(path);
	private Image background = new Image(url.toString());
	
	// GUI variables
	private Canvas canvas;
	private GraphicsContext gc;
	private StackPane root;
	
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
	 * Constructor that prepares the GUI for use and calls the
	 * loadGraphics method.
	 */
	public void guiInit() {
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
				case LEFT:
					Game.paddle.move(-1);
					break;
				case RIGHT:
					Game.paddle.move(1);
					break;
				default:
					break;
			}
		});
		
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Paints the images on the screen
	 */
	public void paintGame() {
		if(Game.isReady) {
			gc.clearRect(0, 0, Game.CANVAS_WIDTH, Game.CANVAS_HEIGHT);
			gc.drawImage(background, 0, 0);
			gc.drawImage(Game.paddle.getImage(), Game.paddle.getLocX(), Game.paddle.getLocY());
			gc.drawImage(Game.ball.getImage(), Game.ball.getLocX(), Game.ball.getLocY());
			for(int i = 0; i < Game.LEVEL_WIDTH; i++) {
				for( int j = 0; j < Game.LEVEL_HEIGHT; j++) {
					if(Game.level[i][j].getIsActive()) {
						gc.drawImage(Game.level[i][j].getImage(), 
									 Game.level[i][j].getLocX() * 32 + 32, 
									 Game.level[i][j].getLocY() * 32 + 32);
					}
				}
			}
		}
	}
	
}
