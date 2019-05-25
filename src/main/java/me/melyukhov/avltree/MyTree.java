package me.melyukhov.avltree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyTree<Key extends Comparable<Key>, Value> implements Map<Key, Value>{

    public class Node {
        private int h;
        private int balance;
        public Key key;
        public Value value;
        private Node left, right, father;
        public Node (Key key, Value value, Node father) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
            this.father = father;
            this.h = 1;
            this.balance = 0;
        }
        public Node next(){
            return getHigherNode(this.key);
        }
        public Node previus(){
            return getLowerNode(this.key);
        }
    }

    private Node root;

    private Node getHigherNode(Key key) {
        Node p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                if (p.left != null)
                    p = p.left;
                else
                    return p;
            } else {
                if (p.right != null) {
                    p = p.right;
                } else {
                    Node father = p.father;
                    Node ch = p;
                    while (father != null && ch == father.right) {
                        ch = father;
                        father = father.father;
                    }
                    return father;
                }
            }
        }
        return null;
    }

    private Node getLowerNode(Key key) {
        Node p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp > 0) {
                if (p.right != null)
                    p = p.right;
                else
                    return p;
            } else {
                if (p.left != null) {
                    p = p.left;
                } else {
                    Node father = p.father;
                    Node ch = p;
                    while (father != null && ch == father.left) {
                        ch = father;
                        father = father.father;
                    }
                    return father;
                }
            }
        }
        return null;
    }

    @Override
    public Node getFirstNode(){
        return min(root);
    }

    @Override
    public Node getLastNode(){
        return max(root);
    }

    private int height(Node x, Node y){
        if(x == null && y == null) return 0;
        else if(x == null) return y.h;
        else if(y == null) return x.h;
        else return Math.max(x.h, y.h);
    }

    private int balance(Node x, Node y){
        if(x == null && y == null) return 0;
        else if(x == null) return - y.h;
        else if(y == null) return x.h;
        else return x.h - y.h;
    }

    private Node put(Node node, Key key, Value value, Node father){
        if (node == null){
            Node newnode = new Node(key,value, father);
            return newnode;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult > 0){node.right = put(node.right, key, value, node); node.h = height(node.left, node.right) + 1;}
        else if(compareResult < 0){node.left = put(node.left, key, value, node); node.h = height(node.left, node.right) + 1;}
        else{
            node.value = value;
        }
        node.balance = balance(node.left, node.right);
        if(node.balance == -2){
            node = leftRotation(node);
        }else if(node.balance == 2){
            node = rightRotation(node);
        }
        return node;
    }

    private Node leftRotation(Node node) {
        if(node.right.right == null && node.right.left != null){
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }else if(node.right.left == null || node.right.left.h <= node.right.right.h){
            Node newnode = node.right;
            newnode.father = node.father;
            node.right = newnode.left;
            if(node.right != null)
                node.right.father = node;
            node.h = height(node.left, node.right)+1;
            node.father = newnode;
            node.balance = balance(node.left, node.right);
            newnode.left = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        }else{
            node.right = rightRotation(node.right);
            node = leftRotation(node);
        }
        return node;
    }

    private Node rightRotation(Node node){
        if(node.left.right != null && node.left.left == null){
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        } else if (node.left.right == null || node.left.right.h <= node.left.left.h){
            Node newnode = node.left;
            newnode.father = node.father;
            node.left = newnode.right;
            if(node.left != null)
                node.left.father = node;
            node.h = height(node.left, node.right)+1;
            node.father = newnode;
            node.balance = balance(node.left, node.right);
            newnode.right = node;
            node = newnode;
            node.balance = balance(node.left, node.right);
            node.h = height(node.left, node.right)+1;
        } else {
            node.left = leftRotation(node.left);
            node = rightRotation(node);
        }
        return node;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value, null);
    }

    private Node remove(Node node, Key key){
        if(node == null) return null;
        int compareResult = key.compareTo(node.key);
        if(compareResult > 0){
            node.right = remove(node.right, key);
        }else if(compareResult < 0){
            node.left = remove(node.left, key);
        }else{
            if(node.right == null && node.left == null){
                node = null;
            }else if(node.right == null){
                node.left.father = node.father;
                node = node.left;
            }else if(node.left == null){
                node.right.father = node.father;
                node = node.right;
            }else{
                if(node.right.left == null){
                    node.right.left = node.left;
                    node.right.father = node.father;
                    node.left.father = node.right;
                    node = node.right;
                }else{
                    Node res = min(node.right);
                    node.key = res.key;
                    node.value = res.value;
                    remove(node.right, node.key);
                }
            }
        }
        if(node != null) {
            node.h = height(node.left, node.right) + 1;
            node.balance = balance(node.left, node.right);
            if (node.balance == -2) {
                node = leftRotation(node);
            } else if (node.balance == 2) {
                node = rightRotation(node);
            }
        }
        return node;
    }

    @Override
    public void remove(Key key) {
        root = remove(root, key);
    }

    @Override
    public Key minKey(){
        return min(root).key;
    }

    @Override
    public Key maxKey(){
        return max(root).key;
    }

    private Node min(Node node){
        if(node.left == null) return node;
        return min(node.left);
    }

    private Node max(Node node){
        if(node.right == null) return node;
        return min(node.right);
    }

    private Value get(Node node, Key key){
        if(node == null) return null;
        int compareResult = key.compareTo(node.key);
        if(compareResult == 0){
            return node.value;
        }else if(compareResult > 0){
            return get(node.right, key);
        }else{
            return get(node.left, key);
        }
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }

    private void print(Node node, int level) {
        if (node != null) {
            print(node.right,level+1);
            for (int i=0;i<level;i++) {
                System.out.print("\t");
            }
            System.out.println(node.key + "->" + node.value+" h="+node.h+" balance="+node.balance);
            print(node.left,level+1);
        }
    }

    @Override
    public void print() {
        print(root,0);
    }

    @Override
    public Comparable<Key> findValue(Value value) {
        List<MyTree.Node> list = nodeList();
        for(MyTree.Node node: list){
            if(node.value == value){
                return node.key;
            }
        }
        return null;
    }

    @Override
    public List<MyTree.Node> nodeList() {
        List<MyTree.Node> nodes = new ArrayList<>();
        order(root, nodes);
        return nodes;
    }

    private void order(Node node, List<MyTree.Node> list){
        if (node == null) return;
        order(node.left, list);
        list.add(node);
        order(node.right, list);
    }

    private void inOrder(Node node, List<Node> list){
        if (node == null) return;
        inOrder(node.left, list);
        list.add(node);
        inOrder(node.right, list);
    }

    @Override
    public List<Key> keySet(){
        List<Node> list = new ArrayList<>();
        inOrder(root, list);
        List<Key> keys = list.stream().map(i -> i.key).collect(Collectors.toList());
        return keys;
    }
}