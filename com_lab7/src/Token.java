public class Token {
    public final String tag;
    public final Fragment coords;

    public String value;

    protected Token(String tag, String value, Position starting, Position following) {
        this.tag = tag;
        this.value = value;
        this.coords = new Fragment(starting, following);
    }

    public String getTag(){
        return tag;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {

            return value + " " + coords.toString() + ": " + value;
    }
}