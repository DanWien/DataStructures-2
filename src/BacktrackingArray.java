public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int arrIndex;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        arrIndex = 0;
    }

    @Override
    public Integer get(int index){
        if (index < 0 || index >= arrIndex) {
        	throw new IllegalArgumentException("illegal index");
        }
    	return arr[index];
    }

    @Override
    public Integer search(int k) {
        for (int i = 0; i < arrIndex; i++) {
        	if (arr[i] == k) {
        		return i;
        	}
        }
    	return -1;
    }

    @Override
    public void insert(Integer x) {
    	if (arrIndex == arr.length) {
    		throw new RuntimeException("array is full");
    	}
    	stack.push("insertion");
        arr[arrIndex++] = x;
    }

    @Override
    public void delete(Integer index) {
    	if (index < 0 || index >= arrIndex) {
        	throw new IllegalArgumentException("illegal index");
        }
    	stack.push(arr[index]);
    	stack.push(index);
    	stack.push("deletion");
    	arr[index] = arr[--arrIndex];
    }

    @Override
    public Integer minimum() {
    	if (arrIndex == 0) {
        	throw new RuntimeException("no minimum in empty set");
        }
        int min = arr[0], minIndex = 0;
    	for (int i = 1; i < arrIndex; i++) {
    		if (arr[i] < min) {
    			min = arr[i];
    			minIndex = i;
    		}
    	}
    	return minIndex;
    }

    @Override
    public Integer maximum() {
    	if (arrIndex == 0) {
        	throw new RuntimeException("no maximum in empty set");
        }
    	int max = arr[0], maxIndex = 0;;
    	for (int i = 1; i < arrIndex; i++) {
    		if (arr[i] > max) {
    			max = arr[i];
    			maxIndex = i;
    		}
    	}
    	return maxIndex;
    }

    @Override
    public Integer successor(Integer index) {
    	if (index < 0 || index >= arrIndex) {
        	throw new IllegalArgumentException("illegal index");
        }
    	int maxIndex = maximum();
    	if (arr[index] == arr[maxIndex]) {
    		throw new RuntimeException("there is no successor to the maximum");
    	}
    	int value = arr[index];
        int closerFromUp = Integer.MAX_VALUE, closerIndex = maxIndex;
        for (int i = 0; i < arrIndex; i++) {
        	if (arr[i] < closerFromUp && arr[i] > value) {
        		closerFromUp = arr[i];
        		closerIndex = i;
        	}
        }
        return closerIndex;
    }
    
    @Override
    public Integer predecessor(Integer index) {
    	if (index < 0 || index >= arrIndex) {
        	throw new IllegalArgumentException("illegal index");
        }
    	int minIndex = minimum();
        if (arr[index] == arr[minIndex]) {
        	throw new RuntimeException("there is no predecessor to the minimum");
        }
    	int value = arr[index];
        int closerFromDown = Integer.MIN_VALUE, closerIndex = minIndex;
        for (int i = 0; i < arrIndex; i++) {
        	if (arr[i] > closerFromDown && arr[i] < value) {
        		closerFromDown = arr[i];
        		closerIndex = i;
        	}
        }
        return closerIndex;
    }

    @Override
    public void backtrack() {
    	if (!stack.isEmpty()) {
	    	if (stack.pop().equals("deletion")) {
	    		int index = (int) stack.pop();
	    		int value = (int) stack.pop();
	    		arr[arrIndex++] = arr[index];
	    		arr[index] = value;
	    	} else {
	    		arrIndex--;
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
    	for (int i = 0; i < arrIndex; i++) {
    		System.out.print(arr[i] + " ");
    	}
//        if (arr.length > 0) {
//        	for (int i = 0; i < arr.length - 1; i++) {
//        		System.out.print(arr[i] + " ");
//        	}
//        	System.out.print(arr[arr.length - 1]);
//        }
    }
    
}
