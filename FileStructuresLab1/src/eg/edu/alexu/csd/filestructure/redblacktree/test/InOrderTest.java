package eg.edu.alexu.csd.filestructure.redblacktree.test;

import eg.edu.alexu.csd.filestructure.redblacktree.implementation.RedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.implementation.TreeMap;

public class InOrderTest {
    public static void main(String[] args) {
        RedBlackTree<Integer,Integer> tree = new RedBlackTree<>();
        
        System.out.println("Initial height = " + tree.getHeight());
        int old_height = tree.getHeight();
        for (int i = 1;i < 100;i++){
            tree.insert(i, i);
            if (tree.getHeight() != old_height){
                System.out.println("after " + i + ", h = " + tree.getHeight());
                old_height = tree.getHeight();
            }
            
        }
    }
    
}
