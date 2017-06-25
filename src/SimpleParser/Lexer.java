package SimpleParser;

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line = 1;
	public final int EOF=65535;
	char peek = ' ';
	Hashtable words = new Hashtable();

	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	public Lexer() {
		reserve(new Word("main", Tag.MAIN));
	}

	void readch() throws IOException {
		if(System.in.available()>0) peek = (char) System.in.read();
		else peek=EOF;
	}
	boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;
		peek = ' ';
		return true;
	}
	//扫面，提取一个词语
	public Token scan() throws IOException {
		//判断是否是空格、tab、转义字符、换行。
		for (;; readch()) {
			if (peek == ' ' || peek == '\t' || peek == '\r')
				continue;
			if (peek == '\n') {
				line++;
				continue;
			}
			break;
		}
		//匹配数字
		if (Character.isDigit(peek)) {
			//匹配整数部分
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			if (peek != '.')
				return new Num(v);
			//匹配浮点数
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek))
					break;
				x += Character.digit(peek, 10) / d;
				d *= 10;
			}
			return new Real(x);
		}
		//提取一个词语
		if (Character.isLetter(peek)) {
			StringBuilder b = new StringBuilder();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		switch (peek) {
		case '+':
			if (readch('+')){
				return Word.AAA;
			}else{
				return new Token('+');
			}
		}
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}

}