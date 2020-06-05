import java.util.Stack;

/**
 * @author hieu
 *
 */
public class InfixToPostFixCompute {
	
	/**
	 * Method the check the priority of arithmetic expression
	 * 
	 * @param ch take the character from String
	 * @return the priority for each arithmetic expression. Number 2 has more
	 *         priority
	 */
	public static int Precedence(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		}
		return -1;
	}

	/**
	 * Methods to check if the string contains only character from a-z for Variables
	 * Using the Regular Expression. Reference:
	 * https://www.geeksforgeeks.org/check-if-a-string-contains-only-alphabets-in-java-using-regex/
	 * 
	 * 
	 * @param str from String input
	 * @return true if it has character from a-z
	 */
	public static boolean isStringOnlyAlphabet(String str) {
		return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")));
	}

	/**
	 * Methods to check if the string contains only number Reference:
	 * https://www.geeksforgeeks.org/check-given-string-valid-number-integer-floating-point-java-set-2-regular-expression-approach/
	 * 
	 * @param str from String input
	 * @return true if it has number
	 */
	public static boolean isNumber(String str) {
		return ((str != null) && (!str.equals("")) && (str.matches("^[0-9]*$")));
	}

	/**
	 * Method to convert infix expression to postfix expression from String input
	 * 
	 * @param exp expression from String input
	 * @return String postfix expression
	 */
	public static String inFixToPostFix(String exp) {
		// Initializing empty String for result
		String result = "";

		// Initializing empty stack
		Stack<Character> s = new Stack<Character>();

		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);

			if (c == ' ')
				continue;

			// If the scanned character is an operand, add it to output
			if (Character.isLetter(c))
				result += c + " ";

			else if (Character.isDigit(c))
				result += c;

			// If the scanned character is an '(', push it to the stack
			else if (c == '(')
				s.push(c);

			// If the scanned character is an ')', pop and output from the stack
			// until '(' is encountered
			else if (c == ')') {

				while (!s.isEmpty() && s.peek() != '(') {
					result += " ";
					result += s.pop();
				}
				if (!s.isEmpty() && s.peek() != '(')
					return "Invalid Expression";
				else {
					result += " ";
					s.pop();
				}
			}

			// an operator is encountered
			else {
				while (!s.isEmpty() && Precedence(c) <= Precedence(s.peek())) {
					result += " ";
					result += s.pop();
				}
				result += " ";
				s.push(c);

			}

		}

		// pop all the operators from the stack
		while (!s.isEmpty()) {
			result += " ";
			result += s.pop();
		}
		return result;

	}

	
}
