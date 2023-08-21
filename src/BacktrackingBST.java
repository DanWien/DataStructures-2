

public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    
    private static boolean clearRedoSt=true;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }
    

    public Node getRoot() {
        return root;
    }
	
    public Node search(int x) {
    	if(root!=null)
    		return root.search(x);
    	return null;
    }

    public void insert(BacktrackingBST.Node z) {
    	LastOperationBST actionMade=new LastOperationBST(z,"Insertion");
    	if(clearRedoSt) {
    		redoStack.clear();// every insert/delete action clears the redoStack as requested BUT if insert/delete is called by "retrack", redoStack won't be cleared
    		stack.push(actionMade); // remembering the last action that was taken
    	}
    	else
    		clearRedoSt=true;
    	if(root==null) { //if tree is empty, thus 'z' will be set to be the root of the tree
        	root=z;
        	root.parent=null; // root's parents does not exist
        }
        else {// the tree is not empty, thus we can pass the job to the nodes
        	root.insert(z);
        	actionMade.setXParentKey(z.parent.key);
        }                        
    }

    public void delete(Node x) {
    	LastOperationBST actionMade=new LastOperationBST(x,"Deletion");
    	if(clearRedoSt) {// every insert/delete action clears the redoStack as requested BUT if insert/delete is called by "retrack", redoStack won't be cleared
    		redoStack.clear();
    		stack.push(actionMade);
    	}
    	else
    		clearRedoSt=true;
    	if(x.parent!=null) // saving x's parent
    		actionMade.setXParentKey(x.parent.key);
    	Node toRemove,child;
        if(x.left==null | x.right==null)
        	toRemove=x;
        else {
        	toRemove=successor(x);
        	actionMade.setInheritor(toRemove.key);  // updating inheritor
        	actionMade.setInheritorParent(toRemove.parent.key);
        }
        if(toRemove.left!=null)
        	child=toRemove.left;
        else
        	child=toRemove.right;
        if(actionMade.getInheritorKey()==null & child!=null) { // setting the inheritor, if neccessary
        	actionMade.setInheritor(child.key);
        	actionMade.setInheritorParent(child.parent.key);
        }
        if(child!=null)
        	child.parent=toRemove.parent;
        if(toRemove.parent==null)
        	root=child;
        else
        	if(toRemove==toRemove.parent.left)
        		toRemove.parent.left=child;
        	else
        		toRemove.parent.right=child;
        if(toRemove!=x) {
        	toRemove.left=x.left; // updating toRemove to be with the exact same values as X (except the key)
        	toRemove.right=x.right;
        	toRemove.parent=x.parent;
        	if(x.left!=null)
        		x.left.parent=toRemove;
        	if(x.right!=null)
        		x.right.parent=toRemove;
        	if(toRemove.parent!=null) // updating the side of toRemove, if it's not the root
	        	if(!rightSide(toRemove.parent,toRemove))
	        		toRemove.parent.left=toRemove;
	        	else
	        		toRemove.parent.right=toRemove;
        	else // else, toRemove should be the root
        		root=toRemove;
        }
        x.parent=null; // disconnecting x from all its values
        x.left=null;
        x.right=null;
    }
 
    
    public Node minimum() {
        if(root==null)
        	throw new ArithmeticException("Index out of bound"); 
        else
        	return root.minimum();
    }

    public Node maximum() {
        if(root==null)
        	throw new ArithmeticException("Index out of bound");
        else
        	return root.maximum();
    }

    public Node successor(Node x) {
    	if(x.right!=null)
    		return x.right.minimum();
    	else
    		return x.successor();
    }

    public Node predecessor(Node x) {
        if(x.left!=null)
        	return x.left.maximum();
        else
        	return x.predecessor();
    }

    @Override
    public void backtrack() {
    	 if(!stack.isEmpty()) {
    		 LastOperationBST data=(LastOperationBST)stack.pop();
         	Node revertX=data.getX();
         	if(data.getAction().equals("Deletion")) {
 	        	if(data.getXPK()==null) { // originally, x was a root
 	        		if(root!=null) { //tree is not empty
 	        			Node inheritor=search(data.getInheritorKey());
 	        			nodeConnection(revertX,inheritor); // connecting between x and its inheritor
 	        			checkConsistency(inheritor,data.getIHPK()); // checking for consistency
 	        		}
 	        		root=revertX;
 	        	}
 	        	else { // originally, x wasn't a root
 	        		boolean isLeaf=data.getIHPK()==null;
 	        		Node parent=search(data.getXPK()); // this node is X's parent prior to the deletion
 	        		nodeConnection(parent,revertX);
 	        		if(!isLeaf) { // if the reverted X was a leaf, no need to check for consistency
 	        			Node inheritor=search(data.getInheritorKey());
 	        			checkConsistency(inheritor,data.getIHPK());
 	        		}
 	        	}
 	        	redoStack.push(data);
         		data.setAction("Delete");
 	        	data.setXNode(null);// we saved x's key in the right field, I decided to set the node field to null, because the node itself might carry the whole tree with him, which is a problem by space complexity.
 	        }
         	else { // last action was insertion
         		if(data.getXPK()==null) // last inserted node is the root now
         			root=null;
         		else { // last inserted node is not a root
         			if(revertX.key>revertX.parent.key) // revertX.parent will overlook revertX node
         				revertX.parent.right=null;
         			else
         				revertX.parent.left=null;
         			revertX.parent=null;
         		}
         		redoStack.push(data);
         		data.setAction("Restore");
         	}
         	// System.out.println("backtracking performed");
         }
    }
    
    
    
    @Override
    public void retrack() {
    	if(!redoStack.isEmpty()) {
        	clearRedoSt=false;
        	LastOperationBST data=(LastOperationBST)redoStack.pop();
        	if(data.getAction().equals("Delete")) { // we are required to delete the node from the last action (last action was the restoration of the node)
        		Node toRemove=search(data.getXKey());
        		delete(toRemove);
        		stack.push(data);
        		data.setXNode(toRemove);
        		data.setAction("Deletion");
        	}
        	else { //last backtrack was "deletion", thus now, we must restore
        		if(data.getXPK()!=null) // if X wasn't root prior to backtracking, we must connect him to his parent
        			nodeConnection(search(data.getXPK()),data.getX());
        		else // else, X was the root, so just updating the root pointer
        			root=data.getX();
	        	stack.push(data);
	        	data.setAction("Insertion");
	        }
        }
    }

    public void printPreOrder(){
        if(root!=null)
        	root.printPreOder();
    }
    
    public void print() {
    	printPreOrder();
    }
    
    //------------------------------------------------------------------------------
    // MY OWN HELPER METHODS
    
    private void nodeConnection(Node ancestor,Node child) {
    	child.parent=ancestor; // connecting child to his ancestor
    	if(!rightSide(ancestor,child)) { 
    		if(ancestor.left!=null) {// parent has child on left
    			ancestor.left.parent=child; // connecting child child ancestor.left
    			if(!rightSide(child,ancestor.left)) // checking on which side was ancestor.left when child was his parent
    				child.left=ancestor.left;
    			else
    				child.right=ancestor.left;		
    		}
			ancestor.left=child;
    	}
    	else {
    		if(ancestor.right!=null) {
    			ancestor.right.parent=child;
    			if(!rightSide(child,ancestor.right))
    				child.left=ancestor.right;
    			else
    				child.right=ancestor.right;
    		}
    		ancestor.right=child;
    	}	
    }
    
    private void checkConsistency(Node inheritor, Integer inheritersPrevParent) {// checks that everything is correct
    	if(inheritor.parent.right==inheritor) { // SOMETIMES	, inheritor's left child, origianlly was inheritor.parent.right child, so we have to transfer it
			if(inheritor.left!=null)
				if(!rightSide(inheritor.parent,inheritor.left)) { //MIGHT BE UNNECCESSARY! checking if there's a node that should be inheriter's parent node
					inheritor.parent.left=inheritor.left;
					inheritor.left.parent=inheritor.parent;
					inheritor.left=null;
				}
    	}
		if(inheritor.parent.key!=inheritersPrevParent) { // inheritor is not in the right place
			Node prevP=search(inheritersPrevParent);
			if(inheritor.parent.right==inheritor) {
				inheritor.right.parent=inheritor.parent;
				inheritor.parent.right=inheritor.right;
				inheritor.right=null;
			}
			nodeConnection(prevP,inheritor); // connecting inheritor to its original parent
		}
    }

	private boolean rightSide(Node node1, Node node2) { // returns true if node2 is on the right side of node2, false otherwise
    	return node1.key<node2.key;
    }
	//--------------------------------------------------------------------------------------

    public static class Node{
    	//These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        
        private BacktrackingBST.Node parent; 
        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public void print() {
        	if(left!=null)
				left.print();
        	System.out.print(key + " ");
			if(right!=null)
				right.print();
			
		}

		public void insert(Node z) {
			if(z.key>key) {
				if(right!=null)
					right.insert(z);
				else {
					right=z;
					right.parent=this;
				}
			}
			else {
				if(left!=null)
					left.insert(z);
				else {
					left=z;
					left.parent=this;
				}
			}
		}

		private Node predecessor() {
			if(this.parent==null) // no predecessor
				return null;
        	if(this==this.parent.left) 
				return this.parent.predecessor();
			else
				return this.parent;
		}

		private Node successor() {
			if(this.parent==null) // no successor
				return null;
			if(this==this.parent.right) 
				return this.parent.successor();
			else
				return this.parent;
		}
		
		private Node maximum() {
			if(right!=null)
				return right.maximum();
			return this;
		}

		private Node minimum() {
			if(left!=null) // the minimal value in a BST will be found in the "left-most" node
				return left.minimum();
			return this;
		}

		private Node search(int x) {
			if(key==x)
				return this;
			else
				if(key>x) { // if key>x, than x might be found in the left subtree
					if(left!=null)
						return left.search(x);
				}
				else
					if(right!=null) //otherwise, x might be found in the right subtree
						return right.search(x);
			return null; // 'x' does not exist in the tree
		}

		private void printPreOder() {
			System.out.print(key + " ");
			if(left!=null)
				left.printPreOder();
			if(right!=null)
				right.printPreOder();
		}

		public int getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

}
