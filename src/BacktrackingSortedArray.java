public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    private int arrSize;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        arrSize = 0;
    }
    
    @Override
    public Integer get(int index){
        if (index < 0 || index >= arrSize) {
        	throw new IllegalArgumentException("illegal index");
        }
        return arr[index];
    }

    @Override
    public Integer search(int k) {
        int low = 0, high = arrSize - 1;
    	while (low <= high) {
    		int mid = (low + high) / 2;
    		if (arr[mid] == k) {
    			return mid;
    		}
    		if (arr[mid] < k) {
    			low = mid + 1;
    		} else {
    			high = mid - 1;
    		}
    	}
    	return -1;
    }

    @Override
    public void insert(Integer x) {
    	insert(x, true);
    }
    
    private void insert(Integer x, boolean regularInsertion) {
    	if (arrSize == arr.length) {
    		throw new RuntimeException("array is full");
    	}
    	arrSize++;
    	int helperIndex = arrSize - 1;
    	while (helperIndex > 0 && arr[helperIndex - 1] > x) {
    		arr[helperIndex] = arr[helperIndex - 1];
    		helperIndex--;
    	}
    	if (regularInsertion) {
    		stack.push(helperIndex);
    		stack.push("insertion");
    	}
    	arr[helperIndex] = x;
    }

    @Override
    public void delete(Integer index) {
    	delete(index, true);
    }
    
    private void delete(Integer index, boolean regularDeletion) {
    	if (index < 0 || index >= arrSize) {
        	throw new IllegalArgumentException("illegal index");
        }
    	if (regularDeletion) {
    		stack.push(arr[index]);
    		stack.push("deletion");
    	}
    	for (int i = index; i < arrSize - 1; i++) {
    		arr[i] = arr[i + 1];
    	}
    	arrSize--;
    }

    @Override
    public Integer minimum() {
    	if (arrSize == 0) {
        	throw new RuntimeException("no minimum in empty set");
        }
    	return 0;
    }

    @Override
    public Integer maximum() {
    	if (arrSize == 0) {
        	throw new RuntimeException("no maximum in empty set");
        }
    	return arrSize - 1;
    }

    @Override
    public Integer successor(Integer index) {
    	if (index < 0 || index >= arrSize - 1) {
        	throw new IllegalArgumentException("illegal index");
        }
        return index + 1;
    }

    @Override
    public Integer predecessor(Integer index) {
    	if (index <= 0 || index >= arrSize) {
        	throw new IllegalArgumentException("illegal index");
        }
        return index - 1;
    }

    @Override
    public void backtrack() {
    	if (!stack.isEmpty()) {
	    	if (stack.pop().equals("deletion")) {
	    		insert((int) stack.pop(), false);
	    	} else {
	    		delete((int) stack.pop(), false);
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
    	for (int i = 0; i < arrSize; i++) {
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
