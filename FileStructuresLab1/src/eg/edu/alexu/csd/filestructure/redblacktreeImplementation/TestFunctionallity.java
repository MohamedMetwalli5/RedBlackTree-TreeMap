package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

public class TestFunctionallity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node <Integer,Integer>nil =new Node<Integer,Integer>();
		Node <Integer,Integer>test2 =new Node<Integer,Integer>(5,5,true,nil,nil);
		Node  <Integer,Integer>temp=test2;
		Node <Integer,Integer>test3=new Node<Integer,Integer>(6,6,true,nil,nil);
		RedBlackTree T=new RedBlackTree();
		
	T.delete(27);
		T.insert(65, 65);
		T.delete(19);
		T.delete(0);
		T.delete(3);
		/*
		T.insert(2, 2);
		T.insert(2, 1);
		T.search(11);*/


		System.out.println(T.search(11));
		/*System.out.print(temp.getKey());
		 * 
		 */
		//System.out.print(test2.getKey());
		/*System.out.print(nil.isNull());
		System.out.println(test2.isNull());*/
	}

}
