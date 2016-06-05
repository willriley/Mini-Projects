import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	// Width and height of application window in pixels 
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	// Dimensions of game board (usually the same) 
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	private static final int PADDLE_Y_OFFSET = 30;
	private GRect paddle;
	private static final int PADDLE_Y_POS = APPLICATION_HEIGHT - (2*PADDLE_Y_OFFSET);
	// ^ paddle's y coordinate

	private static final int NBRICKS_PER_ROW = 10;
	private static final int NBRICK_ROWS = 10;
	private static final int NTURNS = 3;
	
	private static final int BRICK_SEP = 4; // # of pixels between bricks
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	private static final int BRICK_HEIGHT = 8;
	private static final int BRICK_Y_OFFSET = 70;
	
	private GOval ball;
	private static final int BALL_RADIUS = 10;
	private static final int BALL_DELAY = 10; // ms delay for ball animation
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private int bricksLeft = NBRICKS_PER_ROW * NBRICK_ROWS;
	private int livesLeft = NTURNS;
	private int score = 0;
	
	private GLabel livesLabel;
	private GLabel scoreLabel;
	private static final int LABEL_X_OFFSET = 20;
	private static final int LABEL_Y_OFFSET = 15;
	
	// controls incr/decr of velocity after each collision
	private static final double BOUNCE_ACCEL = 1.03; 
													
	
	public void run() {
		setup();
		play();
	}
	
	private void setup() {
		drawBricks();
		drawPaddle();
		drawBall();
		drawLabels();
	}
	
	private void play() {
		while (livesLeft>0 && bricksLeft>0) {
			moveBall();
		}
		gameOver();
	}
	
	private void gameOver() {
		removeAll();
		displayResults(livesLeft>0);
		
		/*
		GLabel endText;
		if (livesLeft == 0) {
			endText = new GLabel("You Lost ");
		} else if (bricksLeft == 0) {
			endText = new GLabel("YOU WIN!!!");
		} else {
			endText = new GLabel("ERROR");
		}
		endText.setFont("Serif-50");
		add(endText, WIDTH/2-endText.getWidth()/2, HEIGHT/2-endText.getAscent());
		*/
	}
	
	private void displayResults(Boolean b) {
		GLabel result = new GLabel("Placeholder Text");
		result.setFont("Serif-50");
		scoreLabel.setFont("Serif-20");
		add(scoreLabel, WIDTH/2 - scoreLabel.getWidth()/2, HEIGHT/2 - scoreLabel.getAscent());
		if (b) 
			result.setLabel("You won!");
		else 
			result.setLabel("You lost :(");
		
		add(result, WIDTH/2 - result.getWidth()/2, HEIGHT/2 - result.getAscent());
	}
	
	private void drawBricks() {
		for (int row=0; row<NBRICK_ROWS; row++) {
			for (int col=0; col<NBRICKS_PER_ROW; col++) {
				
				int x = (BRICK_WIDTH + BRICK_SEP)*col;
				int y = BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP)*row;
				GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				
				brick.setFilled(true);
				brick.setColor(getBrickColor(row));
				add(brick);
			}
		}
	}
	
	private void drawPaddle() {
		paddle = new GRect(WIDTH/2 - PADDLE_WIDTH/2, PADDLE_Y_POS,
				PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}
	
	private void drawBall() {
		ball = new GOval(WIDTH/2 - BALL_RADIUS,HEIGHT/2 + BALL_RADIUS,2*BALL_RADIUS,2*BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
		waitForClick();
		setVelocity();
	}
	
	private void drawLabels() {
		livesLabel = new GLabel("Lives:  " + livesLeft);
		add(livesLabel,WIDTH-LABEL_X_OFFSET-livesLabel.getWidth(),LABEL_Y_OFFSET);
		scoreLabel = new GLabel("Score: " + score); 
		add(scoreLabel,LABEL_X_OFFSET, LABEL_Y_OFFSET);
	}
	
	private void moveBall() {
		ball.move(vx, vy);
		pause(BALL_DELAY); // delays animation
		checkWalls();
			
		// if the colliding object returned is the paddle, bounces the ball
		// if it's something else, i.e. a brick, then removes it & bounces the ball
		GObject collided = getCollidingObj();
		if (collided == paddle) {
			vy = -vy;
		} else if (collided != null && collided != livesLabel
				&& collided != scoreLabel) {
			remove(collided); 
			vy*=-BOUNCE_ACCEL;
			bricksLeft--;
			scoreHit(collided);
			updateLabels();
		}
	}
	
	private void scoreHit(GObject brick) {
		if (brick.getColor() == Color.CYAN)
			score+=5;
		else if (brick.getColor() == Color.GREEN)
			score+=10;
		else if (brick.getColor() == Color.YELLOW)
			score+=15;
		else if (brick.getColor() == Color.ORANGE)
			score+=20;
		else if (brick.getColor() == Color.RED)
			score+=25;
	}
	
	private void checkWalls() {
		// if the ball hits the top wall, reverses sign of vy
		// if it hits the side walls, reverses sign of vx
		// if it hits the bottom wall, takes off a life & resets position
		if (ball.getX() <= 0 || (ball.getX() + 2*BALL_RADIUS) >= WIDTH) {
			vx*=-BOUNCE_ACCEL;
			vy*=BOUNCE_ACCEL;
		} else if (ball.getY() <= 0) {
			vy*=-BOUNCE_ACCEL;
			vx*=BOUNCE_ACCEL;
		} else if ((ball.getY() + 2*BALL_RADIUS) >= HEIGHT) {
			ball.setLocation(WIDTH/2 - BALL_RADIUS, HEIGHT/2 + BALL_RADIUS);
			setVelocity();
			livesLeft--;
			waitForClick();
			updateLabels();
		}		
	}
	
	private void setVelocity() {
		vy = 2.0;
		vx = rgen.nextDouble(1.0, 2.0);
		if(rgen.nextBoolean(0.5)) vx = -vx;
	}
	
	private GObject getCollidingObj() { 		
		// imagines the circular ball as a square with four corner points
		// checks these points for collisions, returning the brick if there's one
		for (int i=0; i<4; i++) {
			if(getElementAt(getPoint(i)) != null)
				return getElementAt(getPoint(i));
		}
		return null;
	}
	
	private GPoint getPoint(int n) {
		switch(n) {
			case 0: return new GPoint(ball.getX(), ball.getY());
			case 1:	return new GPoint(ball.getX() + 2*BALL_RADIUS, ball.getY());
			case 2: return new GPoint(ball.getX(), ball.getY() + 2*BALL_RADIUS);
			case 3:	return new GPoint(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS);
			default: return null;
		}
	}
	
	private Color getBrickColor(int row) {
		if (row==0 || row==1)
			return Color.RED;
		else if (row==2 || row==3)
			return Color.ORANGE;
		else if (row==4 || row==5)
			return Color.YELLOW;
		else if (row==6 || row==7)
			return Color.GREEN;
		else 
			return Color.CYAN;
	}
	
	private void updateLabels() {
		remove(livesLabel);
		remove(scoreLabel);
		drawLabels();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {	
		
		// calibrates paddle position w/ mouse movements
		if (e.getX()>=0 && e.getX()<=WIDTH-PADDLE_WIDTH) 
			paddle.setLocation(e.getX(), PADDLE_Y_POS);
		else if (e.getX()>WIDTH-PADDLE_WIDTH)
			paddle.setLocation(WIDTH-PADDLE_WIDTH, PADDLE_Y_POS);
	}
}