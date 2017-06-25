package SimpleParser;


class Tag {
	public final static int MAIN = 256, NUMBER = 262, REAL = 263, ID = 267
	// ,ADD = 268, SUB = 269, MUL = 270, DIV = 271, LP = 272, RP = 273
	,AAA = 274;
}

public class Token {
	public final int tag;

	public Token(int t) {
		tag = t;
	}

	public String toString() {
		return "" + (char) tag;
	}
}

class Num extends Token {
	public final int value;

	public Num(int v) {
		super(Tag.NUMBER);
		value = v;
	}

	public String toString() {
		return "" + value;
	}
}

class Real extends Token {
	public final float value;

	public Real(float v) {
		super(Tag.REAL);
		value = v;
	}

	public String toString() {
		return "" + value;
	}
}

class Word extends Token {
	public String lexeme = "";

	public Word(String s, int tag) {
		super(tag);
		lexeme = s;
	}

	public String toString() {
		return lexeme ;
	}

	public static final Word AAA = new Word("++", Tag.AAA);
}