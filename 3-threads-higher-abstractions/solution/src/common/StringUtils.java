package common;

public class StringUtils {

    public static int countOccurrences(String content, String pattern) {
        if (content == null || content.isEmpty() || pattern == null || pattern.isEmpty()) {
            return 0;
        }
        int count = 0;
        int fromIdx = 0;
        int idx;
        while ((idx = content.indexOf(pattern, fromIdx)) >= 0) {
            fromIdx = idx + pattern.length();
            count += 1;
        }
        return count;
    }
}
