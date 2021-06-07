package fun.mactavish.mplayer.event;

public class MediaStartEvent {
    private final String url;

    public MediaStartEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
