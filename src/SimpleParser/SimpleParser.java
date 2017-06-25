package SimpleParser;

import java.io.*;

public class SimpleParser {
	public static void main(String[] args) throws IOException, Error {
//		if (args.length < 1) {
//			System.err.println("syntax error!!");
//			return;
//		}
		InputStream cons = System.in;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				"parser.in"));
		System.setIn(in);
		Lexer lex = new Lexer();
		Parse parse = new Parse(lex);
		parse.program();
		System.out.println("成功");
		in.close();
		System.setIn(cons);
	}
}

class Error extends RuntimeException {
	public Error() {
		// TODO Auto-generated constructor stub
	}

	public Error(String msg) {
		super(msg);
	}
}
