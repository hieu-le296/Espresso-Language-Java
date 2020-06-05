
public class SyntaxError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SyntaxError() {
		System.out.println("Line " + ESPInterpreter.lineNo + ": Syntax Error");
	}

}
