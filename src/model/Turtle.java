package model;

import gui.init.canvas.IReset;
import gui.turtle.IChangeImage;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Turtle implements IChangeImage, IReset {
	
	public IntegerProperty move = new SimpleIntegerProperty();
	public DoubleProperty initX = new SimpleDoubleProperty();
	public DoubleProperty initY = new SimpleDoubleProperty();
	private ImageView image;
	private Image defaultImage = new Image(getClass().getClassLoader().getResourceAsStream("turtle.png"));
	private double x, y;
	private double direction;
	private int isPenDown, isVisible;
	private int id;
	private boolean isActive;
	
	public Turtle() {
		image = new ImageView(defaultImage);
		image.setFitHeight(50);
		image.setPreserveRatio(true);
		x = initX.get();
		y = initY.get();
		move.set(0);
		image.setX(x + initX.get() - image.getBoundsInLocal().getWidth()/2);
		image.setY(y + initY.get() - image.getBoundsInLocal().getHeight()/2);
		direction = 0;
		isPenDown = 1;
		isVisible = 1;
	}
	
	public Turtle(double x, double y) {
		this();
		setX(x);
		setY(y);
	}
	
	public Turtle(double x, double y, Image img) {
		this(x, y);
		setImage(img);
	}

	// getter methods:
	public ImageView getImage() {
		return image;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public int getIsPenDown() {
		return isPenDown;
	}
	
	public int getIsVisible() {
		return isVisible;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	// setter methods:
	@Override
	public void setImage(Image img) {
		this.image.setImage(img);
	}
	
	public void setImage(ImageView image) {
		this.image = image;
	}

	public void setImage(String s) {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("turtle.png")));
		this.image = image;
	}
	
	public void setX(double x) {
		this.x = x;
		image.setX(x + initX.get() - image.getBoundsInLocal().getWidth()/2);
	}
	
	public void setY(double y) {
		this.y = y;
		image.setY(y + initY.get() - image.getBoundsInLocal().getHeight()/2);
	}
	
	public void rotate(double angle) {
		image.setRotate(image.getRotate() + angle);
		direction = image.getRotate();
	}
	
	public void setPenDown() {
		isPenDown = 1;
	}
	
	public void setPenUp() {
		isPenDown = 0;
	}
	
	public void setVisible() {
		image.setVisible(true);
		isVisible = 1;
	}
	
	public void setInvisible() {
		image.setVisible(false);
		isVisible = 0;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public void reset() {
		int save = isPenDown;
		setPenUp();
		setX(0);
		setY(0);
		rotate(- this.getDirection());
		if (save == 1) setPenDown();
	}
}
