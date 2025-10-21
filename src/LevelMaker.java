// Author: Gavin Collins
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class LevelMaker {
	
	private double ballX;
	private double ballY;
    private Group root;
    private List<Brick> bricks;
    private ArrayList<Slider> sliderList = new ArrayList<>();
    private ArrayList<Rectangle> outBoundsList = new ArrayList<>();
    private ArrayList<Node> nodeList = new ArrayList<>();

    public LevelMaker(Group root, List<Brick> bricks) {
        this.root = root;
        this.bricks = bricks;
    }
    
    public ArrayList<Slider> getSliderList() {
    	return sliderList;
    }
    
    public ArrayList<Rectangle> getOutOfBounds() {
    	return outBoundsList;
    }
    
    public void resetLevel() { 
    	if (!nodeList.isEmpty()) {
    		root.getChildren().removeAll(nodeList);
    		nodeList.clear();
    	}
    	if (!bricks.isEmpty()) {
    		for (Brick b : new ArrayList<>(bricks)) {
                root.getChildren().remove(b.getBrick());
            }
            bricks.clear();
    	}
    	sliderList.clear();
    	outBoundsList.clear();
    	ballX  = 0;
    	ballY = 0;
    }
    
    public void addSlider(double startX, double startY) {
    	Slider s = new Slider(startX, startY);
        sliderList.add(s);
        root.getChildren().add(s.getNode());
        nodeList.add(s.getNode());
    }
    
    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        outBoundsList.add(r);
        root.getChildren().add(r);
        nodeList.add(r);
    }
    public double getBallX() {
    	return ballX;
    }
    
    public double getBallY() {
    	return ballY;
    }

    public void makeLevelOne() {
    	addSlider(360, 500);
        addOutOfBounds(0,580,800,20,Color.RED);
        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 35;
        double startY = 60;
        double brickGap = 10;
        int pointValue = 1;
        ballX = 400;
        ballY = 400;

        int [][] sixPattern = {
            {1,1,1,1},
            {1,0,0,0},
            {1,0,0,0},
            {1,0,0,0},
            {1,1,1,1},
            {1,0,0,1},
            {1,0,0,1},
            {1,0,0,1},
            {1,0,0,1},
            {1,1,1,1}
        };

        int [][] sevenPattern = {
            {1,1,1,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1},
            {0,0,0,1}
        };

  
        for (int row = 0; row < sixPattern.length; row++) {
            for (int col = 0; col < sixPattern[0].length; col++) {
                if (sixPattern[row][col] == 1) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }

       
        double sevenOffsetX = startX + 5 * (brickWidth + brickGap);
        for (int row = 0; row < sevenPattern.length; row++) {
            for (int col = 0; col < sevenPattern[0].length; col++) {
                if (sevenPattern[row][col] == 1) {
                    double x = sevenOffsetX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }
    }
    
    public void makeLevelTwo() {
        addOutOfBounds(0,580,800,20,Color.RED);
        addOutOfBounds(0,40,800,20,Color.RED);
    	double brickWidth = 70;
        double brickHeight = 20;
        double startX = 200;
        double startY = 145;
        double brickGap = 10;
        int pointValue = 1;
        addSlider(360,540);
    	addSlider(360, 80);
    	ballX = 400;
    	ballY = 500;
    	
        int [][] ePattern = {
                {1,1,1,1,1},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,1,1,1,1},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,0,0,0,0},
                {1,1,1,1,1},
            };

      
        for (int row = 0; row < ePattern.length; row++) {
            for (int col = 0; col < ePattern[0].length; col++) {
                if (ePattern[row][col] == 1) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        } 
    }
}

       