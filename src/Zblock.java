import processing.core.PApplet;

/**
 * Created by bgc82 on 2016-11-29.
 */
public class Zblock extends Block {
    int cell[][][] = {
            {{0, 1}, {1, 1}, {1, 0}, {2, 0}},
            {{0, 0}, {0, 1}, {1, 1}, {1, 2}},
            {{0, 1}, {1, 1}, {1, 0}, {2, 0}},
            {{0, 0}, {0, 1}, {1, 1}, {1, 2}}
    };


    public Zblock(int x, int y) {
        super(x, y);
        setColor(new Color(255, 0, 0));
    }


    @Override
    public void rotateBlock(int[][] grid) {
        int rotationCheckNumber = (getBlockRotation() + 1) % 4;
        if (getPosX() >= 280 && getBlockSize() == 40) {
            setPosX(240);
        } else if (getPosX() > 280) {
            setPosX(280);
        }
        Loop:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                if (grid[(getPosX() + Constant.BLOCK_LENGTH * cell[rotationCheckNumber][i][j]) / Constant.BLOCK_LENGTH]
                        [(getPosY() + Constant.BLOCK_LENGTH * cell[rotationCheckNumber][i][j]) / Constant.BLOCK_LENGTH] != -1) {
                    break Loop;
                } else if (i == 3 && j == 1) {
                    setBlockRotation((getBlockRotation() + 1) % 4);
                }
            }
        }
    }

    @Override
    public boolean checkSide(int[][] grid, int direction, int size) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                if (getPosX() < 400 - size && getPosX() > 0 && grid[(getPosX() + Constant.BLOCK_LENGTH * (cell[getBlockRotation()][i][j] + direction)) / Constant.BLOCK_LENGTH]
                        [(getPosY() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1]) / Constant.BLOCK_LENGTH] != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCollision(GameManager gameManager) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                if (getPosY() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1] > 760 ||
                        gameManager.getGrid()[(getPosX() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j]) / Constant.BLOCK_LENGTH]
                                [(getPosY() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1]) / Constant.BLOCK_LENGTH] != -1) { // 바닥
                    mountBlock(gameManager.getGrid());
                    return true;
                }
            }
        }
        return false;
    }

    public void mountBlock(int[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                if ((getPosY() - 40 + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1]) / Constant.BLOCK_LENGTH == -1) {
                    break;
                }
                grid[(getPosX() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j]) / Constant.BLOCK_LENGTH]
                        [(getPosY() - 40 + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1]) / Constant.BLOCK_LENGTH] = 5;

            }
        }
        setPosY(0);
    }

    @Override
    public void update() {
        if (getBlockRotation() == 0 || getBlockRotation() == 2)
            setBlockSize(120);
        else
            setBlockSize(80);
    }

    @Override
    public void render(PApplet p) {
        p.fill(getRedColor(), getGreenColor(), getBlueColor());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                p.rect(getPosX() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j],
                        getPosY() + Constant.BLOCK_LENGTH * cell[getBlockRotation()][i][j + 1], Constant.BLOCK_LENGTH, Constant.BLOCK_LENGTH);
            }
        }
    }

    @Override
    public void renderNextBlock(PApplet p) {
        p.fill(getRedColor(), getGreenColor(), getBlueColor());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 1; j++) {
                p.rect(Constant.BLOCK_LENGTH * cell[0][i][j] + 500,
                        Constant.BLOCK_LENGTH * cell[0][i][j + 1] + 120, Constant.BLOCK_LENGTH, Constant.BLOCK_LENGTH);
            }
        }
    }
}
