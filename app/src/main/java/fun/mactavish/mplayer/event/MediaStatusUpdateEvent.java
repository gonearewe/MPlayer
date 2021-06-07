package fun.mactavish.mplayer.event;

public class MediaStatusUpdateEvent {
    private final float normalizedPosition;
    private final boolean playing;


    public MediaStatusUpdateEvent(float normalizedPosition, boolean playing) {
        this.normalizedPosition = normalizedPosition;
        this.playing = playing;
    }

    public float getNormalizedPosition() {
        return normalizedPosition;
    }

    public boolean isPlaying() {
        return playing;
    }
}
