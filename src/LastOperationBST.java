public class LastOperationBST {

	private BacktrackingBST.Node x;
	Integer parentKey,inheritor,inheritorsParent, xKey;
	String action;
	
	public LastOperationBST(BacktrackingBST.Node x,String action) {
		this.x=x;
		this.action=action;
		parentKey=null;
		inheritor=null;
		inheritorsParent=null;
		xKey=x.getKey();
	}
	


	public BacktrackingBST.Node getX(){
		return x;
	}
	
	public void setXNode(BacktrackingBST.Node x) {
		this.x=x;
	}
	
	public Integer getXKey() {
		return xKey;
	}
	//--------------------------------------------------- INHERITOR
	public void setInheritor(Integer inheritor) {
		this.inheritor=inheritor;
	}
	public void setInheritorParent(Integer inheritorsParent) {
		this.inheritorsParent=inheritorsParent;
	}
	public Integer getInheritorKey() {
		return inheritor;
	}
	
	public Integer getIHPK() { // IHPK = inhertior's parent key
		return inheritorsParent;
	}
	//--------------------------------------------------- X
	public void setXParentKey(Integer parentKey) {
		this.parentKey=parentKey;
	}
	public Integer getXPK() {
		return parentKey;
	}
	//---------------------------------------------------
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action=action;
	}
}
