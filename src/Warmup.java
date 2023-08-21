public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        int i = 0;
        while (i < arr.length) {
    		for (int j = 0; j < forward && i < arr.length; j++) {
    			if (arr[i] == x) {
    				return i;
    			} else {
    				myStack.push(i);
    				i++;
    			}
    		}
    		for (int j = 0; j < back && i < arr.length; j++) {
    			i = (int) myStack.pop();
    		}
    	}
        return -1;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
        	myStack.push(low);
        	myStack.push(high);
        	int mid = (low + high) / 2;
        	if (arr[mid] == x) {
        		return mid;
        	} else if (arr[mid] > x) {
        		high = mid - 1;
        	} else {
        		low = mid + 1;
        	}
        	int incos = Consistency.isConsistent(arr);
        	for (int i = 0; i < incos; i++) {
        		high = (int) myStack.pop();
        		low = (int) myStack.pop();
        	}
        }
        return -1;
    }
    
//    private static int isConsistent(int[] arr) {
//        double res = Math.random() * 100 - 75;
//
//        if (res > 0) {
//            return (int)Math.round(res / 10);
//        } else {
//            return 0;
//        }
//    }
    
}
