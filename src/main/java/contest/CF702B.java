package contest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CF702B {
	public static void main(String[] args) throws FileNotFoundException {
		InputStream inputStream = System.in;
//		InputStream inputStream = new FileInputStream(new File("src/input"));
		OutputStream outputStream = System.out;

		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Task solver = new Task();
		solver.solve(1, in, out);
		out.close();
	}

	static class Task {
		public void solve(int testNumber, InputReader in, PrintWriter out) {
			int n = in.nextInt();
			Map<Integer, Integer> count = new HashMap<>();
			for (int i = 0; i < n; ++i) {
				int num = in.nextInt();
				if (!count.containsKey(num)) {
					count.put(num, 1);
				} else {
					count.put(num, count.get(num) + 1);
				}
			}

			long res = 0;
			for (Integer k: count.keySet()) {
				for (int p = 1; p <= 31; ++p) {
					int other = (1 << p) - k;
					if (count.containsKey(other)) {
//						System.out.println(k + " " + other);
						if (other != k) {
							res += (long) count.get(k) * count.get(other);
						} else {
							res += (long) count.get(k) * (count.get(k) - 1);
						}
					}
				}
			}

			res /= 2;
			out.print(res);
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
