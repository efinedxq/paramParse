package SimpleParser;

import java.io.*;
import java.util.*;

public class Parse {
	private Lexer lex;// 这个语法分析器的词法分析器
	private Token look;// 向前看词法单元
	int cnt = 0;

	public Parse(Lexer l) throws IOException {
		lex = l;
		move();
	}

	// 扫描一个词语
	void move() throws IOException {
		look = lex.scan();
		log(look.toString());
	}

	// 输出错误
	void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}

	// 提示信息
	void log(String s) {
		System.out.println("near line " + Lexer.line + ": " + s);
	}

	// 如果匹配到t，往下扫描一个词语
	void match(int t) throws IOException {
		// System.out.println("look.tag:"+look.tag+" t:"+t);
		if (look.tag == t) {
			// log(look.toString());
			move();
		} else
			error("语法错误");
	}

	void matchs() throws IOException {
		switch (look.tag) {
		case Tag.AAA:
		case '=':
			move();
			break;
		default:
			error("语法错误");
		}
	}

	public void program() throws IOException {
		match(Tag.MAIN);
		match('(');
		match(')');
		match('{');
		stmts();
		match('}');
		if (look.tag != lex.EOF)
			error("语法错误");
	}

	// <语句串>::=<语句>{;<语句>}
	public void stmts() throws IOException {
		stmt();
		while (look.tag == ';') {
			move();
			stmt();
		}
	}

	// <语句>::=<赋值语句>
	public void stmt() throws IOException {
		assign();
	}

	// 匹配 一个赋值语句 <赋值语句>::=ID:=<表达式>
	public void assign() throws IOException {
		match(Tag.ID);
		matchs();
		expr();
	}

	// 匹配 一个表达式 <表达式>::=<项>{+<项>|-<项>}
	public void expr() throws IOException {
		term();
		while (look.tag == '+' || look.tag == '-') {
			move();
			term();
		}
	}

	// 匹配一个项 <项>::=<因子>{*<因子>|/<因子>}
	public void term() throws IOException {
		factor();
		while (look.tag == '*' || look.tag == '/') {
			move();
			factor();
		}
	}

	// 匹配一个因子 <因子>::=ID|NUM|(<表达式>)
	public void factor() throws IOException {
		// System.out.print("look.tag:"+look.tag);
		// log(look.toString());
		switch (look.tag) {
		case '(':
			move();
			expr();
			match(')');
			break;
		case Tag.NUMBER:
		case Tag.REAL:
		case Tag.ID:
			move();
			break;
		case ';':
			break;
		default:
			error("语法错误");
			break;
		}
	}
}