import java.net.URL;
import javafx.scene.image.Image;

public class Ball {

	// Image Variables
	private final String path = "graphics/ball_sm.png";
	private final URL url = getClass().getResource(path);
	private Image image = new Image(url.toString());
	
	// Game Variables
	private final int BALL_WIDTH = 16;
	private final int BALL_HEIGHT = 16;
	private final int BALL_MAX_X = Game.CANVAS_WIDTH - BALL_WIDTH;
	private final int BALL_MAX_Y = Game.CANVAS_HEIGHT + BALL_HEIGHT;
	private final int BALL_SPEED = 16;
	private int locx, locy;
	
	public Ball() {
		
	}
	
	public int getLocX() {
		return this.locx;
	}
	
	public int getLocY() {
		return this.locy;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setLocX(int locx) {
		this.locx = locx;
		if(this.locx < 0) {
			this.locx = 0;
		} else if(this.locx > BALL_MAX_X) {
			this.locx = BALL_MAX_X;
		}
	}
	
	public void setLocY(int locy) {
		this.locy = locy;
		if(this.locy < 0) {
			this.locy = 0;
		} else if(this.locy > BALL_MAX_Y) {
			this.locy = BALL_MAX_Y;
		}
	}
}
