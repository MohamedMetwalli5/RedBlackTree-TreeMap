package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private Node<T,V> nil = new Node<T,V>();
	private Node<T,V> root = nil.clone();
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
 		if(root.isNull()) {//inserting in empty tree
		 	Node<T,V> nilLeaf =nil.clone();
			nilLeaf.setParent(root);
			root=new Node<T,V>(key,value,Node.BLACK,nilLeaf,nilLeaf.clone());
			root.setParent(nil);
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


		// make child red
		// give him two nils
		child.setColor(Node.RED);
		child.setKey(key);
		child.setValue(value);

		Node<T, V> nilLeaf = nil.clone();
		nilLeaf.setParent(child);
		child.setLeftChild(nilLeaf);
		child.setRightChild(nilLeaf.clone());

		//call the fix up
		insertFix(child);
 		
	}

	//<------------------------------------------------------------------------------------------------------------------------------------>	
	/*

	Delete : 2 children (root and internal)
	Delete : 1 Child
	Delete : 0 Children (children are nil)
	*/
	@Override
	public boolean delete(T key) {
		Node<T, V> u = searchNode(key);
		if(u == null)   // if the tree doesn't contain the node to be deleted
			return false;
		
		if (u.internalNode()){
			// get successor
			// copy key and value
			// removeHelper(successor)
		}else {
			removeHelper(u);
		}

		return true;
		
		/*

		if(compareTwoNodes(searchNode(key),root)) {
			Node temp=inOrderSuccessor(root, root);
			root.setKey((T) temp.getKey());
			root.setValue((V) temp.getValue());
			removeHelper(temp);
			return true;
		}
		
		else {
			removeHelper(searchNode(key));
			return true;
		}
		*/
	}
	
	// Deletes a non internal node (has at most one child)
	private void removeHelper(Node<T,V> toBeDeleted){
		Node<T,V> z = toBeDeleted;
		Node<T,V> child = z.getLeftChild().isNull() ? (Node<T,V>) z.getRightChild() : (Node<T,V>) z.getLeftChild();


		if (z.isRed() || child.isRed()){
			// Case 1
		}else {
			// Double black
			// Delete z and replace it with child
			// child is now "Double black"
			// fix double black(child)
		}



		Node<T,V> x = new Node<>(nil,nil); //nil
		Node<T,V> y = new Node<>(nil,nil); //nil
		Node<T,V> sibling=new Node<>(nil,nil);
		
		
		if (compareTwoNodes(z,z.getParent().getLeftChild()))//check if the deleted node is a left child
			sibling = (Node<T,V>) z.getParent().getRightChild(); //getting sibling
		else
			sibling = (Node<T,V>) z.getParent().getLeftChild();
		
			Node<T,V> parent=(Node<T,V>) z.getParent();
		if (z.getLeftChild().isNull() || z.getRightChild().isNull())
			y = z;
		else y = treeSuccessor(z);
		if (!y.getLeftChild().isNull()) {
			x = (Node<T,V>) y.getLeftChild();
		}else {
			x = (Node<T,V>)y.getRightChild();
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
			removeFixup(x,sibling,parent);
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
	public static Node minValue(Node node){
	    Node current = node;
	 
	    /* loop down to find the leftmost leaf */
	    while (!current.getLeftChild().isNull()){
	        current = (Node) current.getLeftChild();
	    }
	    return current;
	}

	public Node inOrderSuccessor(Node root, Node n){
	 
	        // step 1 of the above algorithm
	        if (!n.getRightChild().isNull()) {
	            return minValue((Node) n.getRightChild());
	        }
	 
	        // step 2 of the above algorithm
	        Node p = (Node) n.getParent();
	        while (!p.isNull() && compareTwoNodes(n,p.getRightChild())) {
	            n = p;
	            p = (Node) p.getParent();
	        }
	        return p;
   }

	
	private void removeFixup(Node toBeDeleted,Node sibling,Node parent){
		//Node sibling=new Node();
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
		Node<T,V> uncle=inserted.getUncle();
		Node<T,V> parent = (Node<T,V>)inserted.getParent();

		if (parent.isBlack())
			return;

		
		// Case 1: uncle is Red

		// Case 2 , set up for case 3
		if (!inserted.isAligned()){
			if (parent.isLeftChild()){
				inserted=parent;
				rotateLeft(parent);
			}
			else {

				rotateRight(parent);
			}
			
		}


		//Case 3
		

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
	}
	private void rotateRight(Node<T,V> toBeRotated) {
		/*
				 P
				  \
				 toBeR (root)
				 /    \
				lc    rc
			   /  \
			 glc  grc
		*/
		Node <T,V> p = (Node<T, V>) toBeRotated.getParent();
		Node <T,V> lc= (Node<T, V>) toBeRotated.getLeftChild();

		Node <T,V> grc= (Node<T, V>) lc.getRightChild();


		toBeRotated.setLeftChild(grc);
		lc.setRightChild(toBeRotated);

		if (toBeRotated.isRightChild())
			p.setRightChild(lc);
		else p.setLeftChild(lc);

		if (toBeRotated == root)
			root = lc;

		/*
		(!(temp.getRightChild()==null))
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
		*/
	}
	private void rotateLeft(Node<T,V> toBeRotated) {
		
		Node <T,V> temp=(Node<T, V>) toBeRotated.getRightChild();
		toBeRotated.setRightChild(temp.getLeftChild());
		
		
		if(!(temp.getLeftChild()==null))
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
