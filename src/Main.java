import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --> Set to true to run JSON test cases (functional tests)
        // --> Set to false to run performance tests (n = 1 to 1,000,000)
        boolean runTests = false;

        if (runTests) {

            // --> Load KMP test cases from JSON file
            String jsonPath = "data/kmp_test_cases.json";
            List<TestCase> testCases = JsonTestCaseLoader.loadFromFile(jsonPath);

            // --> If JSON is empty or missing
            if (testCases.isEmpty()) {
                System.out.println("No test cases found. Check the file: " + jsonPath);
                return;
            }

            System.out.println("KMP String Matching Tests");
            System.out.println("=========================\n");

            // --> Run KMP on each test case loaded from JSON
            for (TestCase tc : testCases) {
                System.out.println("Test ID       : " + tc.getId());
                System.out.println("Text          : " + tc.getText());
                System.out.println("Pattern       : " + tc.getPattern());

                long startTime = System.nanoTime();     // --> start timing
                List<Integer> matches = KmpMatcher.search(tc.getText(), tc.getPattern());
                long endTime = System.nanoTime();       // --> end timing

                long durationNs = endTime - startTime;
                double durationMs = durationNs / 1_000_000.0;  // --> ns -> ms

                System.out.println("Match indices : " + matches);
                System.out.println("Time (ms)     : " + durationMs);
                System.out.println("----------------------------------------\n");
            }

        } else {

            // --> Performance benchmark mode
            System.out.println("\n\nKMP Performance Tests (n = 1 to 1,000,000)");
            System.out.println("===========================================\n");

            // --> Sizes to test: from 1 to 1,000,000
            int[] sizes = {1, 10, 100, 1000, 10000, 100000, 1_000_000};

            for (int n : sizes) {

                // --> Pattern placed at the end of the string (worst-case scenario)
                String pattern = "AAAB";
                String text = generateText(n, pattern);

                // --> Warmup phase (JIT optimization warmup)
                int warmupRuns = Math.min(1000, 10_000_000 / n);
                for (int i = 0; i < warmupRuns; i++) {
                    KmpMatcher.search(text, pattern);
                }

                // --> Actual measured runs
                int measuredRuns = Math.max(100, 1_000_000 / n);
                long totalNs = 0;

                for (int i = 0; i < measuredRuns; i++) {
                    long start = System.nanoTime();
                    List<Integer> matches = KmpMatcher.search(text, pattern);
                    long end = System.nanoTime();

                    totalNs += (end - start);

                    // --> Prevent JVM optimization from removing the call
                    if (matches.isEmpty() && i == 0) {

                    }
                }

                double avgNs = (double) totalNs / measuredRuns;
                double avgMs = avgNs / 1_000_000.0;

                // --> Get final result to print
                List<Integer> finalMatches = KmpMatcher.search(text, pattern);

                // --> Display performance results
                System.out.println("n = " + n);
                System.out.println("Text length          : " + text.length());
                System.out.println("Pattern length       : " + pattern.length());
                System.out.println("Match indices        : " + finalMatches);
                System.out.println("Avg time per run (ms): " + String.format("%.6f", avgMs));
                System.out.println("-----------------------------\n");
            }
        }
    }


    private static String generateText(int n, String pattern) {

        // --> If n is smaller than pattern, return a truncated pattern
        if (n <= pattern.length()) {
            return pattern.substring(0, n);
        }

        StringBuilder sb = new StringBuilder(n);

        // --> Fill with 'A' except the last part (where pattern will be placed)
        int prefixLength = n - pattern.length();
        for (int i = 0; i < prefixLength; i++) {
            sb.append('A');
        }

        // --> Append the pattern at the end
        sb.append(pattern);

        return sb.toString();
    }
}
