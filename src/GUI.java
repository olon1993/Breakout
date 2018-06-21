import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;

public class GUI extends Application {

	private final int CANVAS_WIDTH = 640;
	private final int CANVAS_HEIGHT = 480;

	// GUI variables
	private Canvas canvas;
	private GraphicsContext gc;
	private StackPane root;
	
	// Graphics variables
	private Image background;
	
	// IO variables
	private String backgroundPath = "graphics/breakout_bg.png";
	
	public void start(Stage stage) {
		guiInit();
		sceneInit(stage);
	}
	
	/*
	 * Constructor that prepares the GUI for use
	 */
	public void guiInit() {
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		loadGraphics();
		paintGame();
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
		stage.setScene(scene);
		stage.show();
	}
	
	/*
	 * Paints the images on the screen
	 */
	public void paintGame() {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		gc.drawImage(background, 0, 0);
	}
	
	/*
	 * Loads the graphics into memory
	 */
	public void loadGraphics() {
		try {
			URL url = getClass().getResource(backgroundPath);
			background = new Image(url.toString());
		} catch (Exception e) {
			
		}
	}
	
}
