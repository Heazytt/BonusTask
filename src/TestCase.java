public class TestCase {
    private String id;
    private String text;
    private String pattern;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getPattern() {
        return pattern;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() { // --> Debug output
        return "TestCase{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", pattern='" + pattern + '\'' +
                '}';
    }
}
