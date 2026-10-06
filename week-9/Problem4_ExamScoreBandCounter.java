import java.util.Arrays;

public class Problem4_ExamScoreBandCounter {
    public static int countInBand(int[] scores, int low, int high) {
        if (scores == null || scores.length == 0 || low > high) {
            return 0;
        }
        int firstGeLow = lowerBound(scores, low);
        int firstGtHigh = upperBound(scores, high);
        return Math.max(0, firstGtHigh - firstGeLow);
    }
    private static int lowerBound(int[] scores, int target) {
        int left = 0;
        int right = scores.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    private static int upperBound(int[] scores, int target) {
        int left = 0;
        int right = scores.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        int low1 = 42, high1 = 58;
        int res1 = countInBand(scores, low1, high1);
        System.out.println("Scores: " + Arrays.toString(scores));
        System.out.println("Band [" + low1 + ", " + high1 + "] -> Expected: 6, Output: " + res1);
        int low2 = 90, high2 = 100;
        int res2 = countInBand(scores, low2, high2);
        System.out.println("Band [" + low2 + ", " + high2 + "] -> Expected: 0, Output: " + res2);
    }
}
