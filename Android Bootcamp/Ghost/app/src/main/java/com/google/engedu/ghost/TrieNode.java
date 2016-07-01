package com.google.engedu.ghost;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    // Adds given word to Trie
    public void add(String s) {
        TrieNode prevNode = this;
        char[] word = s.toCharArray();

        for (char c : word) {
            TrieNode nextNode = new TrieNode();
            if (prevNode.children.containsKey(c))
                nextNode = prevNode.children.get(c);
            else
                prevNode.children.put(c, nextNode);

            prevNode = nextNode;
        }
        prevNode.isWord = true;
    }

    // Recursively gets you the last node/letter of a given word
    private TrieNode get(String key) {
        TrieNode t = getHelper(this, key, 0);
        if (t == null) { return null; }
        return t;
    }

    private TrieNode getHelper(TrieNode t, String key, int index) {
        if (t == null) { return null; }
        if (index == key.length()) { return t; }
        TrieNode nextNode = t.children.get(key.charAt(index));

        return getHelper(nextNode, key, index + 1);
    }

    public boolean isWord(String s) {
        TrieNode searched = get(s);
        if (searched == null) { return false; }
        return searched.isWord;
    }

    public String getAnyWordStartingWith(String prefix) {
        TrieNode currentNode = get(prefix);
        if (currentNode == null || currentNode.children.size() == 0) return null;

        char randKey = getRandKey(currentNode);
        Log.d("Prefix + RandKey", prefix + randKey);
        currentNode = currentNode.children.get(randKey);
        String suffix = String.valueOf(randKey);

        while (currentNode.children.size() > 0) {
            if (getWordKey(currentNode) != null) {
                suffix += getWordKey(currentNode);
                break;
            }
            randKey = getRandKey(currentNode);
            suffix += randKey;
            currentNode = currentNode.children.get(randKey);
        }
        Log.d("Suggested word", prefix + suffix);
        return prefix + suffix;
    }

    public Character getWordKey(TrieNode t) {
        for (char key : t.children.keySet()) {
            TrieNode child = t.children.get(key);
            if (child.isWord) return key;
        }
        return null;
    }

    public Character getRandKey(TrieNode t) {
        ArrayList<Character> keys = new ArrayList<>(t.children.keySet());
        int randDex = (int) Math.random() * keys.size();
        return keys.get(randDex);
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
