package DataStructure.exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Home5Ex1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();

        Trie trie = new Trie();
        ArrayList<String> dict = new ArrayList<>(n);
        for (int i=0; i<n; i++) {
            dict.add(in.next());
            trie.add(dict.get(i));
        }
        String line = in.next();
        Node2 node = trie.getRoot();
        for (int i=0; i<k; i++) {
            System.out.println(line.length());
            node = trie.walk(node, line.charAt(i));
            if (node.isTerminal) {
                System.out.println(i + " "+line.charAt(i));
            }
        }
    }
//
//    public static List<String> splitText(Set<String> dictionary, String text) {
//        List<String> result = new ArrayList<>();
//        int i = 0;
//
//        while (i < text.length()) {
//            int maxMatchLength = 0;
//            String maxMatchWord = null;
//
//            for (String word : dictionary) {
//                if (text.startsWith(word, i) && word.length() > maxMatchLength) {
//                    maxMatchLength = word.length();
//                    maxMatchWord = word;
//                }
//            }
//
//            if (maxMatchWord != null) {
//                result.add(maxMatchWord);
//                i += maxMatchLength;
//            } else {
//                i++;
//            }
//        }
//
//        return result;
//    }
}

class Node2 {
    int id;
    boolean isTerminal = false;
    ArrayList<Node2> next;
    Node2 parent;
    char prevChar;
    Node2 suffLink;
    ArrayList<Node2> walk;

    public Node2(int id, Node2 parent, char prevChar) {
        this.id = id;
        this.parent = parent;
        this.prevChar = prevChar;
        this.next = new ArrayList<>(26);
        this.walk = new ArrayList<>(26);
        for (int i=0; i<26; i++) {
            next.add(null);
            walk.add(null);
        }
    }
}

class Trie {
    private ArrayList<Node2> nodes;
    private Node2 root;
    private int size;
    public Trie() {
        this.nodes = new ArrayList<>(List.of(new Node2(0, null, (char)0)));
        this.root = this.nodes.get(0);
        size = 1;
    }
    public Node2 getRoot() {
        return this.root;
    }
    public Node2 last() {
        return this.nodes.get(size-1);
    }
    private int charToInt(char c) {
        return c - 'a';
    }
    public void add(String s) {
        Node2 initNode = this.root;
        for (int i=0; i<s.length(); i++) {
            int charInt = charToInt(s.charAt(i));
            if (initNode.next.get(charInt) == null) {
                nodes.add(new Node2(this.size, initNode, s.charAt(i)));
                initNode.next.set(charInt, this.last());
            }
            initNode = initNode.next.get(charInt);
        }
        initNode.isTerminal = true;
    }
    public boolean getWord(String s) {
        Node2 initNode = this.root;
        for (int i=0; i<s.length(); i++) {
            if (initNode.next.get(charToInt(s.charAt(i))) == null) {
                return false;
            }
            initNode = initNode.next.get(charToInt(s.charAt(i)));
        }
        return initNode.isTerminal;
    }
    public Node2 getLink(Node2 node) {
        if (node.suffLink == null) {
            if (node == this.root || node.parent == this.root) {
                node.suffLink = this.root;
            } else {
                node.suffLink = this.walk(this.getLink(node.parent), node.prevChar);
            }
        }
        return node.suffLink;
    }
    public Node2 walk(Node2 node, char c) {
        if (node.walk.get(charToInt(c)) == null) {
            if (node.next.get(charToInt(c)) != null) {
                node.walk.set(charToInt(c), node.next.get(charToInt(c)));
            } else if (node == this.root) {
                node.walk.set(charToInt(c), this.root);
            } else {
                node.walk.set(charToInt(c), this.walk(this.getLink(node), c));
            }
        }
        return node.walk.get(charToInt(c));
    }
}