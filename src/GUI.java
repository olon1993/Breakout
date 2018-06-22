import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URL;

public class GUI extends Application{

	private final int CANVAS_WIDTH = 640;
	private final int CANVAS_HEIGHT = 480;
	private final int BLOCK_TYPES = 6;

	// GUI variables
	private Canvas canvas;
	private GraphicsContext gc;
	private StackPane root;
	private URL url;
	
	// Graphics variables
	private Image background;
	private Image paddle;
	private Image ball;
	private Image[] blocks;
	
	// IO variables
	private String backgroundPath = "graphics/breakout_bg.png";
	private String paddlePath = "graphics/paddle_sm.png";
	private String ballPath = "graphics/ball_sm.png";
	private String bluBlockPath = "graphics/blu_block.png";
	private String grnBlockPath = "graphics/grn_block.png";
	private String prpBlockPath = "graphics/prp_block.png";
	private String redBlockPath = "graphics/red_block.png";
	private String ylwBlockPath = "graphics/ylw_block.png";
	private String rckBlockPath = "graphics/rck_block.png";
	
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
		gc.drawImage(paddle, 300, 400);
		gc.drawImage(ball, 260, 350);
		for(int i = 0; i < 18; i++) {
			for( int j = 0; j < 9; j++) {
				gc.drawImage(blocks[0], i * 32 + 32, j * 32 + 32);
			}
		}
	}
	
	/*
	 * Loads the graphics into memory
	 */
	public void loadGraphics() {
		blocks = new Image[BLOCK_TYPES];
		try {
			url = getClass().getResource(backgroundPath);
			background = new Image(url.toString());
			
			url = getClass().getResource(paddlePath);
			paddle = new Image(url.toString());
			
			url = getClass().getResource(ballPath);
			ball = new Image(url.toString());
			
			url = getClass().getResource(redBlockPath);
			blocks[0] = new Image(url.toString());
			
			url = getClass().getResource(ylwBlockPath);
			blocks[1] = new Image(url.toString());
			
			url = getClass().getResource(prpBlockPath);
			blocks[2] = new Image(url.toString());
			
			url = getClass().getResource(bluBlockPath);
			blocks[3] = new Image(url.toString());
			
			url = getClass().getResource(grnBlockPath);
			blocks[4] = new Image(url.toString());
			
			url = getClass().getResource(rckBlockPath);
			blocks[5] = new Image(url.toString());
		} catch (Exception e) {
			System.err.println("Error in loadGraphics: \n" + e.getMessage());
		}
	}
	
}
