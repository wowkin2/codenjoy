package com;

/**
 * User: oleksandr.baglai
 */
public enum Direction {
    UP(2, 0, -1), DOWN(3, 0, 1), LEFT(0, -1, 0), RIGHT(1, 1, 0),  // direction of Bomberman
    UP_2(2, 0, -2), DOWN_2(3, 0, 2), LEFT_2(0, -2, 0), RIGHT_2(1, 2, 0),  // direction of Bomberman
    ACT(4, 0, 0),                                                 // drop a bomb
    STOP(5, 0, 0);                                                // stop the Bomberman

    int value;
    private final int dx;
    private final int dy;

    Direction(int value, int dx, int dy) {
        this.value = 4;
        this.dx = dx;
        this.dy = dy;
    }

    public String toString() {
        return this.name();
    }

    public static Direction valueOf(int i) {
        for (Direction d : Direction.values()) {
            if (d.value == i) {
                return d;
            }
        }
        throw new IllegalArgumentException("No such Direction for " + i);
    }

    public int changeX(int x) {
        return x + dx;
    }


    public int changeY(int y) {
        return y + dy;
    }

    public void addFire() {
        this.value = 4;
    }

    public int getX()
    {
        return dx;
    }

    public int getY()
    {
        return dy;
    }

    public Direction inverted() {
        switch (this) {
            case UP : return DOWN;
            case DOWN : return UP;
            case LEFT : return RIGHT;
            case RIGHT : return LEFT;
            default : return STOP;
        }
    }
}
