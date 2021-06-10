package eg.edu.alexu.csd.filestructure.redblacktree.implementation;

import java.util.*;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

    RedBlackTree<T, V> RBTree;

    TreeMap() {
        RBTree = new RedBlackTree<T, V>();
    }


    private Node<T,V> ceilingNode(T key)
    {
        if (key == null)
            throw new RuntimeErrorException(null);

        INode<T, V> root = RBTree.getRoot(), parent = null;
        while (root != null && !root.isNull()) {
            int comparison = key.compareTo(root.getKey());
            if (comparison == 0)
                return (Node<T, V>)root;
            else if (comparison < 0) {
                parent = root;
                root = root.getLeftChild();
            } else
                root = root.getRightChild();
        }
        if (parent == null || parent.isNull())
            return null;
        return (Node<T,V>)parent;
    }

    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        Node<T,V> node = ceilingNode(key);
        if(node == null)
            return null;
        return node.toEntry();
    }

    @Override
    public T ceilingKey(T key) {
        return ceilingEntry(key).getKey();
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
        if (value == null)
            throw new RuntimeErrorException(null);
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
        set.add(new AbstractMap.SimpleEntry<>(node.getKey(), node.getValue()));
        // right
        entrySet(node.getRightChild(), set);
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        if (RBTree.size == 0)
            return null;

        Node<T, V> minNode = (Node<T, V>) getMinNode();
        return minNode.toEntry();
    }

    @Override
    public T firstKey() {
        if (RBTree.size == 0)
            return null;

        return getMinNode().getKey();
    }

    private Node<T,V> floorNode(T key)
    {
        if (key == null)
            throw new RuntimeErrorException(null);

        INode<T, V> root = RBTree.getRoot(), parent = null;
        while (root != null && !root.isNull()) {
            int comparison = key.compareTo(root.getKey());
            if (comparison == 0)
                return (Node<T, V>)root;
            else if (comparison > 0) {
                parent = root;
                root = root.getRightChild();
            } else
                root = root.getLeftChild();
        }
        if (parent == null || parent.isNull())
            return null;
        return (Node<T,V>)parent;
    }


    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        Node<T,V> node = (Node<T,V>) floorNode(key);
        if(node == null)
            return null;
        return node.toEntry();
    }

    @Override
    public T floorKey(T key) {
        Node<T,V> node = (Node<T,V>) floorNode(key);
        if(node == null)
            return null;
        return node.getKey();
    }

    @Override
    public V get(T key) {
        return (V) RBTree.search(key);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        return headMap(toKey, false);
    }

    public void headMapHelper(ArrayList<Map.Entry<T, V>> arr, Node<T,V> node)
    {
        if(node == null || node.isNull())
            return;
        System.out.println("Root: " + node.getKey() + ", left: " + node.getLeftChild() + ", right: " + node.getRightChild());
        
        headMapHelper(arr, (Node<T,V>)node.getLeftChild());
        arr.add(node.toEntry());
        headMapHelper(arr, (Node<T,V>)node.getRightChild());
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        ArrayList<Map.Entry<T, V>> ans = new ArrayList<>();
        Node<T,V> node = floorNode(toKey);

        if(node == null)
            return null;

        System.out.println("floor key: " + node.getKey() + ", toKey: " + toKey);
        int comparison = node.getKey().compareTo(toKey);
        if(comparison == 0)
            headMapHelper(ans, (Node<T,V>) node.getLeftChild());
        else
            headMapHelper(ans, node);

        if(inclusive && comparison == 0)
            ans.add(node.toEntry());
        
        // System.out.println(toKey);
        // System.out.println(ans);
        if(ans.size() == 0)
            return null;
        
        return ans;

    }

    @Override
    public Set<T> keySet() {
        return keySet(RBTree.getRoot());
    }

    private Set<T> keySet(INode node) {
        if (node.isNull() || node == null) {
            return new HashSet<>();
        }

        Set<T> set = new TreeSet<>();
        Set<T> leftSet = new HashSet<>(), rightSet = new HashSet<>();

        if (!node.getLeftChild().isNull())
            leftSet = keySet(node.getLeftChild());

        if (!node.getRightChild().isNull())
            rightSet = keySet(node.getRightChild());

        set.addAll(leftSet);
        set.add((T) node.getKey());
        set.addAll(rightSet);

        return set;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        if (RBTree.size == 0)
            return null;

        Node<T, V> maxNode = (Node<T, V>) getMaxNode();
        return maxNode.toEntry();
    }

    @Override
    public T lastKey() {
        if (RBTree.size == 0)
            return null;

        Map.Entry<T, V> lastEntr = lastEntry();
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
        RBTree.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if (map == null)
            throw new RuntimeErrorException(null);
        for (Map.Entry<T, V> entry : map.entrySet()) {
            RBTree.insert(entry.getKey(), entry.getValue());
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

    public Node<T, V> getRoot() {
        return (Node<T, V>) RBTree.getRoot();
    }

    INode<T, V> getMinNode() {
        return RBTree.minValue(getRoot());
    }

    INode<T, V> getMaxNode() {
        return RBTree.maxValue(getRoot());
    }

    public static void main(String args[]) {
        TreeMap<Integer, String> t = new TreeMap<>();
        t.put(-5, "mo");
        t.put(20, "moaz");
        t.put(10, "ahmed");
        t.put(5, "omar");
        //RBTreePrinter.print(t.getRoot());
        /*
         * Set<Map.Entry<Integer,String>> s= t.entrySet(); t.values().forEach((String
         * k)->{ System.out.println(k); });
         */
        ArrayList<Map.Entry<Integer,String>> arr = new ArrayList<Map.Entry<Integer,String>>();
        t.headMapHelper(arr, t.getRoot());
        System.out.println(arr);
    }
}