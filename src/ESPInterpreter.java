import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author hieu
 *
 */
public class ESPInterpreter {

	private Variable[] variable_table;
	private static String line;
	public static int LineNo = 0;

	/**
	 * Constructor to initialize the variable, read the text file
	 * @throws FileNotFoundException
	 * @throws UndefinedVariableException
	 * @throws SyntaxError
	 */
	public ESPInterpreter() throws FileNotFoundException, UndefinedVariableException, SyntaxError {
		variable_table = new Variable[123];
		for (int i = 0; i <= 'z'; i++) {
			variable_table[i] = new Variable();
		}
		Scanner s = new Scanner(new File("src/file.txt"));
		test(s);
	}

	/**
	 * Method to check the character
	 * 
	 * @param s String input
	 * @return true the String has character
	 */
	public boolean checkCharacter(String s) {
		char x = s.charAt(0);
		if (Character.isAlphabetic(x) && s.length() == 1)
			return true;

		else
			return false;
	}

	/**
	 * Methods to check if the string contains only character from a-z for Variables
	 * Using the Regular Expression. Reference:
	 * https://www.geeksforgeeks.org/check-if-a-string-contains-only-alphabets-in-java-using-regex/
	 * 
	 * @param str from String input
	 * @return true if it has character from a-z
	 */
	public static boolean isVariable(String str) {
		return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")));
	}

	/**
	 * Method to check if character is the operator
	 * 
	 * @param x character
	 * @return true if it is the operator
	 */
	public boolean isOperator(String s) {
		if (s.length() > 1)
			return false;
		char x = s.charAt(0);
		if (x == '+' || x == '-' || x == '*' || x == '/') {
			return true;
		}
		return false;
	}

	/**
	 * Method to read the line Ask user to input a value Assign the value to
	 * variable
	 * 
	 * @param s
	 */
	public void readLine(String s) {
		char x = s.charAt(0);
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		if (isVariable(s)) {
			System.out.print("Enter an integer number for variable " + x + ": ");
			double y = sc.nextDouble();
			variable_table[x].setValue(y);
		}
	}

	/**
	 * Method to get the value from the variable
	 * 
	 * @param c variable
	 * @return the value of variable c
	 * @throws UndefinedVariableException
	 */
	double getValue(char c) throws UndefinedVariableException {
		char x = c;
		double value = variable_table[x].getValue();
		return value;
	}

	/**
	 * Method to calculate the arithmetic expression For example: z = x * x + y * y
	 * 
	 * @param c variable
	 * @param s arithmetic expression
	 * @throws UndefinedVariableException
	 */
	public void evaluateLine(char c, String s) throws UndefinedVariableException {

		String a = InfixToPostFixCompute.inFixToPostFix(s);
		if (a.equals("Invalid Expression")) {
			System.out.println("Line " + LineNo + ": Syntax Error!");
			System.exit(1);
		}
		double value = evaluatePostfix(a);
		variable_table[c].setValue(value);
	}

	/**
	 * Method to calculate and print the arithmetic expression For example: print (
	 * z + x ) / 2
	 * 
	 * @param s arithmetic expression
	 * @throws UndefinedVariableException
	 * @throws SyntaxError
	 */
	public void evaluatePrintLine(String s) throws UndefinedVariableException, SyntaxError {
		String a = InfixToPostFixCompute.inFixToPostFix(s);
		double value = evaluatePostfix(a);
		System.out.println("Line " + LineNo + ": Value of the expression " + s + ": " + value);
	}

	/**
	 * Method to print the variable For example: Print z
	 * 
	 * @param s String "print"
	 * @throws UndefinedVariableException
	 */
	public void printLine(String s) throws UndefinedVariableException {
		try {
			if (isVariable(s)) {
				char x = s.charAt(0);
				System.out.println("Line " + LineNo + ": Variable " + x + " is: " + getValue(x));
			}
		} catch (UndefinedVariableException e) {
			System.exit(1);
		}

	}

	/**
	 * Method to evaluate the posfix expression from String input
	 * 
	 * @param exp from String
	 * @return number in type double
	 * @throws UndefinedVariableException
	 */
	public double evaluatePostfix(String exp) throws UndefinedVariableException {

		Stack<Double> s = new Stack<Double>();
		Scanner tokens = new Scanner(exp);

		while (tokens.hasNext()) {
			String token = tokens.next();

			// Check if token is a number
			if (InfixToPostFixCompute.isNumber(token)) {

				Double n = Double.parseDouble(token);

				s.push(n);
			}

			// Check if token is a character
			else if (isVariable(token)) {
				try {
					double a = variable_table[token.charAt(0)].getValue();
					s.push(a);
				} catch (UndefinedVariableException e) {
					System.exit(1);
				}
			}

			else if (isOperator(token)) {
				if (s.isEmpty()) {
					System.out.println("Line: " + LineNo + ": Wrong operation!");
					System.exit(1);
				}
				double num2 = s.pop();
				if (s.isEmpty()) {
					System.out.println("Line: " + LineNo + ": Wrong operation!");
					System.exit(1);
				}
				double num1 = s.pop();

				switch (token) {
				case "+":
					s.push(num1 + num2);
					break;
				case "-":
					s.push(num1 - num2);
					break;

				case "*":
					s.push(num1 * num2);
					break;

				case "/":
					s.push(num1 / num2);
					break;
				}

			} else {
				System.out.println("Line: " + LineNo + ": Wrong operation!");
				System.exit(1);
			}
		}

		tokens.close();
		double result = s.pop();
		if (!s.isEmpty()) {
			System.out.println("Line: " + LineNo + ": Syntax error!");
			System.exit(1);
		}
		return result;

	}

	/**
	 * Main method to execute all the lines in text file
	 * 
	 * @param s input from Scanner file
	 * @throws UndefinedVariableException
	 * @throws SyntaxError
	 */
	public void test(Scanner s) throws UndefinedVariableException, SyntaxError {
		while (s.hasNextLine()) {
			line = s.nextLine();
			LineNo++;
			String[] tokens;
			tokens = line.split(" ");
			char c = tokens[0].charAt(0);
			String b = "";

			if (tokens[0].equals("read")) {
				if (tokens.length != 2)
					throw new SyntaxError();
				else
					readLine(tokens[1]); // For example: read a
			}

			else if (tokens[0].equals("print")) {
				try {
					if (tokens.length == 2)
						printLine(tokens[1]); // For example: print a
					else if (tokens.length > 2) {
						for (int i = 1; i < tokens.length; i++)
							b += tokens[i].toString(); // For example: String b = x + y
						evaluatePrintLine(b); // For example: print x + y
					}
				} catch (SyntaxError e) {
					System.exit(1);
				}
			}

			else if (isVariable(tokens[0])) {
				try {
					if (tokens.length < 3)
						throw new SyntaxError();
					if (!tokens[1].equals("="))
						throw new SyntaxError();

					for (int i = 2; i < tokens.length; i++) {
						b += tokens[i].toString();
					}
					// Calculate and assign the value to variable
					evaluateLine(c, b); // For example: a = b + c
				} catch (UndefinedVariableException e) {
					System.exit(1);
				}
			}

		}
	}

	public static void main(String[] args) throws FileNotFoundException, UndefinedVariableException, SyntaxError {
		new ESPInterpreter();
	}

}
