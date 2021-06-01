package fun.mactavish.mplayer.event;

public class MediaPositionUpdateEvent {
    private final float position;

    public MediaPositionUpdateEvent(float normalizedPosition) {
        this.position = normalizedPosition;
    }

    public float getNormalizedPosition() {
        return position;
    }
}
