package hbut.tokenizer;

import hbut.exception.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by yan on 2016/10/24.
 */
public class Tokenizer {

    private Reader reader;

    private ArrayList<Token> tokens = new ArrayList<>();

    private int c;

    private boolean isUnread = false;

    private int savedChar;

    public Tokenizer(BufferedReader bufferedReader) {
        this.reader = bufferedReader;
    }

    public void Tokenizer(Reader reader) throws IOException{
        this.reader = reader;
    }

    public void tokenizer() throws IOException, JsonParseException {
        Token token;
        do{
            token = start();
            tokens.add(token);
        }while (token.getType()!=TokenType.END_DOC);
    }

    private Token start() throws IOException, JsonParseException {
        c = '?';
        Token token = null;
        do{
            c = read();
        }while (isSpace(c));
        if(isNull(c)){
            return new Token(TokenType.NULL,null);
        }else if(c == ','){
            return new Token(TokenType.COMMA,',');
        }else if(c == ':'){
            return new Token(TokenType.COLON,':');
        }else if (c == '{') {
            return new Token(TokenType.START_OBJ, "{");
        } else if (c == '[') {
            return new Token(TokenType.START_ARRAY, "[");
        } else if (c == ']') {
            return new Token(TokenType.END_ARRAY, "]");
        } else if (c == '}') {
            return new Token(TokenType.END_OBJ, "}");
        } else if (isTrue(c)){
            return new Token(TokenType.BOOLEAN,"true");
        } else if (isFalse(c)){
            return new Token(TokenType.BOOLEAN,"false");
        } else if(c == '"'){
            return readString();
        }else if(isNum(c)){
            unread();
            return readNum();
        }else if (c == -1) {
            return new Token(TokenType.END_DOC, "EOF");
        } else {
            throw new JsonParseException("Invalid JSON input.");
        }
    }

    private boolean isDigit(int c) {
        return c >= '0' && c <= '9';
    }

    private Token readNum(){
        return null;
    }

    private void unread() {
        isUnread = true;
    }

    private boolean isNum(int c) {
        return isDigit(c)||c=='-';
    }

    private Token readString() throws IOException, JsonParseException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            c = read();
            if (isEscape()) {    //�ж��Ƿ�Ϊ\", \\, \/, \b, \f, \n, \t, \r.
                if (c == 'u') {
                    sb.append('\\' + (char) c);
                    for (int i = 0; i < 4; i++) {
                        c = read();
                        if (isHex(c)) {
                            sb.append((char) c);
                        } else {
                            throw new JsonParseException("Invalid Json input.");
                        }
                    }
                } else {
                    sb.append("\\" + (char) c);
                }
            } else if (c == '"') {
                return new Token(TokenType.STRING, sb.toString());
            } else if (c == '\r' || c == '\n') {
                throw new JsonParseException("Invalid JSON input.");
            } else {
                sb.append((char) c);
            }
        }
    }

    private boolean isHex(int c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') ||
                (c >= 'A' && c <= 'F');
    }

    private boolean isTrue(int c) throws JsonParseException, IOException {
        if(c=='t') {
            c = read();
            if(c=='r') {
                c = read();
                if (c == 'u') {
                    c = read();
                    if (c == 'e') {
                        return true;
                    }
                    else
                        throw new JsonParseException("Invalid JSON input");
                } else
                    throw new JsonParseException("Invalid JSON input");
            }else
                throw new JsonParseException("Invalid JSON input");
        }
        return false;
    }

    private boolean isFalse(int c) throws JsonParseException, IOException{
        if(c=='f') {
            c = read();
            if(c=='a') {
                c = read();
                if (c == 'l') {
                    c = read();
                    if (c == 's') {
                        c = read();
                        if(c == 'e')
                            return true;
                        else
                            throw new JsonParseException("Invalid JSON input");
                    }
                    else
                        throw new JsonParseException("Invalid JSON input");
                } else
                    throw new JsonParseException("Invalid JSON input");
            }else
                throw new JsonParseException("Invalid JSON input");
        }
        return false;
    }

    private boolean isNull(int c) throws IOException, JsonParseException {
        if(c=='n') {
            c = read();
            if(c=='u') {
                c = read();
                if (c == 'l') {
                    c = read();
                    if (c == 'l') {
                        return true;
                    }
                    else
                        throw new JsonParseException("Invalid JSON input");
                } else
                    throw new JsonParseException("Invalid JSON input");
            }else
                throw new JsonParseException("Invalid JSON input");
        }
        return false;
    }

    private boolean isSpace(int c) {
        return c>=0&&c<=' ';
    }

    private int read() throws IOException {
        if(!isUnread){
            int c = reader.read();
            savedChar = c;
            return c;
        }else{
            isUnread = false;
            return savedChar;
        }
    }

    public boolean isEscape() throws JsonParseException, IOException {
        if(c=='\\'){
            c = read();
            if (c == '"' || c == '\\' || c == '/' || c == 'b' ||
                    c == 'f' || c == 'n' || c == 't' || c == 'r' || c == 'u') {
                return true;
            } else {
                throw new JsonParseException("Invalid JSON input.");
            }
        }else{
            return false;
        }

    }

    public Token next() {
      return  tokens.remove(0);
    }

    public Token peek(int i) {
        return tokens.get(i);
    }
}
