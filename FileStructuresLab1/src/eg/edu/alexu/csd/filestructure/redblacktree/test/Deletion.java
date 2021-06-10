package eg.edu.alexu.csd.filestructure.redblacktree.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Assert;


import eg.edu.alexu.csd.filestructure.redblacktree.implementation.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.implementation.RedBlackTree;

public class Deletion{
	public static void main(String[] args){
		RedBlackTree<Integer, String> redBlackTree = new RedBlackTree();
					
		try {
			Random r = new Random();
			HashSet<Integer> list = new HashSet<>();
			ArrayList<Integer> entered = new ArrayList<>(List.of(4467, 6476, 5257, 2030, 1502, 8244, 8107, 1087, 6147, 2790));
			ArrayList<Integer> deleted = new ArrayList<>(List.of(8244,
					2790,
					5257,
					1502,
					1087

));

			for (Integer key : entered) 
				redBlackTree.insert(key, "soso" + key);
			
			
			for (Integer elem : deleted) {
				System.out.println(elem);
				TestFunctionallity.print(redBlackTree.getRoot());
				Assert.assertTrue(redBlackTree.delete(elem));
				
			}
			
			INode<Integer, String> node = redBlackTree.getRoot();
			if ((node == null || node.isNull()))
				Assert.fail();

			System.out.println(node.getRightChild().getColor() == INode.BLACK);
			//Assert.assertTrue(verifyProps(node));
		} catch (Throwable e) {
			TestRunner.fail("Fail to handle deletion", e);
		}
	}
		
}