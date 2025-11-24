package com.maksim.TrieProject.utils;

import java.io.Serializable;

public class LinkedList<T> implements Serializable {
    private static class Node<T> implements Serializable {
        T value;
        Node<T> next;
        Node<T> prev;

        private Node(T value) {
            this.value = value;
        }

        private Node() {
        }
    }

    private final Node<T> head;
    private int size;

    public LinkedList() {
        head = new Node<>();
        head.next = head;
        head.prev = head;
        size = 0;
    }

    private Node<T> find(T value) {
        if (size == 0) return null;
        Node<T> cur = head;
        while ((cur = cur.next) != head) {
            if (cur.value.equals(value)) return cur;
        }
        return null;
    }

    public LinkedList<T> add(T value) {
        Node<T> node = new Node<>(value);
        node.prev = head.prev;
        node.next = head;
        head.prev.next = node;
        head.prev = node;
        size++;
        return this;
    }

    public boolean remove(T value) {
        Node<T> node = find(value);
        if (node == null) return false;
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> cur = head;
        while ((cur = cur.next) != head) {
            sb.append(cur.value).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
