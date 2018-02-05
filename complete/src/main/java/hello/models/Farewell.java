package hello.models;

public class Farewell {
    private final long id;
    private final String content;

    public Farewell(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
