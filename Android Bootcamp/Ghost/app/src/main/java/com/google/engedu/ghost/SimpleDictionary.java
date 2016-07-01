package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int searchDex = binarySearch(prefix);
        if (searchDex != -1) { return words.get(searchDex); }
        return null; // return null if no words w/ prefix
    }

    @Override
    /*
     * Starts with binary search index and iterates backwards and forwards
     * to find the range of words starting with that prefix. Then, it randomly
     * returns one of those words. It's better than getAnyWordStartingWith()
     * because it won't pick the same word every time if you feed it the same prefix,
     * making the CPU (theoretically) harder to beat.
     */
    public String getGoodWordStartingWith(String prefix) {
        int bIndex = binarySearch(prefix);
        if (bIndex == -1) { return null; }

        int lowerBound = bIndex;
        int upperBound = bIndex;

        while (words.get(lowerBound).startsWith(prefix)) {
            if (lowerBound > 0) lowerBound--;
        }
        lowerBound++;
        while (words.get(upperBound).startsWith(prefix)) {
            if (upperBound < words.size() - 1) upperBound++;
        }

        Random r = new Random();
        int randDex = r.nextInt(upperBound - lowerBound) + lowerBound;
        return words.get(randDex);
    }

    private int binarySearch(String prefix) {
        int lo = 0;
        int hi = words.size() - 1;
        // if there's no prefix, return a random word
        if (prefix.isEmpty() || prefix == null) {
            return (int)(Math.random() * words.size());
        }

        while (lo <= hi) {
            int mid = (lo+hi)/2;
            String dictWord = words.get(mid);
            if ((dictWord.length() > prefix.length()) && (dictWord.startsWith(prefix))) {
                return mid;
            } else if (prefix.compareTo(dictWord) < 0) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return -1;
    }
}
