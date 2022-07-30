package Assignment;

import java.util.List;

public class Cell {

    private int x;

    private int y;

    private boolean isAlive = false;

    private boolean newLifeStatus = false;

    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    // marks the life status of the cell which is actually updates after calling `refreshLifeStatus`
    public void lazyUpdateLifeStatus(List<Cell> neighbors) {
        long neighborsAlive = neighbors.stream().filter(neighbor -> neighbor.isAlive()).count();
        if (isOverpopulated(this.isAlive, neighborsAlive) || isUnderpopulated(neighborsAlive)) {
            this.newLifeStatus = false;
        } else if (canReproduce(this.isAlive, neighborsAlive)) {
            this.newLifeStatus = true;
        } else {
            this.newLifeStatus = this.isAlive;
        }
    }


    public void refreshLifeStatus() {
        this.isAlive = this.newLifeStatus;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static boolean isUnderpopulated(long neighborsAlive) {
        return neighborsAlive < 2;
    }

    private static boolean isOverpopulated(boolean cellAlive, long neighborsAlive) {
        return cellAlive && neighborsAlive > 3;
    }

    private static boolean canReproduce(boolean cellAlive, long neighborsAlive) {
        return !cellAlive && neighborsAlive == 3;
    }
}
