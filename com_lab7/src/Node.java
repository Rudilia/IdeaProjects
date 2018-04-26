import java.util.ArrayList;
import java.util.List;

public  class Node<T> {

    private T value;
    private int id = 0;
    private boolean isTerm = false;
    private Node<T> parent = null;
    private ArrayList<Node<T>> children = new ArrayList<>();

    void setValue(T value) { this.value = value; }
    void setId(int id) { this.id = id; }
    T getValue() { return value; }

    int getId() {
        return id;
    }

    boolean isTerm() {
        return isTerm;
    }

    void setTerm(boolean term) {
        isTerm = term;
    }

    Node<T> getParent() {
        return parent;
    }
    void setParent(Node<T> parent) {
        this.parent = parent;
    }
    ArrayList<Node<T>> getChildren() {
        return children;
    }
    void setChildren(ArrayList<Node<T>> children) {
        this.children = children;
    }

    boolean hasChildren() {
        return children.size() != 0;
    }

}