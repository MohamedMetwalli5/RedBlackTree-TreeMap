package eg.edu.alexu.csd.filestructure.redblacktree.implementation;

import java.util.ArrayList;
import java.util.Map;

import javax.management.RuntimeErrorException;


public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private final INode<T,V> NIL = new Node<T,V>();
	private INode<T,V> root = new Node<T,V>();
	public int size = 0;
	@Override
	public INode<T, V> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
		//the root is nil
	}

	@Override
	public void clear() {
		this.root = NIL;
		size = 0;
	}

	@Override
	public V search(T comparable) throws RuntimeErrorException {
		if(comparable == null)
			throw new RuntimeErrorException(null);
		Node<T,V> temp =new Node<T,V>(root.getKey(),root.getValue(),root.getColor(),root.getRightChild(),root.getLeftChild());
		
		while(temp == null || !temp.isNull()) {
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
		if(key == null)
			throw new RuntimeErrorException(null);
		return (searchNode(key)==null) ?false:true;
	}

	@Override
	public void insert(T key, V value) {
		if(key == null || value == null)
			throw new RuntimeErrorException(null);
 		if(root.isNull()) {//inserting in empty tree
			root=new Node<T,V>(key,value,Node.BLACK,null,null);
			root.setParent(NIL);
			giveNilChildren(root);
			size++;
			return;
		}
		
 		INode<T,V> temp = root;
 		INode<T,V> child = temp;
 		while(!child.isNull()) {
 			temp=child;
			if(temp.getKey().compareTo(key)<0) {
				child=(Node<T, V>) temp.getRightChild();
			}
			else if(temp.getKey().compareTo(key)>0) {
				child=(Node<T, V>) temp.getLeftChild();
			}
			else{//the same key so i update the associated node
				temp.setValue(value);
				return;
			}
		}


		// make child red and give it the key and value
		child.setColor(Node.RED);
		child.setKey(key);
		child.setValue(value);
		
		// Make two nil nodes and connect them with child
		giveNilChildren(child);

		//call the fix up
		insertFix((Node<T, V>) child);
		
		size++;
	}

	//<------------------------------------------------------------------------------------------------------------------------------------>	
	/*

	Delete : 2 children (root and internal)
	Delete : 1 Child
	Delete : 0 Children (children are nil)
	*/


	@Override
	public boolean delete(T key) {
		if(key == null)
			throw new RuntimeErrorException(null);

		Map.Entry<T, V> ret = poll(key);

		if (ret == null)
			return false;
		
		return true;
		
	}
	
	public Map.Entry<T,V> poll(T key) {
		if (key == null)
			return null;
			
		Node<T, V> toBeDeleted = (Node<T, V>) searchNode(key);
		if(toBeDeleted == null)   // if the tree doesn't contain the node to be deleted
			return null;

		

		Map.Entry<T,V> ret = toBeDeleted.toEntry();
		
	
		if (toBeDeleted.internalNode()){
			
			Node<T,V> successor = (Node<T,V>)minValue(toBeDeleted.getRightChild());

			toBeDeleted.setKey(successor.getKey());
			toBeDeleted.setValue(successor.getValue());
			removeHelper(successor);
			
		}else {
			removeHelper(toBeDeleted);
		}
		
		size--;
		return ret;
		

	}
	

	// Deletes a non internal node (has at most one child)
	private void removeHelper(Node<T,V> toBeDeleted){
		Node<T,V> z = toBeDeleted;

		
		if (z.isRed()) {
			z.setKey(null);
			z.setValue(null);
			z.setLeftChild(null);
			z.setRightChild(null);
			z.setColor(Node.BLACK);
			return;
		}

		if (z.hasRedChild()){
			// Case 1
			
			// Get the non nil child
			Node<T,V> child = (Node<T, V>) z.getRedChild();
			
			// Copy child's key and value to z
			z.setKey(child.getKey());
			z.setValue(child.getValue());
			
			// Set z's children to new nil nodes
			giveNilChildren(z);
			z.setColor(Node.BLACK);
			return;
		}else {
			// Both children are nil nodes
			// Make z nil
			// fix double black at z

			z.setKey(null);
			z.setValue(null);
			z.setLeftChild(null);
			z.setRightChild(null);
			removeFixup(z);
		}


	}


	public INode<T,V> getSuccessor(Node<T,V> x){
		if (x.getRightChild().isNull()) 
			return null;
		
		return minValue(x.getRightChild());
	}
	
	public INode<T,V> getPredecessor(Node<T,V> x){
		if (x.getLeftChild().isNull()) 
			return null;
		
		return maxValue(x.getLeftChild());
	}
	

	
	// The smallest value in the subtree rooted at node
	public  INode<T,V>  minValue(INode<T,V> node){
	    INode<T,V>  current = node;
	 
	    /* loop down to find the leftmost leaf */
	    while (!current.getLeftChild().isNull()){
	        current = current.getLeftChild();
	    }
	    return current;
	}
	
	public  INode<T,V>  maxValue(INode<T,V> node){
	    INode<T,V>  current = node;
	 
	    /* loop down to find the rightmost leaf */
	    while (!current.getRightChild().isNull()){
	        current = current.getRightChild();
	    }
	    return current;
	}

	public INode<T,V> inOrderSuccessor(Node<T,V> root, Node<T,V> n){
	 
	        // step 1 of the above algorithm
	        if (!n.getRightChild().isNull()) {
	            return minValue(n.getRightChild());
	        }
	 
	        // step 2 of the above algorithm
	        Node<T,V> p = (Node<T,V>) n.getParent();
	        while (!p.isNull() && compareTwoNodes(n,p.getRightChild())) {
	            n = p;
	            p = (Node<T,V>) p.getParent();
	        }
	        return p;
   }

	
	private void removeFixup(Node<T,V> doubleBlack){
		if (doubleBlack == root) // Double black at the root is ignored
			return;
		
		Node<T,V> sibling = doubleBlack.getSibling();
		Node<T,V> parent = (Node<T,V>)doubleBlack.getParent();


		if (sibling.isBlack() && sibling.hasRedChild()){
			Node<T,V> redChild = sibling.getRedChild();

			if (!redChild.isAligned()){
				if (redChild.isLeftChild())
					rotateRight(sibling);
				else rotateLeft(sibling);

				
				sibling.setColor(Node.RED);
				redChild.setColor(Node.BLACK);
			}


			// Right Right / Left Left Case:
			sibling = doubleBlack.getSibling();
			redChild = sibling.getRedChild();

			redChild.setColor(Node.BLACK);
			sibling.setColor(parent.getColor());
			parent.setColor(Node.BLACK);
			if (sibling.isRightChild())
				rotateLeft(parent);
			else rotateRight(parent);
			
			return;

		}else if (sibling.isBlack() && !sibling.hasRedChild()){
			sibling.setColor(Node.RED);
			
			if (parent.isRed()) {
				parent.setColor(Node.BLACK);
				return;
			}else {
				removeFixup(parent);
				return;
			}
			
		}else if (sibling.isRed()){
			sibling.setColor(Node.BLACK);
			parent.setColor(Node.RED);
			
			if (sibling.isRightChild())
				rotateLeft(parent);
			else rotateRight(parent);

			removeFixup(doubleBlack);
			return;
		}

	}
	
	public INode<T, V> searchNode(T key) {
		INode<T,V> temp = root;
		if (root.isNull())
			return null;

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
		if (inserted == root && root.getColor() == INode.RED) {
			root.setColor(Node.BLACK);
			return;
		}
		
		Node<T,V> parent = (Node<T,V>)inserted.getParent();
		if (parent.isBlack())
			return;
		
		Node<T,V> grandParent = (Node<T,V>)parent.getParent();
		Node<T,V> uncle  = inserted.getUncle();

		

		
		// Case 1: uncle is Red
		if (uncle.isRed()) {
			parent.setColor(Node.BLACK);
			uncle.setColor(Node.BLACK);
			grandParent.setColor(Node.RED);
			insertFix(grandParent);
			return;
		}

		// Case 2 , set up for case 3
		if (!inserted.isAligned()){
			if (parent.isLeftChild()){
				// new problematic node is the parent after it's been rotated down
				rotateLeft(parent);
				insertFix(parent);
				return;
			}
			else {
				rotateRight(parent);
				insertFix(parent);
				return;
			}
			
		}


		//Case 3 root->>
		if (parent.isLeftChild()) 
			rotateRight(grandParent);
		else 
			rotateLeft(grandParent);

		grandParent.setColor(Node.RED);
		parent.setColor(Node.BLACK);
	}
	
	
	public void rotateRight(Node<T,V> toBeRotated) {
		/*
				  P 
				   |
				 toBeR (might be root)
				 /    \
				lc    rc
			   /  \
			 glc  grc
		*/
		Node <T,V> p = (Node<T, V>) toBeRotated.getParent();
		Node <T,V> lc= (Node<T, V>) toBeRotated.getLeftChild();

		Node <T,V> grc= (Node<T, V>) lc.getRightChild();


		toBeRotated.setLeftChild(grc);
		grc.setParent(toBeRotated);
		lc.setRightChild(toBeRotated);
		lc.setParent(p);
		
		if (toBeRotated == root)  // the root's parent nil doesn't have root as its child
			root = lc;
		else if (toBeRotated.isRightChild())
			p.setRightChild(lc);
		else p.setLeftChild(lc);
		toBeRotated.setParent(lc);
	}
	private void rotateLeft(Node<T,V> toBeRotated) {
		
		/*
			   P
			   |
			 toBeR (might be root)
			 /    \
			lc    rc
			     /  \
			   glc  grc
		*/
		
		Node <T,V> p = (Node<T, V>) toBeRotated.getParent();
		Node <T,V> rc= (Node<T, V>) toBeRotated.getRightChild();
		
		Node <T,V> glc= (Node<T, V>) rc.getLeftChild();
		
		
		toBeRotated.setRightChild(glc);
		glc.setParent(toBeRotated);
		rc.setLeftChild(toBeRotated);
		rc.setParent(p);
		
		if (toBeRotated == root)  // the root's parent nil doesn't have root as its child
			root = rc;
		else if (toBeRotated.isRightChild())
			p.setRightChild(rc);
		else p.setLeftChild(rc);
		toBeRotated.setParent(rc);

	}
	
	//return true if the two nodes have the same key(means that they are the same node)
	private boolean compareTwoNodes(INode<T, V> iNode,INode<T,V> B ) {
		if(B.isNull()||iNode.isNull())
			return false;
		return (iNode.getKey().compareTo(B.getKey())==0) ? true:false;
	}
	
	private void giveNilChildren(INode<T,V> node) {
		Node<T,V> nilnode1 = new Node<>();
		Node<T,V> nilnode2 = new Node<>();
		node.setRightChild(nilnode1);
		node.setLeftChild(nilnode2);
		nilnode1.setParent(node);
		nilnode2.setParent(node);
	}
	
	
	
    public void toArrayList (Node<T,V> node, ArrayList<Map.Entry<T,V>> arr){
        if (node == null || node.isNull())
            return;
        
        
        toArrayList((Node<T,V>)node.getLeftChild(), arr);
        arr.add(node.toEntry());
        toArrayList((Node<T,V>)node.getRightChild(), arr);
    }
    
    

	

}
