package net.tdx.gym.trie;

import java.io.*;
import java.util.*;

/**
 * Watto and Mechanism
 * CF514C
 * http://codeforces.com/problemset/problem/514/c
 */
public class CF514C {
	public static void main(String[] args) throws FileNotFoundException {
		InputStream inputStream = System.in;
//		InputStream inputStream = new FileInputStream(new File("src/input"));
		OutputStream outputStream = System.out;

		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Task2 solver = new Task2();
		solver.solve(in, out);
		out.close();
	}

	static class Trie {
		Node root = new Node();

		void addWord(String word) {
			Node cur = root;

			for (Character c: word.toCharArray()) {
				cur = cur.addNext(c);
			}
		}

		boolean isDiffer(String word, int total) {
			return isDiffer(word, -1, total, root);
		}

		boolean isDiffer(String word, int pos, int total, Node start) {
			if (pos == word.length() - 1) {
				return total == 0 && start.next.size() == 0;
			}

			Character nextChar = word.charAt(pos + 1);
			boolean res = false;
			if (start.hasNext(nextChar)) {
				res = isDiffer(word, pos + 1, total, start.getNext(nextChar));
			}

			if (total > 0) {
				for (Character nc: start.next.keySet()) {
					if (nc != nextChar) {
						res |= isDiffer(word, pos + 1, total - 1, start.getNext(nc));
					}
				}
			}

			return res;
		}

		class Node {
			Character c;
			Map<Character, Node> next = new HashMap<>();

			boolean hasNext(Character n) {
				return next.containsKey(n);
			}

			Node getNext(Character n) {
				return next.get(n);
			}

			Node addNext(Character n) {
				if (!hasNext(n)) {
					Node newNode = new Node();
					newNode.c = n;
					next.put(n, newNode);
					return newNode;
				}

				return next.get(n);
			}
		}
	}

	// Hashing
	static class Task2 {
		static long MOD = (int)1e9 + 7;
		static int BASE = 31;

		static long pow(int a, int b, long p) {
			if (b == 0) {
				return 1 % p;
			}

			long half = pow(a, b/2, p);
			if (b % 2 == 1) {
				return (half * half % p * a % p);
			} else {
				return (half * half % p);
			}
		}

		static long getHash(String a) {
			long res = 0;
			for (Character c: a.toCharArray()) {
				res = res * BASE % MOD;
				res += c - 'a';
			}
			return res;
		}

		public void solve(InputReader in, PrintWriter out) {
			int n, m;
			Map<Long, Set<String>> hashes = new HashMap<>();
			n = in.nextInt();
			m = in.nextInt();

			for (int i = 0; i < n; ++i) {
				String word = in.next();
				long hash = getHash(word);
				if (!hashes.containsKey(hash)) {
					hashes.put(hash, new HashSet<>());
				}

				hashes.get(hash).add(word);
			}

			for (int i = 0; i < m; ++i) {
				String word = in.next();
				Long curHash = getHash(word);

				boolean ok = false;

				for (int j = 0; j < word.length() && !ok; ++j) {
					Character cur = word.charAt(j);
					for (Character c: "abc".toCharArray()) {
						if (c != cur) {
							long newHash = (curHash + (c - cur) * pow(BASE, word.length() - j - 1, MOD) % MOD + MOD) % MOD;

							if (hashes.containsKey(newHash)) {
								StringBuilder s = new StringBuilder(word);
								s.setCharAt(j, c);
								ok |= hashes.get(newHash).contains(s.toString());
							}
						}
					}
				}

				out.println(ok ? "YES" : "NO");
			}
		}
	}

	// TRIE: TLE on test 33
	static class Task {
		public void solve(int testNumber, InputReader in, PrintWriter out) {
			int n, m;
			Trie trie = new Trie();
			n = in.nextInt();
			m = in.nextInt();

			for (int i = 0; i < n; ++i) {
				String word = in.next();
				trie.addWord(word);
			}

			for (int i = 0; i < m; ++i) {
				String word = in.next();
				boolean res = trie.isDiffer(word, 1);
				out.println(res ? "YES" : "NO");
			}
		}
	}

	static class InputReader {
		BufferedReader reader;
		StringTokenizer tokenizer;

		InputReader(InputStream stream) {
			reader = new BufferedReader(new InputStreamReader(stream), 32768);
			tokenizer = null;
		}

		String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}
	}
}
