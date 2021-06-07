package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;

public class TestFunctionallity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node <Integer,Integer>nil =new Node<Integer,Integer>();
		Node <Integer,Integer>test2 =new Node<Integer,Integer>(5,5,true,nil,nil);
		Node  <Integer,Integer>temp=test2;
		Node <Integer,Integer>test3=new Node<Integer,Integer>(6,6,true,nil,nil);
		RedBlackTree<Integer, Integer> T=new RedBlackTree();
		
	    T.delete(27);
		T.insert(65, 65);
		T.insert(42, 0);
		T.delete(42);
		T.insert(17, 90);
		T.insert(31, 76);
		T.insert(48, 71);
		T.insert(5, 50);
		T.insert(7, 68);
		T.insert(73, 74);	
		T.insert(85, 18);
		T.insert(74, 95);
		T.insert(84, 82);
		T.insert(59, 29);
		T.insert(71, 71);
		 T.delete(42);
		 T.insert(51, 40);
		 T.insert(33, 76);
		 T.insert(89, 95);
		 T.insert(30, 31);
		 T.insert(37, 99);
		 T.delete(51);
		 T.insert(95, 35);
		 T.delete(65);
		 T.delete(81);
		 T.insert(61, 46);
		 T.insert(50, 33);
		 print(T.getRoot());
		 //T.delete(5);

		 
		
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
	 public static void print(INode  root)
	    {
	        List<List<String>> lines = new ArrayList<List<String>>();
	        List<INode > level = new ArrayList<INode >();
	        List<INode > next = new ArrayList<INode >();
	 
	        level.add(root);
	        int nn = 1;
	 
	        int widest = 0;
	 
	        while (nn != 0) {
	            List<String> line = new ArrayList<String>();
	 
	            nn = 0;
	 
	            for (INode  n : level) {
	                if (n == null || n.isNull())  {
	                    line.add(null);
	 
	                    next.add(null);
	                    next.add(null);
	                } else {
	                    String aa = (n.getValue().toString())+(n.getColor()?"(R)":"(B)");
	                    line.add(aa);
	                    if (aa.length() > widest) widest = aa.length();
	 
	                    next.add(n.getLeftChild());
	                    next.add(n.getRightChild());
	 
	                    if (n.getLeftChild() != null) nn++;
	                    if (n.getRightChild() != null) nn++;
	                }
	            }
	 
	            if (widest % 2 == 1) widest++;
	 
	            lines.add(line);
	 
	            List<INode > tmp = level;
	            level = next;
	            next = tmp;
	            next.clear();
	        }
	 
	        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
	        for (int i = 0; i < lines.size(); i++) {
	            List<String> line = lines.get(i);
	            int hpw = (int) Math.floor(perpiece / 2f) - 1;
	 
	            if (i > 0) {
	                for (int j = 0; j < line.size(); j++) {
	 
	                    // split node
	                    char c = ' ';
	                    if (j % 2 == 1) {
	                        if (line.get(j - 1) != null) {
	                            c = (line.get(j) != null) ? '┴' : '┘';
	                        } else {
	                            if (j < line.size() && line.get(j) != null) c = '└';
	                        }
	                    }
	                    System.out.print(c);
	 
	                    // lines and spaces
	                    if (line.get(j) == null) {
	                        for (int k = 0; k < perpiece - 1; k++) {
	                            System.out.print(" ");
	                        }
	                    } else {
	 
	                        for (int k = 0; k < hpw; k++) {
	                            System.out.print(j % 2 == 0 ? " " : "─");
	                        }
	                        System.out.print(j % 2 == 0 ? "┌" : "┐");
	                        for (int k = 0; k < hpw; k++) {
	                            System.out.print(j % 2 == 0 ? "─" : " ");
	                        }
	                    }
	                }
	                System.out.println();
	            }
	 
	            // print line of numbers
	            for (int j = 0; j < line.size(); j++) {
	 
	                String f = line.get(j);
	                if (f == null) f = "";
	                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
	                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);
	 
	                // a number
	                for (int k = 0; k < gap1; k++) {
	                    System.out.print(" ");
	                }
	                System.out.print(f);
	                for (int k = 0; k < gap2; k++) {
	                    System.out.print(" ");
	                }
	            }
	            System.out.println();
	 
	            perpiece /= 2;
	        }
	    }

}
