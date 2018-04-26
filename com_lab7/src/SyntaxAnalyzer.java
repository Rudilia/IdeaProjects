import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SyntaxAnalyzer {
    private static HashMap<String, Integer> ids = new HashMap<>();

    private static HashMap<String, Integer> terminals = new HashMap<>() {{
        put("{", 0); put("}", 1); put(",", 2); put("[",3);
        put("]",4); put(":",5);  put("term", 6); put("nonTerm", 7);
        put("@", 8); put("EOF",9); put("EPS", -2); }};


    private static HashMap<String, Integer> nonTerminals = new HashMap<>() {{
        put("A", 0); put("A1", 1); put("RS", 2); put("RS1", 3); put("R", 4);
        put("R1", 5); put("R2", 6); put("R3", 7);}};

    private static String[][][] delta = {
            {{"{","nonTerm","}","A1","RS"}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}, {""}},
            {{""}, {""}, {",","nonTerm", "A1"}, {"EPS"}, {""}, {""}, {""}, {""}, {""}, {""}},
            {{""}, {""}, {""}, {"[", "R", "]", "RS1"}, {""}, {""}, {""}, {""}, {""}, {""}},
            {{""}, {""}, {""}, {"[", "R", "]", "RS1"}, {""}, {""}, {""}, {""}, {""}, {"EPS"}},
            {{""}, {""}, {""}, {""}, {""}, {""}, {""}, {"nonTerm", ":", "R1", "R3"}, {""}, {""}},
            {{""}, {""}, {""}, {""}, {""}, {""}, {"term", "R2"}, {"nonTerm", "R2"}, {"@"}, {""}},
            {{""}, {""}, {""}, {""}, {"EPS"}, {"EPS"}, {"term", "R2"}, {"nonTerm", "R2"}, {""}, {""}},
            {{""}, {""}, {""}, {""}, {"EPS"},{":", "R1"}, {""}, {""}, {""}, {""}}

    };





    public static void main(String[] args) throws IOException {
        String file = "C:\\Users\\tanyu\\IdeaProjects\\com_lab7\\src\\input.txt";
        try {
            Scanner scanner = new Scanner(new File(file));
            String text = scanner.useDelimiter("\\A").next();
            MyScanner scan = new MyScanner(text);
            FileWriter writer = new FileWriter("output.txt", false);
            Tree<String> tree = parseExpr(scan);
            tree.printTree(tree.getRoot(), writer);
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Tree<String> parseExpr(MyScanner scanner) {
        Stack<String> stack = new Stack<>();
        stack.push("EOF");
        stack.push("A");
        Token token = scanner.nextToken();
        for(String nt : nonTerminals.keySet()) ids.put(nt, 0);
        for(String t : terminals.keySet()) ids.put(t, 0);
        Tree<String> tree = new Tree<>( "A");
        do {
            String X = stack.peek();
            if (token.getTag().equals("error")){
                System.out.println("ERROR: " + token);
                break;
            }

            if(terminals.containsKey(X)) {
                if((X.equals(token.getTag()) || X.equals("EPS"))) {

                    stack.pop();
                    if(!X.equals("EPS")) {
                        Node<String> node = tree.search(tree.getRoot(), token.getTag(), ids.get(token.getTag()));
                        node.setValue(token.getValue());
                        node.setTerm(true);
                        token = scanner.nextToken();
                    }
                } else {
                    System.out.println("ERROR: " + token);
                    break;
                }
            } else {
                String[] rule = delta[nonTerminals.get(X)][terminals.get(token.getTag())];
                if(!Arrays.equals(rule, new String[]{""})) {
                    stack.pop();
                    Node<String> node = tree.search(tree.getRoot(), X,ids.get(X));
                    ArrayList<Node<String>> children = new ArrayList<>();
                    for(int i = rule.length-1, j = 0; i >= 0; i--, j++) {
                        stack.push(rule[i]);
                        ids.put(rule[j], ids.get(rule[j])+1);
                        Node<String> child = new Node<>();
                        child.setValue(rule[j]);
                        child.setId(ids.get(rule[j]));
                        child.setParent(node);
                        children.add(child);
                        tree.size++;
                    }
                    node.setChildren(children);
                } else {
                    System.out.println("ERROR: " + token);
                    break;
                }
            }
        } while(!stack.peek().equals("EOF"));
        return tree;
    }
}
