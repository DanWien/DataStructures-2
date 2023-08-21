
public class BacktrackingArray implements Array<Integer>, Backtrack {
	private Stack stack;
	private int[] arr;
	private int size;

	// Do not change the constructor's signature
	public BacktrackingArray(Stack stack, int size) {
		this.stack = stack;
		arr = new int[size];
		this.size = 0;
	}

	@Override
	public Integer get(int index) {
		if (index >= size | index<0)
			throw new IllegalArgumentException("No element in input index");
		return arr[index];
	}

	@Override
	// Applying linear search.
	public Integer search(int k) {
		for (int i = 0; i < size; i++) {
			if (arr[i] == k)
				return i;
		}
		return -1;
	}

	@Override
	
	public void insert(Integer x) {
		if (size >= arr.length)
			throw new RuntimeException("Array is Full");
		arr[size] = x;
		size++;
		stack.push("Insert");
	}

	@Override
	// Array is unsorted , so we move the last element to the deleted element index.
	public void delete(Integer index) {
		if (index >= size | index<0)
			throw new IllegalArgumentException("No element in input index");
		stack.push(index);
		stack.push(arr[index]);
		stack.push("Delete");
		arr[index]=arr[size-1];
		arr[size-1]=0;
		size--;
	}

	@Override
	public Integer minimum() {
		if (size == 0)
			throw new IllegalArgumentException("Array is Empty");
		int minIdx = 0;
		for (int i = 1 ; i<size ; i++) {
			if (arr[i] < arr[minIdx])
				minIdx=i;
		}
		return minIdx;
	}

	@Override
	public Integer maximum() {
		if (size == 0)
			throw new IllegalArgumentException("Array is Empty");
		int maxIdx = 0;
		for (int i = 1 ; i<size ; i++) {
			if (arr[i] > arr[maxIdx])
				maxIdx=i;
		}
		return maxIdx;
	}

	@Override
	public Integer successor(Integer index) {
		if (index >= size | index<0)
			throw new IllegalArgumentException("No element in input index");
		int sucIdx = this.maximum();
		if (sucIdx == index)
			throw new IllegalArgumentException("Max has no successor");
		for (int i = 0 ; i<size ; i++) {
			if (arr[index] < arr[i] && arr[i] < arr[sucIdx])
				sucIdx = i;
		}
		return sucIdx;
	}

	@Override
	public Integer predecessor(Integer index) {
		if (index >= size | index<0)
			throw new IllegalArgumentException("No element in input index");
		int predIdx = this.minimum();
		if (predIdx == index)
			throw new IllegalArgumentException("Min has no predeccessor");
		for (int i = 0 ; i<size ; i++) {
			if (arr[i] < arr[index] && arr[i] > arr[predIdx])
				predIdx = i;
		}
		return predIdx;
	}

	@Override
	// "Cancelling" our last insert or delete actions accordingly.
	public void backtrack() {
		if(!stack.isEmpty()) {
			if(stack.pop().equals("Insert")) {
				arr[size]=0;
				size--;
			}
			else {
				int elementToReturn = (int)stack.pop();
				int returnIndex = (int)stack.pop();
				arr[size] = arr[returnIndex];
				arr[returnIndex] = elementToReturn;
				size++;
			}
		}
	}

	@Override
	public void retrack() {
		/////////////////////////////////////
		// Do not implement anything here! //
		/////////////////////////////////////
	}

	@Override
	public void print() {
		String output = "";
		for (int i=0; i<size ; i++) 
			output = output + arr[i] + " ";	
		System.out.print(output);
	}
}
