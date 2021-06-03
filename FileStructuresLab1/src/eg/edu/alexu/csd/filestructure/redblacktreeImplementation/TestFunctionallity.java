package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

public class TestFunctionallity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node <Integer,Integer>nil =new Node<Integer,Integer>();
		Node <Integer,Integer>test2 =new Node<Integer,Integer>(5,5,true,nil,nil);
		Node  <Integer,Integer>temp=test2;
		Node <Integer,Integer>test3=new Node<Integer,Integer>(6,6,true,nil,nil);
		temp.setRightChild(test3);
		temp=(Node<Integer, Integer>) temp.getRightChild();
		System.out.print(temp.getKey());
		System.out.print(test2.getKey());
		/*System.out.print(nil.isNull());
		System.out.println(test2.isNull());*/
	}

}
