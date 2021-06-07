package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T,V> nil = new Node<T,V>();
	private Node<T,V> root = nil;
	@Override
	public INode<T, V> getRoot() {
		// TODO Auto-generated method stub
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.root.isNull();
		//the root is nil
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.root = nil;
	}

	@Override
	public V search(T comparable) {
		// TODO Auto-generated method stub
		Node<T,V> temp =new Node<T,V>(root.getKey(),root.getValue(),root.getColor(),root.getRightChild(),root.getLeftChild());
		
		while(!temp.isNull()) {
			if(temp.getKey().compareTo(comparable)==0)
				return temp.getValue();
			else if(temp.getKey().compareTo(comparable)<0) {
			temp = (Node<T, V>) temp.getRightChild();
			}
			else {
			temp = (Node<T, V>) temp.getLeftChild();
			}
		}
		return null;
	}

	@Override
	public boolean contains(T key) {
		// TODO Auto-generated method stub
		return (search(key)==null) ?false:true;
	}

	@Override
	public void insert(T key, V value) {
		// TODO Auto-generated method stub
 		if(root.isNull()) {//inserting in empty tree
			root=new Node<T,V>(key,value,Node.BLACK,nil,nil);
			root.setParent(nil);
			return;
		}
		
 		Node<T,V> temp=root;
 		Node<T,V> child=temp;
  		boolean flag=false;
  		boolean update =false;
 		while(!child.isNull()) {
 			temp=child;
			if(temp.getKey().compareTo(key)<0) {
				
				child=(Node<T, V>) temp.getRightChild();
				flag=false;
				
			}
			else if(temp.getKey().compareTo(key)>0) {
				child=(Node<T, V>) temp.getLeftChild();
				flag=true;
				
			}
			else{//the same key so i update the associated node
				temp.setValue(value);
				update=true;
				break;
				
			}
		}
 		if(!update) {
		Node<T,V> holder=new Node(key,value,Node.RED,nil,nil);
		holder.setParent(temp);
		if(flag)
			temp.setLeftChild(holder);
		else
			temp.setRightChild(holder);
		//call the fix up
		insertFix(holder);
 		}
	}

	//<------------------------------------------------------------------------------------------------------------------------------------>	
	
	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		if(search(key) == null) {  // if the tree doesn't contain the node to be deleted
			return false;
		}else {
			removeHelper(key);
			return true;
		}
	}
	

	private void removeHelper(T key){
		Node z = searchDelete(key);
		Node x = new Node<>(); //nil
		Node y = new Node<>(); //nil

		if (z.getLeftChild().isNull() || z.getRightChild().isNull())
			y = z;
		else y = treeSuccessor(z);
		if (!y.getLeftChild().isNull()) {
			x = (Node) y.getLeftChild();
		}else {
			x = (Node)y.getRightChild();
		}
		x.setParent(y.getParent());
		if (y.getParent().isNull()) {
			root = x;
		}else if (!y.getParent().getLeftChild().isNull() && compareTwoNodes(y.getParent().getLeftChild(),y)) {
			y.getParent().setLeftChild(x);
		}else if (!y.getParent().getRightChild().isNull() && compareTwoNodes(y.getParent().getRightChild(),y)) {
			y.getParent().setRightChild(x);
		}
		if (!compareTwoNodes(y, z)){
			z.setKey(y.getKey());
		}
		if (y.getColor() == Node.BLACK)
			removeFixup(x);
	}

	private Node treeSuccessor(Node x){
		if (!x.getLeftChild().isNull()) {
			return treeMinimum((Node) x.getRightChild());
		}
		Node result = (Node) x.getParent();
		while (!result.isNull() && compareTwoNodes(x,result.getRightChild())){
			x = result;
			result = (Node) result.getParent();
		}
		return result;
	}
	
	private Node treeMinimum(Node node){
		while (!node.getLeftChild().isNull()) {
			node = (Node) node.getLeftChild();
		}
		return node;
	}
 
	
	private void removeFixup(Node toBeDeleted){
		Node sibling=new Node();
		while (!compareTwoNodes(toBeDeleted, root) && toBeDeleted.getColor() == Node.BLACK){
			
			if (compareTwoNodes(toBeDeleted, toBeDeleted.getParent().getLeftChild())){//check if the deleted node is a left child
				sibling = (Node) toBeDeleted.getParent().getRightChild(); //getting sibling
				//case 1 sibling is red
				if (sibling.getColor() == Node.RED){
					sibling.setColor(Node.BLACK);
					toBeDeleted.getParent().setColor(Node.RED);
					rotateLeft((Node<T, V>) toBeDeleted.getParent());
					sibling = (Node) toBeDeleted.getParent().getRightChild();
				}
				//case 2 ,both sibling's children are black
				if (sibling.getLeftChild().getColor() == Node.BLACK &&
							sibling.getRightChild().getColor() == Node.BLACK){
					sibling.setColor(Node.RED);
					toBeDeleted = (Node) toBeDeleted.getParent();
				}else{
					//sibling's right child is black
					if (sibling.getRightChild().getColor() == Node.BLACK){
						sibling.getLeftChild().setColor(Node.BLACK);
						sibling.setColor(Node.RED);
						rotateRight(sibling);
						sibling = (Node) toBeDeleted.getParent().getRightChild();
					}
					//sibling is black and it's right child is red
					sibling.setColor(toBeDeleted.getParent().getColor());
					toBeDeleted.getParent().setColor(Node.BLACK);
					sibling.getRightChild().setColor(Node.BLACK);
					rotateLeft((Node<T, V>) toBeDeleted.getParent());
					toBeDeleted = root;
				}
			}else{
				//if the deleted is a right child
				sibling = (Node) toBeDeleted.getParent().getLeftChild();
				
				//case 1 sibling is red
				if (sibling.getColor() == Node.RED){
					sibling.setColor(Node.BLACK);
					toBeDeleted.getParent().setColor(Node.RED);
					rotateRight((Node) toBeDeleted.getParent());
					sibling = (Node) toBeDeleted.getParent().getLeftChild();
				}
				//case 2 both sibling's children are black
				if (sibling.getRightChild().getColor() == Node.BLACK &&
							sibling.getLeftChild().getColor() == Node.BLACK){
					sibling.setColor(Node.RED);
					toBeDeleted = (Node) toBeDeleted.getParent();
				}else{
					//case 3 sibling's left child is black
					 if (sibling.getLeftChild().getColor() == Node.BLACK){
						sibling.getRightChild().setColor(Node.BLACK);
						sibling.setColor(Node.RED);
						rotateLeft(sibling);
						sibling = (Node) toBeDeleted.getParent().getLeftChild();
					}
					 //case 4 sibling is black and it's left is red
					sibling.setColor(toBeDeleted.getParent().getColor());
					toBeDeleted.getParent().setColor(Node.BLACK);
					sibling.getLeftChild().setColor(Node.BLACK);
					rotateRight((Node<T, V>) toBeDeleted.getParent());
					toBeDeleted = root;
				}
			}
		}
		toBeDeleted.setColor(Node.BLACK);
	}
	
	public Node<T, V> searchDelete(T key) {
		Node<T,V> temp =new Node<T,V>(root.getKey(),root.getValue(),root.getColor(),root.getRightChild(),root.getLeftChild());
		while(!temp.isNull()) {
			if(temp.getKey().compareTo(key) == 0) {
				return temp;
			}else if(temp.getKey().compareTo(key)<0) {
				temp = (Node<T, V>) temp.getRightChild();
			}else {
				temp = (Node<T, V>) temp.getLeftChild();
			}
		}
		return null;
	}
		
	
	
	//<------------------------------------------------------------------------------------------------------------------------------------>
	
	/*takes the new inserted node and checks if there is a 
	 * violation and fix it depending on the current violation*/
	private void insertFix(Node<T,V> inserted) {
		Node<T,V> uncle=nil;
		while(inserted.getParent().getColor()==Node.RED) {
			//the parent of the inserted is a left child
		if(compareTwoNodes(inserted.getParent(),inserted.getParent().getParent().getLeftChild())) {
			//get the uncle of the inserted
			uncle=(Node<T, V>) inserted.getParent().getParent().getRightChild();
			
			//case 1 uncle is red so we recolor
			if(uncle.getColor()==Node.RED) {
				inserted.getParent().setColor(Node.BLACK);
				uncle.setColor(Node.BLACK);
				uncle.getParent().setColor(Node.RED);
				inserted=(Node<T, V>) inserted.getParent().getParent();	
			}
			//case 2 uncle is black and the inserted is a right child
			else if(compareTwoNodes(inserted,inserted.getParent().getRightChild())) {
				//left rotate around parent 
				inserted=(Node<T, V>) inserted.getParent();
				rotateLeft(inserted);
			}
			//case 3 if uncle is black and inserted is a left child
			else if(compareTwoNodes(inserted,inserted.getParent().getLeftChild())){
				//recolor and rotate around grandparent
				inserted.getParent().setColor(Node.BLACK);
				inserted.getParent().getParent().setColor(Node.RED);
				rotateRight((Node<T, V>) inserted.getParent().getParent());
					
			}
		}
		else {
			//the inserted parent is a right child
			uncle=(Node<T, V>) inserted.getParent().getParent().getLeftChild();
			
			//case 1 uncle is red so we recolor
			if(uncle.getColor()==Node.RED) {
				inserted.getParent().setColor(Node.BLACK);
				uncle.setColor(Node.BLACK);
				uncle.getParent().setColor(Node.RED);
				inserted=(Node<T, V>) inserted.getParent().getParent();	
			}
			//case 2 uncle is black and the inserted is a left child
			else if(compareTwoNodes(inserted,inserted.getParent().getLeftChild())) {
				//left rotate around parent 
				inserted=(Node<T, V>) inserted.getParent();
				rotateRight(inserted);
			}
			//case 3 if uncle is black and inserted is a right child
			else if(compareTwoNodes(inserted,inserted.getParent().getRightChild())){
				//recolor and rotate around grandparent
				inserted.getParent().setColor(Node.BLACK);
				inserted.getParent().getParent().setColor(Node.RED);
				rotateLeft((Node<T, V>) inserted.getParent().getParent());
					
			}
		}
	}
	root.setColor(Node.BLACK);
	}
	private void rotateRight(Node<T,V> toBeRotated) {
		
		Node <T,V> temp=(Node<T, V>) toBeRotated.getLeftChild();
		toBeRotated.setLeftChild(temp.getRightChild());
		
		if(!temp.getRightChild().isNull())
			temp.getRightChild().setParent(toBeRotated);
		temp.setParent(toBeRotated.getParent());
		//if the parent is nil so toBeRotated is the root so we modify it
		if(toBeRotated.getParent().isNull())
			this.root=temp;
		else if(compareTwoNodes(toBeRotated.getParent().getRightChild(),toBeRotated))//toBeRotated is a right child
			toBeRotated.getParent().setRightChild(temp);
		else //toBeRotated is  a left child
			toBeRotated.getParent().setLeftChild(temp);
		temp.setRightChild(toBeRotated);
		toBeRotated.setParent(temp);
	}
	private void rotateLeft(Node<T,V> toBeRotated) {
		
		Node <T,V> temp=(Node<T, V>) toBeRotated.getRightChild();
		toBeRotated.setRightChild(temp.getLeftChild());
		
		
		if(!temp.getLeftChild().isNull())
			temp.getLeftChild().setParent(toBeRotated);
		temp.setParent(toBeRotated.getParent());
		//if the parent is nil so toBeRotated is the root so we modify it
		if(toBeRotated.getParent().isNull())
			this.root=temp;
		else if(compareTwoNodes(toBeRotated.getParent().getLeftChild(),toBeRotated))//toBeRotated is a left child
			toBeRotated.getParent().setLeftChild(temp);
		else //toBeRotated is  a right child
			toBeRotated.getParent().setRightChild(temp);
		temp.setLeftChild(toBeRotated);
		toBeRotated.setParent(temp);
	}
	
	//return true if the two nodes have the same key(means that they are the same node)
	private boolean compareTwoNodes(INode<T, V> iNode,INode<T,V> B ) {
		if(B.isNull()||iNode.isNull())
			return false;
		return (iNode.getKey().compareTo(B.getKey())==0) ? true:false;
	}
	

}
