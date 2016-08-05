import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CF702C {
	public static void main(String[] args) throws FileNotFoundException {
		InputStream inputStream = System.in;
//		InputStream inputStream = new FileInputStream(new File("src/input"));
		OutputStream outputStream = System.out;

		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Task solver = new Task();
		solver.solve(in, out);
		out.close();
	}

	static class Task {
		int nCity, nTower;
		List<Integer> cities = new ArrayList<>();
		List<Integer> towers = new ArrayList<>();

		boolean isOK(int r) {
//			System.out.println("Check " + r);
			int posTower = 0;
			for (int posCity = 0; posCity < nCity; ++posCity) {
				int city = cities.get(posCity);
				boolean ok = false;
				while (posTower < nTower) {
					int tower = towers.get(posTower);
					if ((long)tower - r <= (long)city && city <= (long)tower + r) {
						ok = true;
						break;
					}
					posTower++;
				}
				if (!ok) {
					return false;
				}
			}

			return true;
		}

		public void solve(InputReader in, PrintWriter out) {
			nCity = in.nextInt();
			nTower = in.nextInt();

			for (int i = 0; i < nCity; ++i) {
				cities.add(in.nextInt());
			}

			for (int j = 0; j < nTower; ++j) {
				towers.add(in.nextInt());
			}

			int head = 0, tail = (int) 2e9 + 5;

			while (head < tail - 1) {
				int mid = (int)(((long) head + tail)/2);
				if (isOK(mid)) {
					tail = mid;
				} else {
					head = mid + 1;
				}
			}

			out.print(isOK(head) ? head : tail);
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
