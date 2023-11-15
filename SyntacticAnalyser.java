import java.util.*;

public class SyntacticAnalyser {

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {

		if (tokens.isEmpty()) {
			throw new SyntaxException("List of tokens is empty.");
		}

		TreeNode currentNode = new TreeNode(TreeNode.Label.prog, null);
		ParseTree tree = new ParseTree(currentNode);

		int productionRuleID;
		boolean tokenAdded;

		HashMap<Pair<TreeNode.Label, Token.TokenType>, Integer> parsingTable = generateTable();

		Deque<TreeNode> stack = new ArrayDeque<>();
		stack.push(currentNode);

		for (Token token : tokens) {
			tokenAdded = false;
			while (!tokenAdded) {
				currentNode = stack.getFirst();
				System.out.println(stack);

				//if epsilon is on the top of the stack, add epsilon to the parse tree and pop it from the stack

				if (currentNode.getLabel() == TreeNode.Label.epsilon) {
					currentNode.getParent().addChild(currentNode);
					stack.pop();
					currentNode = stack.getFirst();
				}

				//if a terminal is on the top of the stack and the input token matches the terminal, add the terminal to the parse tree and pop it from the stack

				else if ((currentNode.getLabel() == TreeNode.Label.terminal) && (currentNode.getToken().isPresent()) && (currentNode.getToken().get().getType() == token.getType())) {

					//if the input token contains a value, replace the token of the current node with the current input token so that the node stores the value

					if (token.getValue().isPresent()) {
						currentNode = new TreeNode(TreeNode.Label.terminal, token, currentNode.getParent());
					}
					currentNode.getParent().addChild(currentNode);
					stack.pop();
					tokenAdded = true;
				}

				//if the current node is a non-terminal, apply the production rule which corresponds with the current node and current token pair

				else if (currentNode.getLabel() != TreeNode.Label.terminal) {

					try {
						productionRuleID = parsingTable.get(new Pair<>(currentNode.getLabel(), token.getType()));
					}
					catch (NullPointerException e) {

						throw new SyntaxException("Variable and terminal pair does not exist.");
					}

					if (currentNode.getParent() != null) {
						currentNode.getParent().addChild(currentNode);
					}

					stack.pop();

					switch (productionRuleID) {
						case 1:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ARGS), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGARR), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MAIN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.VOID), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STATIC), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CLASS), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC), currentNode));
							break;
						case 2:
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.stat, currentNode));
							break;
						case 3:
							stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
							break;
						case 4:
							stack.push(new TreeNode(TreeNode.Label.whilestat, currentNode));
							break;
						case 5:
							stack.push(new TreeNode(TreeNode.Label.forstat, currentNode));
							break;
						case 6:
							stack.push(new TreeNode(TreeNode.Label.ifstat, currentNode));
							break;
						case 7:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							stack.push(new TreeNode(TreeNode.Label.assign, currentNode));
							break;
						case 8:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							stack.push(new TreeNode(TreeNode.Label.decl, currentNode));
							break;
						case 9:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							stack.push(new TreeNode(TreeNode.Label.print, currentNode));
							break;
						case 10:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							break;
						case 11:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.WHILE), currentNode));
							break;
						case 12:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.forarith, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
							stack.push(new TreeNode(TreeNode.Label.forstart, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FOR), currentNode));
							break;
						case 13:
							stack.push(new TreeNode(TreeNode.Label.decl, currentNode));
							break;
						case 14:
							stack.push(new TreeNode(TreeNode.Label.assign, currentNode));
							break;
						case 15:
							stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
							break;
						case 16:
							stack.push(new TreeNode(TreeNode.Label.elseifstat, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), currentNode));
							break;
						case 17:
							stack.push(new TreeNode(TreeNode.Label.elseifstat, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.los, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.elseorelseif, currentNode));
							break;
						case 18:
							stack.push(new TreeNode(TreeNode.Label.possif, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ELSE), currentNode));
							break;
						case 19:
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), currentNode));
							break;
						case 20:
							stack.push(new TreeNode(TreeNode.Label.expr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
							break;
						case 21:
							stack.push(new TreeNode(TreeNode.Label.possassign, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
							stack.push(new TreeNode(TreeNode.Label.type, currentNode));
							break;
						case 22:
							stack.push(new TreeNode(TreeNode.Label.expr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), currentNode));
							break;
						case 23:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.printexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PRINT), currentNode));
							break;
						case 24:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE), currentNode));
							break;
						case 25:
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							break;
						case 26:
							stack.push(new TreeNode(TreeNode.Label.charexpr, currentNode));
							break;
						case 27:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CHARLIT), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), currentNode));
							break;
						case 28:
							stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.boolop, currentNode));
							break;
						case 29:
							stack.push(new TreeNode(TreeNode.Label.booleq, currentNode));
							break;
						case 30:
							stack.push(new TreeNode(TreeNode.Label.boollog, currentNode));
							break;
						case 31:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.EQUAL), currentNode));
							break;
						case 32:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NEQUAL), currentNode));
							break;
						case 33:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.AND), currentNode));
							break;
						case 34:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.OR), currentNode));
							break;
						case 35:
							stack.push(new TreeNode(TreeNode.Label.relexprprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
							break;
						case 36:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TRUE), currentNode));
							break;
						case 37:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FALSE), currentNode));
							break;
						case 38:
							stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.relop, currentNode));
							break;
						case 39:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LT), currentNode));
							break;
						case 40:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LE), currentNode));
							break;
						case 41:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GE), currentNode));
							break;
						case 42:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GT), currentNode));
							break;
						case 43:
							stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.term, currentNode));
							break;
						case 44:
							stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.term, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PLUS), currentNode));
							break;
						case 45:
							stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.term, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MINUS), currentNode));
							break;
						case 46:
							stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
							break;
						case 47:
							stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TIMES), currentNode));
							break;
						case 48:
							stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DIVIDE), currentNode));
							break;
						case 49:
							stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
							stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MOD), currentNode));
							break;
						case 50:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
							stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
							break;
						case 51:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
							break;
						case 52:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NUM), currentNode));
							break;
						case 53:
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGLIT), currentNode));
							stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), currentNode));
							break;
					}
				}
				else {
					throw new SyntaxException("Invalid syntax");
				}
			}
		}
		return tree;
	}

	public static HashMap<Pair<TreeNode.Label, Token.TokenType>, Integer> generateTable() {

		HashMap<Pair<TreeNode.Label, Token.TokenType>, Integer> parsingTable = new HashMap<>();

		parsingTable.put(new Pair<>(TreeNode.Label.prog, Token.TokenType.PUBLIC), 1);

		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.WHILE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.FOR), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.IF), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.ID), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.TYPE), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.PRINT), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.SEMICOLON), 2);
		parsingTable.put(new Pair<>(TreeNode.Label.los, Token.TokenType.RBRACE), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.WHILE), 4);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.FOR), 5);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.IF), 6);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.ID), 7);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.TYPE), 8);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.PRINT), 9);
		parsingTable.put(new Pair<>(TreeNode.Label.stat, Token.TokenType.SEMICOLON), 10);

		parsingTable.put(new Pair<>(TreeNode.Label.whilestat, Token.TokenType.WHILE), 11);

		parsingTable.put(new Pair<>(TreeNode.Label.forstat, Token.TokenType.FOR), 12);

		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.TYPE), 13);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.ID), 14);
		parsingTable.put(new Pair<>(TreeNode.Label.forstart, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.LPAREN), 15);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.ID), 15);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.NUM), 15);
		parsingTable.put(new Pair<>(TreeNode.Label.forarith, Token.TokenType.RPAREN), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.ifstat, Token.TokenType.IF), 16);

		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.ELSE), 17);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.WHILE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.FOR), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.IF), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.ID), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.TYPE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.PRINT), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.SEMICOLON), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.elseifstat, Token.TokenType.RBRACE), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.elseorelseif, Token.TokenType.ELSE), 18);

		parsingTable.put(new Pair<>(TreeNode.Label.possif, Token.TokenType.IF), 19);
		parsingTable.put(new Pair<>(TreeNode.Label.possif, Token.TokenType.LBRACE), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.assign, Token.TokenType.ID), 20);

		parsingTable.put(new Pair<>(TreeNode.Label.decl, Token.TokenType.TYPE), 21);

		parsingTable.put(new Pair<>(TreeNode.Label.possassign, Token.TokenType.ASSIGN), 22);
		parsingTable.put(new Pair<>(TreeNode.Label.possassign, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.print, Token.TokenType.PRINT), 23);

		parsingTable.put(new Pair<>(TreeNode.Label.type, Token.TokenType.TYPE), 24);

		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.LPAREN), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.ID), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.NUM), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.TRUE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.FALSE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.expr, Token.TokenType.SQUOTE), 26);

		parsingTable.put(new Pair<>(TreeNode.Label.charexpr, Token.TokenType.SQUOTE), 27);

		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.EQUAL), 28);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.NEQUAL), 28);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.AND), 28);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.OR), 28);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.RPAREN), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.boolexpr, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.EQUAL), 29);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.NEQUAL), 29);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.AND), 30);
		parsingTable.put(new Pair<>(TreeNode.Label.boolop, Token.TokenType.OR), 30);

		parsingTable.put(new Pair<>(TreeNode.Label.booleq, Token.TokenType.EQUAL), 31);
		parsingTable.put(new Pair<>(TreeNode.Label.booleq, Token.TokenType.NEQUAL), 32);

		parsingTable.put(new Pair<>(TreeNode.Label.boollog, Token.TokenType.AND), 33);
		parsingTable.put(new Pair<>(TreeNode.Label.boollog, Token.TokenType.OR), 34);

		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.LPAREN), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.ID), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.NUM), 35);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.TRUE), 36);
		parsingTable.put(new Pair<>(TreeNode.Label.relexpr, Token.TokenType.FALSE), 37);

		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.LT), 38);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.LE), 38);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.GE), 38);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.GT), 38);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.EQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.NEQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.AND), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.OR), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.RPAREN), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.relexprprime, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.LT), 39);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.LE), 40);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.GE), 41);
		parsingTable.put(new Pair<>(TreeNode.Label.relop, Token.TokenType.GT), 42);

		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.LPAREN), 43);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.ID), 43);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexpr, Token.TokenType.NUM), 43);

		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.PLUS), 44);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.MINUS), 45);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.LT), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.LE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.GE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.GT), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.EQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.NEQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.AND), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.OR), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.RPAREN), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.arithexprprime, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.LPAREN), 46);
		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.ID), 46);
		parsingTable.put(new Pair<>(TreeNode.Label.term, Token.TokenType.NUM), 46);

		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.TIMES), 47);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.DIVIDE), 48);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.MOD), 49);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.PLUS), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.MINUS), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.LT), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.LE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.GE), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.GT), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.EQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.NEQUAL), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.AND), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.OR), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.RPAREN), 3);
		parsingTable.put(new Pair<>(TreeNode.Label.termprime, Token.TokenType.SEMICOLON), 3);

		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.LPAREN), 50);
		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.ID), 51);
		parsingTable.put(new Pair<>(TreeNode.Label.factor, Token.TokenType.NUM), 52);

		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.LPAREN), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.ID), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.NUM), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.TRUE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.FALSE), 25);
		parsingTable.put(new Pair<>(TreeNode.Label.printexpr, Token.TokenType.DQUOTE), 53);

		return parsingTable;
	}
}

class Pair<A, B> {
	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	public A fst() {
		return a;
	}

	public B snd() {
		return b;
	}

	@Override
	public int hashCode() {
		return 3 * a.hashCode() + 7 * b.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return other.fst().equals(a) && other.snd().equals(b);
		}

		return false;
	}
}
