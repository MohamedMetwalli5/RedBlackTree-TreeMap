package redblacktree;


import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

import java.util.*;
import java.util.stream.Collectors;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {


    RedBlackTree<T,V> RBTree;
    TreeMap(){
        RBTree = new RedBlackTree<T,V>();
    }

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (RBTree.size == 0)
            return null;

    	Node<T,V> node = RBTree.searchNode(key);
    	if (node == null)
    		return null;
    	
    	Node<T,V> predecssor = RBTree.getPredecessor(node);
    	if (predecssor == null) // No successor
    		return node.toEntry();
    	else
    		return predecssor.toEntry();
    }

    @Override
    public T ceilingKey(T key) {
        if (RBTree.size == 0)
            return null;

    	Map.Entry<T, V> ceilingEntr = ceilingEntry(key);
    	return ceilingEntr.getKey();
    }

    @Override
    public void clear() {
        RBTree.clear();
    }

    @Override
    public boolean containsKey(T key) {
        return RBTree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        return this.values().contains(value);
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        Set<Map.Entry<T, V>> set = new LinkedHashSet<>();
        entrySet(RBTree.getRoot(), set);
        return set;
    }

    private void entrySet(INode<T, V> node, Set<Map.Entry<T, V>> set) {
        if (node == null || node.isNull()) {
            return;
        }
        // left
        entrySet(node.getLeftChild(), set);
        // me
        set.add( new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()) );
        // right
        entrySet(node.getRightChild(), set);
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if (RBTree.size == 0)
            return null;

    	Node<T,V> minNode = getMinNode();
        return minNode.toEntry();
    }


    @Override
    public T firstKey() {
        if (RBTree.size == 0)
            return null;

        return getMinNode().getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        if (RBTree.size == 0)
            return null;

    	Node<T,V> node = RBTree.searchNode(key);
    	if (node == null)
    		return null;
    	
    	Node<T,V> successor = RBTree.getSuccessor(node);
    	if (successor == null) // No successor
    		return node.toEntry();
    	else
    		return successor.toEntry();
    }

    @Override
    public T floorKey(T key) {
        if (RBTree.size == 0)
            return null;

    	Map.Entry<T, V> floorEntry = floorEntry(key);
    	return floorEntry.getKey();
    }

    @Override
    public V get(T key) {
        return (V) RBTree.search(key);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        return headMap(toKey, false);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {

        return null;

    }
    

    @Override
    public Set<T> keySet() {
        return keySet(RBTree.getRoot());
    }

    private Set<T> keySet(INode node) {
        if(node.isNull() || node == null){
            return new HashSet<>();
        }

        Set<T> set = new TreeSet<>();
        Set<T> leftSet = new HashSet<>(), rightSet = new HashSet<>();

        if(!node.getLeftChild().isNull())
            leftSet = keySet(node.getLeftChild());

        if(!node.getRightChild().isNull())
            rightSet = keySet(node.getRightChild());

        set.addAll(leftSet);
        set.add((T)node.getKey());
        set.addAll(rightSet);

        return set;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        if (RBTree.size == 0)
            return null;

        Node<T,V> maxNode = getMaxNode();
        return maxNode.toEntry();
    }

    @Override
    public T lastKey() {
        if (RBTree.size == 0)
            return null;

    	Map.Entry<T,V> lastEntr = lastEntry();
        return lastEntr.getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        return RBTree.poll(firstKey());
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        return RBTree.poll(lastKey());
    }

    @Override
    public void put(T key, V value) {
        RBTree.insert(key,value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        for (Map.Entry<T,V> entry : map.entrySet()){
            RBTree.insert(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public boolean remove(T key) {
        return RBTree.delete(key);
    }

    @Override
    public int size() {
        return RBTree.size;
    }

    @Override
    public Collection<V> values() {
         return this.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Node<T,V> getRoot(){
        return  (Node<T,V> )RBTree.getRoot();
    }
    
    Node<T,V> getMinNode(){
    	return RBTree.minValue(getRoot());
    }
    
    Node<T,V> getMaxNode(){
    	return RBTree.maxValue(getRoot());
    }
    

    public static void main(String args[]){
        TreeMap<Integer,String> t = new TreeMap<>();
        t.put(-5,"mo");
        t.put(20,"moaz");
        t.put(10,"ahmed");
        t.put(5,"omar");
        RBTreePrinter.print(t.getRoot());
        /*Set<Map.Entry<Integer,String>> s=  t.entrySet();
        t.values().forEach((String k)->{
            System.out.println(k);
        });*/

    }
}