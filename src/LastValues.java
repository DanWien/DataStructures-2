
public class LastValues {

	private int min,max;
	private int value;
	
	public LastValues(int value,int min, int max) {
		this.value=value;
		this.min=min;
		this.max=max;
	}
	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}
	public int getValue() {
		return value;
	}
}
