package contest;

import java.io.*;
import java.util.StringTokenizer;

public class CF702A {
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
			int res = 0;
			int last = -1, cnt = 0;
			for (int i = 0; i < n; ++i) {
				int num = in.nextInt();
				if (num > last) {
					cnt += 1;
					last = num;
				} else {
					res = res < cnt ? cnt : res;
					cnt = 1;
					last = num;
				}
			}

			res = Math.max(res, cnt);

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
