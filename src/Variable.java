
public class Variable {
	private double value;
	private boolean initialized;
	/**
	 * @param initialized
	 * 
	 */
	public Variable() {
		this.initialized = false;
	}
	
	public double getValue() throws UndefinedVariableException{
		if (!initialized)
			throw new UndefinedVariableException();
		else		
			return value;
	}
	public void setValue(double y) {
		this.value = y;
		this.initialized = true;
	}
	
	
	
	
	
	

}
