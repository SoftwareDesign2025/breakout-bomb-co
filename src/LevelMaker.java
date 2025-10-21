// Author: Gavin Collins
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class LevelMaker {

    private Group root;
    private List<Brick> bricks;
    private ArrayList<Slider> sliderList = new ArrayList<>();
    private ArrayList<Rectangle> outBoundsList = new ArrayList<>();
    private Random rand = new Random();

    public LevelMaker(Group root, List<Brick> bricks) {
        this.root = root;
        this.bricks = bricks;
    }

    public ArrayList<Slider> getSliderList() { return sliderList; }

    public ArrayList<Rectangle> getOutOfBounds() { return outBoundsList; }

    public void resetLevel() {
        sliderList.clear();
        outBoundsList.clear();
    }

    public void addSlider(double startX, double startY) {
        Slider s = new Slider(startX, startY);
        sliderList.add(s);
        root.getChildren().add(s.getNode());
    }

    public void addOutOfBounds(double x, double y, double width, double height, Color color) {
        Rectangle r = new Rectangle(x, y, width, height);
        r.setFill(color);
        outBoundsList.add(r);
        root.getChildren().add(r);
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
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
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
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
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
                    Brick brick = new Brick(brickWidth, brickHeight, x, y, pointValue);
                    bricks.add(brick);
                    root.getChildren().add(brick.getBrick());
                }
            }
        } 
    }
}

       