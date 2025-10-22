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
    private Random rand = new Random();

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

    private int randomizeBrick(int val) {
        if (val != 1) return val;
        int chance = rand.nextInt(100);
        if (chance < 10) return 2;
        if (chance < 15) return 3;
        if (chance < 20) return 4;
        return 1;
    }

    private Color getBrickColor(int val) {
        if (val == 2) return Color.YELLOW;
        if (val == 3) return Color.LIMEGREEN;
        if (val == 4) return Color.BLACK;
        return Color.BLUE;
    }

    public void makeLevelOne() {
        addSlider(360, 500);
        addOutOfBounds(0, 580, 800, 20, Color.RED);

        double brickWidth = 70;
        double brickHeight = 20;
        double startX = 35;
        double startY = 60;
        double brickGap = 10;
        int pointValue = 1;
        ballX = 400;
        ballY = 400;

        int[][] sixPattern = {
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

        int[][] sevenPattern = {
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
            for (int col = 0; col < sixPattern[row].length; col++) {
                int val = randomizeBrick(sixPattern[row][col]);
                if (val != 0) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, Color.BLUE, null);
                    brick.getBrick().setFill(getBrickColor(val));
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }

        double sevenOffsetX = startX + 5 * (brickWidth + brickGap);

        for (int row = 0; row < sevenPattern.length; row++) {
            for (int col = 0; col < sevenPattern[row].length; col++) {
                int val = randomizeBrick(sevenPattern[row][col]);
                if (val != 0) {
                    double x = sevenOffsetX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, Color.BLUE, null);
                    brick.getBrick().setFill(getBrickColor(val));
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
            	int val = randomizeBrick(ePattern[row][col]);
                if (val != 0) {
                    double x = startX + col * (brickWidth + brickGap);
                    double y = startY + row * (brickHeight + brickGap);
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue, Color.BLUE, null);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        } 
    }
    //bomb
    public void makeLevelThree() {
        addOutOfBounds(0, 580, 800, 20, Color.RED);
        addSlider(360, 500);

        double brickWidth = 50;
        double brickHeight = 25;
        int rows = 12;
        int cols = 12;

        double totalWidth = cols * brickWidth;
        double totalHeight = rows * brickHeight;
        double startX = (800 - totalWidth) / 2;
        double startY = 100;

        double centerRow = rows / 2.0;
        double centerCol = cols / 2.0;
        double radius = 5.2;
        ballX = 400;
        ballY = 450;

        //bomb
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double dx = col - centerCol;
                double dy = row - centerRow;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < radius + 0.5) {
                    double x = startX + col * brickWidth;
                    double y = startY + row * brickHeight;

                    Color color;
                    if (dist < radius * 0.5) color = Color.rgb(10, 10, 10);   // pure black center
                    else if (dist < radius * 0.8) color = Color.rgb(25, 25, 25);
                    else color = Color.rgb(45, 45, 45);

                    Brick brick = new Brick(brickWidth - 2, brickHeight - 2, x, y, 1, color, null);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        }
        //fuse
        double fuseBaseX = startX + centerCol * brickWidth + brickWidth / 4.0;
        double fuseBaseY = startY - (brickHeight * 0.3); // lowered to meet bomb

        for (int i = 0; i < 5; i++) {
            double fx = fuseBaseX + i * 6;
            double fy = fuseBaseY - i * (brickHeight * 0.9);

            Color color;
            if (i <= 1) color = Color.RED;
            else if (i == 2) color = Color.ORANGE;
            else color = Color.YELLOW;

            Brick fuseBrick = new Brick(brickWidth - 20, brickHeight - 12, fx, fy, 5, color, null);
            bricks.add(fuseBrick);
            root.getChildren().add(fuseBrick.getBrick());
        }
    }








}

       