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
	public static final int BLOCK_WIDTH = 32;
	public static final int BLOCK_HEIGHT = 32;
	private int locx, locy, hp;
	private boolean isActive;
	private Image image;
	
	public Block(int hp, int locx, int locy) {
		switch(hp) {
			case 0:
				this.isActive = false;
				break;
			case 1:
				this.isActive = true;
				this.url = getClass().getResource(redPath);
				this.image = new Image(url.toString());
				break;
			case 2:
				this.isActive = true;
				this.url = getClass().getResource(ylwPath);
				this.image = new Image(url.toString());
				break;
			case 3:
				this.isActive = true;
				this.url = getClass().getResource(prpPath);
				this.image = new Image(url.toString());
				break;
			case 4:
				this.isActive = true;
				this.url = getClass().getResource(bluPath);
				this.image = new Image(url.toString());
				break;
			case 5:
				this.isActive = true;
				this.url = getClass().getResource(grnPath);
				this.image = new Image(url.toString());
				break;
			case -1:
				this.isActive = true;
				this.url = getClass().getResource(rckPath);
				this.image = new Image(url.toString());
				break;
			default:
				this.isActive = true;
				this.url = getClass().getResource(rckPath);
				this.image = new Image(url.toString());
				break;
		}
		
		this.hp = hp;
		this.locx = locx * BLOCK_WIDTH + BLOCK_WIDTH;
		this.locy = locy * BLOCK_HEIGHT + BLOCK_HEIGHT;
	}
	
	public void hit() {
		this.hp -= 1;
		if(this.hp == 0) {
			this.isActive = false;
		}
	}
	
	////////////////////////////////////////////////////////////////////
	// 						 GETTERS / SETTERS 						  //
	////////////////////////////////////////////////////////////////////
	
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
