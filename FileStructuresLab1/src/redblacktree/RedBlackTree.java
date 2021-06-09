package redblacktree;

import java.util.ArrayList;
import java.util.Map;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T,V> nil = new Node<T,V>();
	private Node<T,V> root = nil.clone();
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
		this.root = nil;
		size = 0;
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
 		if(root.isNull()) {//inserting in empty tree
			root=new Node<T,V>(key,value,Node.BLACK,null,null);
			root.setParent(nil);
			giveNilChildren(root);
			size++;
			return;
		}
		
 		Node<T,V> temp=root;
 		Node<T,V> child=temp;
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
		insertFix(child);
		
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
		Map.Entry<T, V> ret = poll(key);

		if (ret == null)
			return false;
		
		size--;
		return true;
		
	}
	
	public Map.Entry<T,V> poll(T key) {
		if (key == null)
			return null;
			
		Node<T, V> toBeDeleted = searchNode(key);
		if(toBeDeleted == null)   // if the tree doesn't contain the node to be deleted
			return null;
		
		Map.Entry<T,V> ret = toBeDeleted.toEntry();
		
	
		if (toBeDeleted.internalNode()){
			Node<T,V> successor = minValue((Node<T,V>)toBeDeleted.getRightChild());

			toBeDeleted.setKey(successor.getKey());
			toBeDeleted.setValue(successor.getValue());
			removeHelper(successor);
			
		}else {
			removeHelper(toBeDeleted);
		}
		
		return ret;
		

	}
	

	// Deletes a non internal node (has at most one child)
	private void removeHelper(Node<T,V> toBeDeleted){
		Node<T,V> z = toBeDeleted;

		

		if (z.isRed() || z.hasRedChild()){
			// Case 1
			
			// Get the non nil child
			Node<T,V> child = (Node<T, V>) (z.getLeftChild().isNull() ? 
								z.getRightChild() :  z.getLeftChild());
			
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


	public Node<T,V> getSuccessor(Node<T,V> x){
		if (x.getRightChild().isNull()) 
			return null;
		
		return minValue((Node<T, V>) x.getRightChild());
	}
	
	public Node<T,V> getPredecessor(Node<T,V> x){
		if (x.getLeftChild().isNull()) 
			return null;
		
		return maxValue((Node<T, V>) x.getLeftChild());
	}
	
	private Node treeMinimum(Node node){
		while (!node.getLeftChild().isNull()) {
			node = (Node) node.getLeftChild();
		}
		return node;
	}
	
	
	// The smallest value in the subtree rooted at node
	public  Node<T,V>  minValue(Node<T,V> node){
	    Node<T,V>  current = node;
	 
	    /* loop down to find the leftmost leaf */
	    while (!current.getLeftChild().isNull()){
	        current = (Node<T,V>)current.getLeftChild();
	    }
	    return current;
	}
	
	public  Node<T,V>  maxValue(Node<T,V> node){
	    Node<T,V>  current = node;
	 
	    /* loop down to find the rightmost leaf */
	    while (!current.getRightChild().isNull()){
	        current = (Node<T,V>)current.getRightChild();
	    }
	    return current;
	}

	public Node<T,V> inOrderSuccessor(Node<T,V> root, Node<T,V> n){
	 
	        // step 1 of the above algorithm
	        if (!n.getRightChild().isNull()) {
	            return minValue((Node<T,V>) n.getRightChild());
	        }
	 
	        // step 2 of the above algorithm
	        Node<T,V> p = (Node) n.getParent();
	        while (!p.isNull() && compareTwoNodes(n,p.getRightChild())) {
	            n = p;
	            p = (Node) p.getParent();
	        }
	        return p;
   }

	
	private void removeFixup(Node<T,V> toBeDeleted){
		if (toBeDeleted == root) // Double black at the root is ignored
			return;
		
		Node<T,V> sibling = toBeDeleted.getSibling();
		Node<T,V> parent = (Node<T,V>)toBeDeleted.getParent();


		if (sibling.isBlack() && sibling.hasRedChild()){
			Node<T,V> redChild = sibling.getRedChild();

			// Right Left / Left Right Case:
			if (!redChild.isAligned()){
				if (redChild.isLeftChild())
					rotateRight(sibling);
				else rotateLeft(sibling);

				
				sibling.setColor(Node.RED);
				redChild.setColor(Node.BLACK);
				removeFixup(toBeDeleted);
				return;
			}

			// Right Right / Left Left Case:
			redChild.setColor(Node.BLACK);
			
			if (parent.isRed()) {
				parent.setColor(Node.BLACK);
				sibling.setColor(Node.RED);
			}
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

			removeFixup(toBeDeleted);
			return;
		}

		/*
		while (!compareTwoNodes(toBeDeleted, root) && toBeDeleted.getColor() == Node.BLACK){
			
			if (compareTwoNodes(toBeDeleted, toBeDeleted.getParent().getLeftChild())){//check if the deleted node is a left child
				//sibling = (Node) toBeDeleted.getParent().getRightChild(); //getting sibling
				//case 1 sibling is red
				if (sibling.getColor() == Node.RED){
					sibling.setColor(Node.BLACK);
					parent.setColor(Node.RED);
					rotateLeft( parent);
					sibling = (Node)parent.getRightChild();
				}
				//case 2 ,both sibling's children are black
				if(sibling!=null) {
				if (sibling.getLeftChild().getColor() == Node.BLACK &&
							sibling.getRightChild().getColor() == Node.BLACK){
					sibling.setColor(Node.RED);
					toBeDeleted = (Node) parent;
				}else{
					//sibling's right child is black
					if (sibling.getRightChild().getColor() == Node.BLACK){
						sibling.getLeftChild().setColor(Node.BLACK);
						sibling.setColor(Node.RED);
						rotateRight(sibling);
						sibling = (Node) parent.getRightChild();
					}
					if(!sibling.isNull()) {
					//sibling is black and it's right child is red
					sibling.setColor(parent.getColor());
					sibling.getRightChild().setColor(Node.BLACK);
					rotateLeft((Node<T, V>) parent);
					}
					parent.setColor(Node.BLACK);
					
					toBeDeleted = root;
					}
					}
				}
			else{
				//if the deleted is a right child
				//sibling = (Node) toBeDeleted.getParent().getLeftChild();
				
				//case 1 sibling is red
				if (sibling.getColor() == Node.RED){
					sibling.setColor(Node.BLACK);
					parent.setColor(Node.RED);
					rotateRight((Node) parent);
					sibling = (Node) parent.getLeftChild();
				}
				if(sibling!=null) {
				//case 2 both sibling's children are black
				if (sibling.getRightChild().getColor() == Node.BLACK &&
							sibling.getLeftChild().getColor() == Node.BLACK){
					sibling.setColor(Node.RED);
					toBeDeleted = (Node) parent;
				}else{
					
					//case 3 sibling's left child is black
					 if (sibling.getLeftChild().getColor() == Node.BLACK){
						sibling.getRightChild().setColor(Node.BLACK);
						sibling.setColor(Node.RED);
						rotateLeft(sibling);
						sibling = (Node) parent.getLeftChild();
					}
					 if(!sibling.isNull()) {
					 //case 4 sibling is black and it's left is red
					sibling.setColor(parent.getColor());
					sibling.getLeftChild().setColor(Node.BLACK);
					rotateRight((Node<T, V>) parent);
					 }
					 parent.setColor(Node.BLACK);
				
					toBeDeleted = root;
				}
				}
				}
			
		}
		toBeDeleted.setColor(Node.BLACK);
		*/
	}
	
	public Node<T, V> searchNode(T key) {
		Node<T,V> temp = root;
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
		if (inserted == root && root.isRed()) {
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

		/*
		while(inserted.isRed()) {
			//the parent of the inserted is a left child
			if(((Node<T,V>)inserted.getParent()).isLeftChild()) {
				
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
	*/
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
		
		System.out.println("Right rotation: " + toBeRotated.getKey());
		TestFunctionallity.print(root);

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

		System.out.println("Left rotation: " + toBeRotated.getKey());
		TestFunctionallity.print(root);

	}
	
	//return true if the two nodes have the same key(means that they are the same node)
	private boolean compareTwoNodes(INode<T, V> iNode,INode<T,V> B ) {
		if(B.isNull()||iNode.isNull())
			return false;
		return (iNode.getKey().compareTo(B.getKey())==0) ? true:false;
	}
	
	private void giveNilChildren(Node<T,V> node) {
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
