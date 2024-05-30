package entity;

public abstract class Paddle extends Entity {
    public void setDefaultValues() {
        if (isOnLeft) {
            x = 10;
        } else {
            x = gp.getScreenWidth() - 20;
        }
        height = 100;
        width = 10;
        y = (gp.getScreenHeight() / 2) - (height / 2);
        ySpeed = 10;
    }

    protected Directions directions = Directions.NONE;
}
