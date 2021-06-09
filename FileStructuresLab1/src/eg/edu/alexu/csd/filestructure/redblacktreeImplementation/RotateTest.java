package eg.edu.alexu.csd.filestructure.redblacktreeImplementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import eg.edu.alexu.csd.filestructure.redblacktreeImplementation.TestFunctionallity;
class RotateTest {

	@Test
	void test() {
		RedBlackTree<Integer, Integer> T=new RedBlackTree();
		T.insert(1, 1);
		T.insert(2, 2);
		T.insert(6, 6);
		TestFunctionallity.print(T.getRoot());
		
		T.insert(3, 3);
		TestFunctionallity.print(T.getRoot());

		T.insert(4, 4);
		TestFunctionallity.print(T.getRoot());

		/*
				 P
				  \
				 toBeR 
				 /    \
				lc    rc
			   /  \
			 glc  grc
		*/
		
		
	}

}
