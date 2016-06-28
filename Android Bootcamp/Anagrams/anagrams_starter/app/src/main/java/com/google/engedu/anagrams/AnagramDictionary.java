package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int wordLength = DEFAULT_WORD_LENGTH;
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /* Contains all the words from the input */
    private ArrayList<String> wordList = new ArrayList<>();
    /* For rapidly checking if a word is valid */
    private HashSet<String> wordSet = new HashSet<>();
    /* Key is char. combo, value is ArrayList of its anagrams */
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    /* Key is length of word, value is ArrayList of words that size */
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            String sortedWord = sortLetters(word);

            wordList.add(word);
            wordSet.add(word);

            if (lettersToWord.containsKey(sortedWord)) {
                // if the word's an anagram of something
                // get ArrayList assoc. w/ sortedWord key, add word to list
                lettersToWord.get(sortedWord).add(word);
            } else {
                // Since it's a unique/unencountered char. combo, make an AList
                // for holding its anagrams, add the initial word, and put into HashMap
                ArrayList<String> keyList = new ArrayList<>();
                keyList.add(word);
                lettersToWord.put(sortedWord, keyList);
            }

            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                ArrayList<String> keyList = new ArrayList<>();
                keyList.add(word);
                sizeToWords.put(word.length(), keyList);
            }

        }
        optimizeWords();
    }

    /*
     * A word is 'good' if it's a valid dictionary word (i.e. in wordSet)
     * and doesn't contain the base word as a substring.
     * For example, isGoodWord("nonstop", "post") will return true,
     * but isGoodWord("poster", "post") will return false.
     */
    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    /*
     * Takes a string and finds all the anagrams of that string in wordList.
     * It uses the lettersToWord HashMap<String, ArrayList<String>>, using
     * the sorted version of the target word as a key to access a String ArrayList
     * that contains all of the sorted target word's anagrams.
     */
    public ArrayList<String> getAnagrams(String targetWord) {
        return lettersToWord.get(sortLetters(targetWord));
    }

    /*
     * Takes a string and finds all the anagrams if you'd hypothetically
     * added one letter to that word.
     */
    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (char c : ALPHABET) {
            String testWord = sortLetters(word + c);
            if (lettersToWord.containsKey(testWord)) {
                for (String s : lettersToWord.get(testWord)) {
                    if (isGoodWord(s, word)) { result.add(s); }
                }
            }
        }
        return result;
    }
    /* The oneMoreLetter anagrams of all oneMoreLetter anagrams of the original word */
    public ArrayList<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> oneMoreLetter = getAnagramsWithOneMoreLetter(word);
        ArrayList<String> result = new ArrayList<>();
        for(String s : oneMoreLetter) {
            result.addAll(getAnagramsWithOneMoreLetter(s));
        }

        HashSet<String> hs = new HashSet<>();
        hs.addAll(result);
        result.clear();
        result.addAll(hs); // removes duplicate entries

        return result;
    }

    // Randomly picks a word of wordLength with at least MIN_NUM_ANAGRAMS.
    public String pickGoodStarterWord() {
        ArrayList<String> nSizeWords = sizeToWords.get(wordLength);
        int index = random.nextInt(nSizeWords.size());
        if (wordLength < MAX_WORD_LENGTH) { wordLength++; }
        return nSizeWords.get(index);
    }

    /*
     * Removes words from HashMap for getting starter words if they
     * don't have enough anagrams.
     */
    private void optimizeWords() {
        for (HashMap.Entry<Integer, ArrayList<String>> entry : sizeToWords.entrySet()) {
            ArrayList<String> nSizeWords = entry.getValue();
            Iterator<String> iter = nSizeWords.iterator(); // using iterator so I can delete while iterating
            while (iter.hasNext()) {
                String word = iter.next();
                if (getAnagramsWithOneMoreLetter(word).size() < MIN_NUM_ANAGRAMS) {
                    iter.remove();
                }
            }
        }
    }

    /* Alphabetically sorts characters in a given string. */
    private String sortLetters(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return String.valueOf(arr);
    }
}
