package fun.mactavish.mplayer.event;

public class ProgressPositionUpdateEvent {
    private final float position;

    public ProgressPositionUpdateEvent(float normalizedPosition) {
        this.position = normalizedPosition;
    }

    public float getNormalizedPosition() {
        return position;
    }
}
