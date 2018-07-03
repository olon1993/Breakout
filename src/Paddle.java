import java.net.URL;
import javafx.scene.image.Image;

public class Paddle {
	
	// Image variables
	private final String path = "graphics/paddle_md.png";
	private final URL url = getClass().getResource(path);
	private Image image;
	
	// Game Variables
	private final int PADDLE_WIDTH = 112;
	private final int PADDLE_SPEED = 16;
	private final int PADDLE_Y = 400;
	private final int PADDLE_MAX_X = Game.CANVAS_WIDTH - PADDLE_WIDTH;
	private int locx, locy;
	
	public Paddle() {
		locx = PADDLE_MAX_X / 2;
		locy = PADDLE_Y;
	}
	
	public void init() {
		image = new Image(url.toString());
	}
	
	public void move(int direction) {
		locx += PADDLE_SPEED * direction;
		if(locx < 0) {
			locx = 0;
		} else if (locx > PADDLE_MAX_X) {
			locx = PADDLE_MAX_X;
		}
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
	
}
