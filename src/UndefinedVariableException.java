
public class UndefinedVariableException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Parameterless Constructor
	
	public UndefinedVariableException() {
		System.out.println("Line " + ESPInterpreter.LineNo + ": Error! Undefined Variable");
	}
	
	

}
