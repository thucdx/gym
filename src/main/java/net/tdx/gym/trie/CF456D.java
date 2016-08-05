package net.tdx.gym.trie;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Matching Names
 * CF456D
 * http://codeforces.com/problemset/problem/456/D
 */
public class CF456D {
	private static int n, k;
	private static List<String> words = new ArrayList<>();
	private static Node root;

	static class Node {
		Character c;
		Map<Character, Node> nextChars = new HashMap<>();
		boolean canWin, canLose;

		public Node(Character c, boolean canWin, boolean canLose) {
			this.c = c;
			this.canWin = canWin;
			this.canLose = canLose;
		}

		public void addNode(Character c) {
			Node newNode = new Node(c, false, false);
			nextChars.put(c, newNode);
		}

		public boolean hasNode(Character c) {
			return nextChars.containsKey(c);
		}

		public Node getNode(Character c) {
			return nextChars.get(c);
		}
	}

	static void buildTrie() {
		root = new Node(null, false, false);
		for (String word: words) {
			Node cur = root;
			for (int i = 0; i < word.length(); ++i) {
				Character c = word.charAt(i);
				if (cur.hasNode(c)) {
					cur = cur.getNode(c);
				} else{
					cur.addNode(c);
					cur = cur.getNode(c);
				}
			}
		}
	}

	static boolean canWin(Node a) {
		if (a.nextChars.size() < 1) {
			a.canWin = false;
		} else {
			boolean res = false;
			for (Node c: a.nextChars.values()) {
				res |= !canWin(c);
			}
			a.canWin = res;
		}
		return a.canWin;
	}

	static boolean canLose(Node a) {
		if (a.nextChars.size() < 1) { // leaf
			a.canLose = true;
		} else {
			boolean res = false;
			for (Node c : a.nextChars.values()) {
				res |= !canLose(c);
			}
			a.canLose = res;
		}

		return a.canLose;
	}

	static boolean getAnswer() {
		boolean canWin = canWin(root);
		boolean canLose = canLose(root);

		if (!canWin) {
			return false;
		}

		if (canLose) {
			return true;
		} else {
			return k % 2 == 1;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(new InputStreamReader(System.in));
		n = sc.nextInt();
		k = sc.nextInt();
		for (int i = 1; i <= n; ++i) {
			words.add(sc.next());
		}

		buildTrie();
		System.out.println(getAnswer() ? "First" : "Second");
	}
}
