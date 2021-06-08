package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;

public class TestFunctionallity {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Node <Integer,Integer>nil =new Node<Integer,Integer>();
		/*Node <Integer,Integer>test2 =new Node<Integer,Integer>(5,5,true,nil,nil);
		Node  <Integer,Integer>temp=test2;
		Node <Integer,Integer>test3=new Node<Integer,Integer>(6,6,true,nil,nil);*/
		RedBlackTree<Integer, Integer> T=new RedBlackTree();
		leetInput(T);

		print( T.getRoot());
	/*
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
		 T.delete(81);
		 T.insert(61, 46);
		 T.insert(50, 33);
		 print(T.getRoot());
		 T.delete(5);
		 T.delete(65);
		 print(T.getRoot());
		 */
		
		/*
		T.insert(2, 2);
		T.insert(2, 1);
		T.search(11);*/



		/*System.out.print(temp.getKey());
		 * 
		 */
		//System.out.print(test2.getKey());
		/*System.out.print(nil.isNull());
		System.out.println(test2.isNull());*/
	}
	public static void leetInput(RedBlackTree T) {
		String command[]={"get","put","put","put","remove","put","put","put","get","put","put","put","put","get","put","get","put","put","put","put","remove","put","put","put","put","put","put","put","get","put","put","put","put","put","put","put","put","put","put","put","put","put","remove","put","remove","put","remove","put","remove","put","put","put","remove","put","put","put","put","get","put","put","put","get","remove","put","put","put","put","remove","put","put","put","get","put","put","get","get","put","put","put","put","put","put","put","put","get","put","put","put","get","get","remove","remove","put","get","get","put","get","put","put","get"};
		int nums [][]= {{79},{72,7},{77,1},{10,21},{26},{94,5},{53,35},{34,9},{94},{96,8},{73,79},{7,60},{84,79},{94},{18,13},{18},{69,34},{21,82},{57,64},{23,60},{0},{12,97},{56,90},{44,57},{30,12},{17,10},{42,13},{62,6},{34},{70,16},{51,39},{22,98},{82,42},{84,7},{29,32},{96,54},{57,36},{85,82},{49,33},{22,14},{63,8},{56,8},{94},{78,77},{51},{20,89},{51},{9,38},{20},{29,64},{92,69},{72,25},{73},{6,90},{1,67},{70,83},{58,49},{79},{73,2},{56,16},{58,26},{53},{7},{27,17},{55,40},{55,13},{89,32},{49},{75,75},{64,52},{94,74},{81},{39,82},{47,36},{57},{66},{3,7},{54,34},{56,46},{58,64},{22,81},{3,1},{21,96},{6,19},{77},{60,66},{48,85},{77,16},{78},{23},{72},{27},{20,80},{30},{94},{74,85},{49},{79,59},{15,15},{26}};
				int i;
		
		for( i=0;i<command.length;i++) {
			if(command[i].equals("remove")) {
				/*System.out.println("number:" +nums[i][0]+" i "+i);
				T.delete(nums[i][0]);*/
			}
			else if(command[i].equals("get")) {
				System.out.println("getting:"+T.search(nums[i][0])+"shit");
			}
			else {
				T.insert(nums[i][0],nums[i][1]);
			}
		}
		
		System.out.println(i+",,"+command.length);
		
		
		
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
