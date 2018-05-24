package se.zeeraa.HangManAI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordList {
	private ArrayList<String> words = new ArrayList<>();

	public void clear() {
		words.clear();
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public boolean load(File file) {
		clear();
		System.out.println("Loading words list from " + file.toURI());
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				words.add(line.toLowerCase());
			}
		} catch (Exception e) {
			System.err.println("Loading failed\n");
			e.printStackTrace();
			return false;
		}
		System.out.println("Loaded " + words.size() + " words");
		return true;
	}

	// Version 1
	public Map<Character, Integer> getLettersUsedInMostWords1() {
		Map<Character, Integer> result = new HashMap<>();
		for (String word : words) {

			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);

				if (result.containsKey(ch)) {
					result.put(ch, result.get(ch) + 1);
				} else {
					result.put(ch, 1);
				}
			}
		}

		return result;
	}

	// Version 2
	public Map<Character, Integer> getLettersUsedInMostWords2() {
		Map<Character, Integer> result = new HashMap<>();
		for (String word : words) {
			ArrayList<Character> used = new ArrayList<>();
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				if (!used.contains(ch)) {
					if (result.containsKey(ch)) {
						used.add(ch);
						result.put(ch, result.get(ch) + 1);
					} else {
						used.add(ch);
						result.put(ch, 1);
					}
				}
			}
		}

		return result;
	}

	public void removeWordsThatContains(char letter) {
		ArrayList<String> words2 = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i).toLowerCase();
			if (!(letter + "").equalsIgnoreCase(" ")) {
				if (!(word.contains(letter + ""))) {
					words2.add(word);
				}
			}
		}
		words = words2;
	}

	public void filterLetterAt(int index, char letter) {
		ArrayList<String> words2 = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i).toLowerCase();
			if (word.length() - 1 >= index) {
				if (word.charAt(index) == letter) {
					words2.add(word);
				}
			}
		}
		words = words2;
	}

	public void filterLength(int length) {
		ArrayList<String> words2 = new ArrayList<>();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i).toLowerCase();
			if (word.length() == length && word.length() > 0) {
				words2.add(word);
			}
		}
		words = words2;

	}
}
