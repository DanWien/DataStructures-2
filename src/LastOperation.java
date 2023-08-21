
public class LastOperation {

	private int value;
	private String operationName;
	
	public LastOperation(int value, String operationName) {
		this.value=value;
		this.operationName=operationName;
	}
	
	public int getValue() {
		return value;
	}
	public String getOperation() {
		return operationName;
	}
}
