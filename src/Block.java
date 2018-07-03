import java.net.URL;

import javafx.scene.image.Image;

public class Block {

	// Image variables
	private final String redPath = "graphics/red_block.png";
	private final String ylwPath = "graphics/ylw_block.png";
	private final String prpPath = "graphics/prp_block.png";
	private final String bluPath = "graphics/blu_block.png";
	private final String grnPath = "graphics/grn_block.png";
	private final String rckPath = "graphics/rck_block.png";
	private URL url;
	
	// Game Variables
	private int locx, locy, hp;
	private boolean isActive;
	private Image image;
	
	public Block(int hp) {
		switch(hp) {
			case 1:
				this.url = getClass().getResource(redPath);
				break;
			case 2:
				this.url = getClass().getResource(ylwPath);
				break;
			case 3:
				this.url = getClass().getResource(prpPath);
				break;
			case 4:
				this.url = getClass().getResource(bluPath);
				break;
			case 5:
				this.url = getClass().getResource(grnPath);
				break;
			case -1:
				this.url = getClass().getResource(rckPath);
				break;
			default:
				this.url = getClass().getResource(rckPath);
				break;
		}
		
		this.image = new Image(url.toString());
		this.hp = hp;
	}
	
	public void init() {
		this.image = new Image(url.toString());
	}
	
	public int getLocX() {
		return this.locx;
	}
	
	public int getLocY() {
		return this.locy;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public void setLocX(int locx) {
		this.locx = locx;
	}
	
	public void setLocY(int locy) {
		this.locy = locy;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
