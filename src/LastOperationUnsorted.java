
public class LastOperationUnsorted extends LastOperation {
	private int index;
	
	public LastOperationUnsorted(int value,int index,String operationName) {
		super(value, operationName);
		this.index=index;
	}
	public int getIndex() {
		return index;
	}
}
