import java.util.ArrayList;
import java.util.List;


public class KmpMatcher {


    public static List<Integer> search(String text, String pattern) { // --> KMP search implementation
        List<Integer> result = new ArrayList<>();

        if (pattern == null || pattern.isEmpty()) { // --> Edge case: empty pattern
            return result;
        }

        if (text == null || text.length() < pattern.length()) { // --> Edge case: text shorter than pattern
            return result;
        }

        int[] lps = buildLpsArray(pattern); // --> Build LPS array

        int i = 0; // --> index for text
        int j = 0; // --> index for pattern

        while (i < text.length()) { // --> Traverse the text
            if (text.charAt(i) == pattern.charAt(j)) { // --> Characters match -> move both pointers
                i++;
                j++;


                if (j == pattern.length()) { // --> Found a full match
                    result.add(i - j); // --> Add starting index

                    j = lps[j - 1]; // --> Continue searching
                }
            } else {
                if (j != 0) { // --> Mismatch after j matches
                    j = lps[j - 1]; // --> roll back to the pattern using LPS
                } else {
                    i++; // --> No partial match, move text
                }
            }
        }

        return result; // --> Return all match starting indices
    }


    private static int[] buildLpsArray(String pattern) { // --> Build LPS (Longest Prefix Suffix) array
        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0; // --> Length of the previous longest prefix suffix
        int i = 1;   // --> lps[0] = 0 always

        while (i < m) { // --> Build the lps array
            if (pattern.charAt(i) == pattern.charAt(len)) { // --> Characters match
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) { // --> Mismatch after len matches
                    len = lps[len - 1];
                } else { // --> No match
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps; // --> Return the constructed LPS array
    }
}
