package com.maksim.TrieProject.utils;

import java.io.Serializable;
import java.util.Objects;

public class HashMap<K, V> implements Serializable {
    private static class Node<K, V> implements Serializable {
        K key;
        int hash;
        V value;
        Node<K, V> next;

        Node(K key, int hash, V value) {
            this.key = key;
            this.hash = hash;
            this.value = value;
        }
    }

    public static class Iterator<K, V> {
        private int index;
        private Node<K, V> cur;
        private final HashMap<K, V> map;

        private Iterator(HashMap<K, V> map) {
            this.map = map;
            index = -1;
        }

        public boolean next() {
            boolean flag = cur != null;
            while (cur == null && index + 1 < map.capacity) {
                index++;
                cur = map.table[index];
            }
            if (cur == null) return false;

            if (flag) {
                cur = cur.next;
            }
            if (cur == null) {
                return next();
            }
            return true;
        }

        public Pair<K, V> getNext() {
            return new Pair<K, V>(cur.key, cur.value);
        }
    }

    private final Node<K, V>[] table;
    private final int capacity;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.table = new Node[capacity];
    }

    private int getCeil(int hash) {
        return hash % this.capacity;
    }

    public boolean insert(K key, V value) {
        int hash = Objects.hashCode(key);
        int ceil = getCeil(hash);
        if (table[ceil] == null) {
            Node<K, V> node = new Node<>(key, hash, value);
            table[ceil] = node;
            return true;
        }
        Node<K, V> cur = table[ceil];
        Node<K, V> prev = cur;
        while (cur != null) {
            if (cur.hash == hash && Objects.equals(cur.key, key)) {
                cur.value = value;
                return false;
            }
            prev = cur;
            cur = cur.next;
        }
        // Никогда не станет null, выше есть проверка
        prev.next = new Node<>(key, hash, value);
        return true;
    }

    public V get(K key) {
        int hash = Objects.hashCode(key);
        int ceil = getCeil(hash);
        Node<K, V> node = table[ceil];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    boolean remove(K key) {
        int hash = Objects.hashCode(key);
        int ceil = getCeil(hash);
        Node<K, V> node = table[ceil];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public void print() {
        for (Node<K, V> kvNode : table) {
            Node<K, V> cur = kvNode;
            while (cur != null) {
                System.out.println("{" + cur.key + " : " + cur.value + "}");
                cur = cur.next;
            }
        }
    }

    public Iterator<K, V> getIterator() {
        return new Iterator<K, V>(this);
    }

}
