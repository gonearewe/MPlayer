package fun.mactavish.mplayer.event;

public class MediaSkipTimeEvent {
    private final long timeMs;

    public MediaSkipTimeEvent(long timeMs) {
        this.timeMs = timeMs;
    }

    public long getTimeMs() {
        return timeMs;
    }
}
