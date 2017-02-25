/**
 * Created by bgc82 on 2016-11-28.
 */

import processing.core.PApplet;

import java.util.ArrayList;

public class GameManager extends PApplet {

    private int grid[][];
    private ArrayList<Block> blocks;
    private Block currentBlock;
    private Block nextBlock;
    private Block tempBlcok;
    private int currentTime;
    private int sceneNumber;
    private int score;
    private int timer;
    private int blockSpeed;
    private int keyInputTime;

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.runSketch();
    }

    public GameManager() {
    }

    public void initGame() {
        sceneNumber = 0;
        blocks = new ArrayList<>();
        blocks.add(new Iblock(160, 0));
        blocks.add(new Lblock(160, 0));
        blocks.add(new Lblock2(160, 0));
        blocks.add(new Oblock(160, 0));
        blocks.add(new Tblock(160, 0));
        blocks.add(new Zblock(160, 0));
        blocks.add(new Zblock2(160, 0));
        grid = new int[10][20];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                grid[i][j] = -1;
            }
        }
        currentBlock = blocks.get((int) (Math.random() * blocks.size()));
        nextBlock = blocks.get((int) (Math.random() * blocks.size())).clone();
        blockSpeed = 10;
        timer = Constant.BLOCK_FALLING_TIMER;
        score = 0;
        currentTime = millis() + timer;
    }

    public Block changeCurrentBlock() {
        tempBlcok = nextBlock;
        currentBlock = tempBlcok;
        nextBlock = blocks.get((int) (Math.random() * blocks.size()));
        return currentBlock;
    }

    public void settings() {
        size(700, 800);
        initGame();
    }

    public void update() {
        moveBlock();
        if (millis() > currentTime) {
            currentBlock.fallingBlock();
            currentTime = millis() + timer - blockSpeed;
        }

        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 10; i++) {
                if (grid[i][j] == -1)
                    break;
                else if (grid[i][j] != -1 && i == 9) {
                    deleteLine(j);
                }
            }
        }
        if (checkGameOver())
            sceneNumber = 1;

        blockSpeed++;
        if (blockSpeed > Constant.BLOCK_MAX_SPEED)
            blockSpeed = Constant.BLOCK_MAX_SPEED;

    }

    @Override
    public void draw() {
        if (sceneNumber == 0) {
            background(200);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 20; j++) {
                    if (grid[i][j] != -1) {
                        fill(blocks.get(grid[i][j]).getRedColor(), blocks.get(grid[i][j]).getGreenColor(), blocks.get(grid[i][j]).getBlueColor());
                    } else
                        fill(255, 255, 255);
                    rect(i * 40, j * 40, 40, 40);
                }

            }
            update();

            if (currentBlock.checkCollision(this))
                changeCurrentBlock();
            currentBlock.update();
            currentBlock.render(this);
            nextBlock.renderNextBlock(this);
            drawText();
        } else
            drawGameOver();
    }

    public void drawText() {
        textSize(30);
        fill(0, 0, 0);
        text("Next Block", 480, 70);
        text("Score", 510, 350);
        text(score, 540, 400);
    }

    public boolean checkGameOver() {
        for (int i = 0; i < 10; i++) {
            if (grid[i][0] != -1) {
                return true;
            }
        }
        return false;
    }

    public void drawGameOver() {
        textSize(50);
        fill(0, 0, 0);
        text("GAME OVER", 220, 350);
        text("Press 'R' to restart", 150, 450);
    }

    public void deleteLine(int line) {
        for (int i = 0; i < 10; i++) {
            for (int j = line; j > 0; j--) {
                grid[i][j] = grid[i][j - 1];
            }
        }
        score += 100;
    }

    public void moveBlock() {
        if (millis() > keyInputTime) {
            key = Character.toUpperCase(key);
            if (keyPressed && keyCode == LEFT) {
                currentBlock.moveBlock(-1, currentBlock.getBlockSize(), grid);
            } else if (keyPressed && keyCode == RIGHT) {
                currentBlock.moveBlock(+1, currentBlock.getBlockSize(), grid);
            } else if (keyPressed && keyCode == DOWN) {
                currentBlock.moveBlock(0, currentBlock.getBlockSize(), grid);
            } else if (keyPressed && keyCode == UP) {
                currentBlock.rotateBlock(grid);
            } else if (keyPressed && key == ' ') {
                currentBlock.dropFast(this);
            }
            keyInputTime += 1000;
        }
    }



    @Override
    public void keyPressed() {
        key = Character.toUpperCase(key);
        keyInputTime = millis();
        if (key == 'R' && sceneNumber == 1)
            initGame();
    }

    public int[][] getGrid() {
        return grid;
    }
}
