package interfaces;

/**
 * An interface representing an updatable entity that needs to be recalculated every frame.
 */
public interface Updatable {
    void move(Long delta);

    boolean isActive();
}
