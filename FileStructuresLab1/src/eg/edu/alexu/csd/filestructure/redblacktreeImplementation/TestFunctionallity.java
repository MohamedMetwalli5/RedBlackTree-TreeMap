package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

public class TestFunctionallity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node <Integer,Integer>nil =new Node<Integer,Integer>();
		Node <Integer,Integer>test2 =new Node<Integer,Integer>(5,5,true,nil,nil);
		Node  <Integer,Integer>temp=test2;
		Node <Integer,Integer>test3=new Node<Integer,Integer>(6,6,true,nil,nil);
		RedBlackTree T=new RedBlackTree();
		T.insert(5, "initroot");
		T.insert(6, "init right");
		T.insert(3, "init left");
		T.insert(4, "inseted case 1 left");
		T.insert(8, "second right");
		T.insert(7, "second left right");


		System.out.println(T);
		/*System.out.print(temp.getKey());
		 * 
		 */
		//System.out.print(test2.getKey());
		/*System.out.print(nil.isNull());
		System.out.println(test2.isNull());*/
	}

}
