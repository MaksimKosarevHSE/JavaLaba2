package com.maksim.TrieProject;

import com.maksim.TrieProject.utils.HashMap;
import com.maksim.TrieProject.utils.LinkedList;

public class Trie {
    private static class Node {
        private boolean isTerminal;
        private final HashMap<Character, Node> children;

        private Node() {
            children = new HashMap<>(100);
            isTerminal = false;
        }
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            Node node = cur.children.get(word.charAt(i));
            if (node == null) {
                Node newNode = new Node();
                cur.children.insert(word.charAt(i), newNode);
            }
            cur = cur.children.get(word.charAt(i));
        }
        cur.isTerminal = true;
    }

    private Node search(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            Node node = cur.children.get(word.charAt(i));
            if (node == null) {
                return null;
            }
            cur = node;
        }
        return cur;
    }

    public boolean contains(String word) {
        Node node = search(word);
        return node != null && node.isTerminal;
    }

    public boolean startsWith(String prefix) {
        return search(prefix) != null;
    }

    private void getByPrefix_(Node node, StringBuilder sb, LinkedList<String> list) {
        if (node.isTerminal) list.add(sb.toString());
        var iter = node.children.getIterator();
        while (iter.next()) {
            var el = iter.getNext();
            var tmp = new StringBuilder(sb);
            getByPrefix_(el.value, sb.append(el.key), list);
            sb = tmp;
        }
    }

    public LinkedList<String> getByPrefix(String prefix) {
        Node cur = root;
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < prefix.length(); i++) {
            Node node = cur.children.get(prefix.charAt(i));
            if (node == null) {
                return new LinkedList<>();
            }
            cur = node;
        }
        LinkedList<String> result = new LinkedList<>();
        getByPrefix_(cur, sb, result);
        return result;
    }

}
