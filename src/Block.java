import processing.core.PApplet;

import java.awt.*;
abstract public class Block implements Cloneable {
    private int posX;
    private int posY;
    private Color color;
    private int blockSize;
    private int blockRotation;

    public Block(int x, int y) {
        this.posX = x;
        this.posY = y;
        blockRotation = 0;
    }

    abstract public boolean checkSide(int[][] grid, int direction, int size);

    abstract public boolean checkCollision(GameManager gameManager);

    abstract public void update();

    abstract public void render(PApplet p);

    abstract public void renderNextBlock(PApplet p);

    @Override
    public Block clone() {
        try {
            Block ball = (Block) super.clone();
            return ball;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void moveBlock(int direction, int size, int[][] grid) {
        switch (direction) {
            case 1:
                if (!checkSide(grid, direction, size))
                    posX = posX + Constant.BLOCK_LENGTH;
                posX = posX > 400 - size ? 400 - size : posX;
                break;
            case -1:
                if (!checkSide(grid, direction, size))
                    posX = posX - Constant.BLOCK_LENGTH;
                posX = posX < 0 ? 0 : posX;
                break;
            case 0:
                posY = posY + Constant.BLOCK_LENGTH;
                break;
        }
    }

    abstract public void rotateBlock(int[][] grid);

    public void fallingBlock() {
        posY = posY + Constant.BLOCK_LENGTH;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRedColor() {
        return this.color.getRed();
    }

    public int getGreenColor() {
        return this.color.getGreen();
    }

    public int getBlueColor() {
        return this.color.getBlue();
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getBlockRotation() {
        return blockRotation;
    }

    public void setBlockRotation(int blockRotation) {
        this.blockRotation = blockRotation;
    }

    public void dropFast(GameManager gameManager) {
        while (true) {
            fallingBlock();
            if (checkCollision(gameManager)) {
                break;
            }
        }
        gameManager.changeCurrentBlock();
    }
}
