package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T,V> nil =new Node<T,V>();
	private Node<T,V> nilLeaf =new Node<T,V>();
	private Node<T,V> root=nil;
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
		this.root=nil;
	}

	@Override
	public V search(T key) {
		// TODO Auto-generated method stub
		Node<T,V> temp =new Node<T,V>(root.getKey(),root.getValue(),root.getColor(),root.getRightChild(),root.getLeftChild());
		while(!temp.isNull()) {
			if(temp.getKey().compareTo(key)==0)
				return temp.getValue();
			else if(temp.getKey().compareTo(key)<0) {
			temp=(Node<T, V>) temp.getRightChild();
			}
			else {
			temp=(Node<T, V>) temp.getLeftChild();
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
			root=new Node<T,V>(key,value,Node.BLACK,nilLeaf,nilLeaf);
			root.setParent(nil);
			nilLeaf.setParent(root);
			return;
		}
		
 		Node<T,V> temp=root;
 		while(!temp.isNull()) {
			if(temp.getKey().compareTo(key)<0) {
				temp=(Node<T, V>) temp.getRightChild();
			}
			else if(temp.getKey().compareTo(key)>0) {
				temp=(Node<T, V>) temp.getLeftChild();
			}
			else
				break;//the same key so i update the associated node
		}
		Node <T,V>parent= (Node<T, V>) temp.getParent();
		temp.setColor(Node.RED);
		temp.setKey(key);
		temp.setValue(value);
		temp.setRightChild(nilLeaf);
		temp.setLeftChild(nilLeaf);
		temp.setParent(parent);
		
		//call the fix up
		insertFix(temp);
	}

	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		return false;
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
		return (iNode.getKey().compareTo(B.getKey())==0) ? true:false;
	}
	

}
