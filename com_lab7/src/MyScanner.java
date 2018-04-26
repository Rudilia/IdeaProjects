public class MyScanner {
    public String program;
    private Position cur;

    public MyScanner(String program) {
        cur = new Position(this.program = program);
    }

    public Token nextToken() {
        while (cur.Cp() != -1) {
            while (cur.isWhitespace()) cur.add();

            Position start = new Position(cur);

            switch (cur.Cp()) {
                case -1:
                    return new Token("EOF", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case '{':
                    cur.add();
                    return new Token("{", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case '}':
                    cur.add();
                    return new Token("}", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case ',':
                    cur.add();
                    return new Token(",", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case '[':
                    cur.add();
                    return new Token("[", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case ']':
                    cur.add();
                    return new Token("]", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case ':':
                    cur.add();
                    return new Token(":", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case '@':
                    cur.add();
                    return new Token("@", program.substring(start.getIndex(),
                            cur.getIndex()), start, new Position(cur));
                case '\"':

                    cur.add();
                    if (cur.Cp() != '\"') {
                        while (cur.Cp() != '\"') {
                            if (cur.Cp() == -1)
                                return new Token("error", program.substring(start.getIndex(),
                                        cur.getIndex()), start, new Position(cur));
                            cur.add();
                        }
                        cur.add();
                        return new Token("term", program.substring(start.getIndex()+1,
                                cur.getIndex()-1), start, new Position(cur));
                    } else {
                        return new Token("error", program.substring(start.getIndex(),
                                cur.getIndex()), start, new Position(cur));
                    }

                default:
                    if (cur.isLetter()) {
                        cur.add();
                        while (cur.isLetterOrDigit()) {
                            cur.add();
                        }
                        if (cur.Cp() == '\'') cur.add();
                        return new Token("nonTerm", program.substring(start.getIndex(),
                                cur.getIndex()), start, new Position(cur));
                    } else {
                        while (cur.isWhitespace()) cur.add();
                        return new Token("error", program.substring(start.getIndex(),
                                cur.getIndex()), start, new Position(cur));
                    }
            }
        }
        return new Token("EOF", "eof", cur, new Position(cur));
    }



}