

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    private int size;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        this.size=0;
    }
    
    @Override
    public Integer get(int index){
        if (index>size | index < 0)
            throw new IllegalArgumentException("No element in input index");
    	return arr[index]; 
    }

    @Override
    // Applying binarySearch.
    public Integer search(int k) {
        int high = size-1;
        int low = 0;
        while (high>=low) {
        	int mid = (high+low) / 2;
        	if (arr[mid] == k)
        		return mid;
        	if (arr[mid]>k)
        		high=mid-1;
        	else 
        		low=mid+1;
        }
        return -1;
    }

    @Override
    //Finding the location to insert and moving the elements accordingly.
    public void insert(Integer x) {
    	if (size >= arr.length)
			throw new RuntimeException("Array is Full");
    	int idx=0;
        while(size > 0 && (x>arr[idx] & idx<=size-1))
        	idx++;
        stack.push(idx);
        stack.push("insert");
        for(int i=size; i>idx; i--)
        	arr[i]=arr[i-1];
        arr[idx]=x;
        size++;
    }

    @Override
    public void delete(Integer index) {
    	if (index >= size | index<0)
			throw new IllegalArgumentException("No element in input index");
    	if(size==0)
    		throw new RuntimeException();
        stack.push(index);
        stack.push(arr[index]);
        stack.push("delete");
    	for(int i=index; i<size-1; i++)
    		arr[i]=arr[i+1];
        arr[size-1]=0;
    	size--;
    }

    @Override
    public Integer minimum() {
    	if(size==0)
    		throw new RuntimeException();
    	return 0;
    }

    @Override
    public Integer maximum() {
    	if(size==0)
    		throw new RuntimeException();
    	return size-1;
    }

    @Override
    public Integer successor(Integer index) {
    	if (index >= size)
			throw new IllegalArgumentException("No element in input index");
    	if (index==size-1)
			throw new IllegalArgumentException("Max has no successor");
    	return index+1;
    }

    @Override
    public Integer predecessor(Integer index) {
    	if (index >= size)
			throw new IllegalArgumentException("No element in input index");
    	if (index==0)
			throw new IllegalArgumentException("Min has no predecessor");
    	return index-1;
    }

    @Override
    // "Cancelling" our last insert or delete actions accordingly.
    public void backtrack() {
        if(!stack.isEmpty()) {
            if(stack.pop().equals("insert")) {
                for(int i = (int)stack.pop(); i<size ; i++)
                    arr[i] = arr[i+1];
                size--;
            }
            else {
                int toReturn = (int)stack.pop();
                int returnIndex = (int)stack.pop();
                for(int i = size ; i>returnIndex ; i--)
                  arr[i]=arr[i-1];
                arr[returnIndex] = toReturn;
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
