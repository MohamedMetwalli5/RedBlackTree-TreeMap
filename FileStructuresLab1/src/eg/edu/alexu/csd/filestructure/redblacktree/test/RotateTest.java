package eg.edu.alexu.csd.filestructure.redblacktree.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import eg.edu.alexu.csd.filestructure.redblacktree.test.TestFunctionallity;
import eg.edu.alexu.csd.filestructure.redblacktree.implementation.RedBlackTree;
class RotateTest {

	@Test
	void test() {
		RedBlackTree<Integer, Intsseger> T=new RedBlackTree();
		
		int[] insert = new int[] {41, 38, 31, 12, 19, 8};
		for (int x : insert) {
			T.insert(x, x);
			TestFunctionallity.print(T.getRoot());

		}
		
		int[] delete = new int[] { 8, 12, 19, 31, 38, 41};
		for (int x : delete) {
			T.delete(x);
			TestFunctionallity.print(T.getRoot());
		}
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
