import java.io.FileWriter;
import java.io.IOException;

public class Tree<T> {
    private Node<T> root;
    int size = 0;
    private int print = 0;

    public Tree(T rootValue) {
        root = new Node<>();
        root.setValue(rootValue);
        size++;
    }

    Node<T> getRoot() {
        return root;
    }

    void printTree(Node<T> root, FileWriter writer) throws IOException {
        print++;
        if(root.getParent() == null) writer.write("digraph Tree {\n");
        if(root.hasChildren()) {
            StringBuilder res = new StringBuilder();
            res.append("\t\"");
            if (root.isTerm()) res.append("term: ");
            res.append(root.getValue() + " " + root.getId() + "\" -> {rank=same;");
            for(int j = 0; j < root.getChildren().size(); j++) {
                Node<T> child = root.getChildren().get(j);
                res.append("\"");
                if (child.isTerm()) res.append("term: ");
                res.append(child.getValue() + " " + child.getId() + "\";");
            }

            res.append("};");
            writer.write(res+"\n");
            for(int j = 0; j < root.getChildren().size(); j++)
                printTree(root.getChildren().get(j), writer);
        }
        if(print == size) {
            print = 0;
            writer.write("}");
        }
    }

    Node<T> search(Node<T> root, String value, int id) {
        if(root.getValue().equals(value) && root.getId() == id){
            return root;
        }
        else {
            Node<T> res = null;
            if(root.hasChildren()) {
                for (Node<T> child : root.getChildren()) {
                    res = search(child, value, id);
                    if (res != null) break;
                }
            }
            return res;
        }
    }

}