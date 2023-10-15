import java.awt.*;

public class Camera extends Rectangle {
    private AbstractEntity lock;
    private Integer xOffset, yOffset;

    public Camera(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void lock(AbstractEntity entity, Integer xOffset, Integer yOffset) {
        this.lock = entity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void updatePosition() {
        this.setLocation((int)lock.getPosition().x - xOffset, (int)lock.getPosition().y - yOffset);
    }
}
