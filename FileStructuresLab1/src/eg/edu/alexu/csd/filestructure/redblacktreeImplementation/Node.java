package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;

public class Node<T extends Comparable<T>, V> implements INode<T, V> {
	private T key;
	private V value;
	private boolean color;
	private Node<T,V> parent;
	private Node<T,V> rightChild;
	private Node<T,V> leftChild;
	
	/*i will overload the constructor so we can 
	 * have a nil constructor beside the original one 
	*/
	
	//main constructor
	protected Node(T key,V value, boolean color,INode<T,V> rightChild,INode<T,V> leftChild){
		this.color=color;
		this.key= key;
		this.value= value;
		this.rightChild=(Node<T, V>) rightChild;
		this.leftChild=(Node<T, V>) leftChild;
	}
	//nil constructor
	protected Node(INode<T,V> right,INode<T,V> left) {
		this.color=BLACK;
		this.rightChild=(Node<T, V>) right;
		this.leftChild=(Node<T, V>) left;
	}
	protected Node() {
		this.color=BLACK;
			}
	@Override
	public void setParent(INode<T, V> parent) {
		// TODO Auto-generated method stub
		this.parent=(Node<T, V>) parent;
	}

	@Override
	public INode<T, V> getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public void setLeftChild(INode<T, V> leftChild) {
		// TODO Auto-generated method stub
		this.leftChild=(Node<T, V>) leftChild;
	}

	@Override
	public INode<T, V> getLeftChild() {
		// TODO Auto-generated method stub
		return this.leftChild;
	}

	@Override
	public void setRightChild(INode<T, V> rightChild) {
		// TODO Auto-generated method stub
		this.rightChild=(Node<T, V>) rightChild;
	}

	@Override
	public INode<T, V> getRightChild() {
		// TODO Auto-generated method stub
		return this.rightChild;
	}

	@Override
	public T getKey() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public void setKey(T key) {
		// TODO Auto-generated method stub
		this.key=key;
	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(V value) {
		// TODO Auto-generated method stub
		this.value=value;
	}

	@Override
	public boolean getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setColor(boolean color) {
		// TODO Auto-generated method stub
		this.color=color;
	}

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return this.key == null;
		
		//assumption : null means that  doesn't have a value nor key
	}

}
