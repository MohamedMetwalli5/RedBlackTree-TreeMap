package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T,V> nil =new Node<T,V>();
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
			if(root.getKey().compareTo(key)==0)
				return root.getValue();
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
			root=new Node<T,V>(key,value,Node.BLACK,nil,nil);
			root.setParent(nil);
			return;
		}
		Node<T,V> temp =new Node<T,V>(root.getKey(),root.getValue(),root.getColor(),root.getRightChild(),root.getLeftChild());
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
		temp=new Node<T,V>(key,value,Node.RED,nil,nil);
		temp.setParent(parent);
		//call the fix up
	}

	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		return false;
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
	private boolean compareTwoNodes(INode<T, V> iNode,Node<T,V> B ) {
		return (iNode.getKey().compareTo(B.getKey())==0) ? true:false;
	}
	

}
