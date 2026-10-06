import java.util.Arrays;

public class Problem2_LongestBudgetFriendlyStreak {
    public static class StreakResult {
        public final int length;
        public final int startIndex;
        public StreakResult(int length, int startIndex) {
            this.length = length;
            this.startIndex = startIndex;
        }
        @Override
        public String toString() {
            return "(" + length + ", " + startIndex + ")";
        }
    }
    public static StreakResult longestStreak(int[] costs, long budget) {
        if (costs == null || costs.length == 0 || budget < 0) {
            return new StreakResult(0, -1);
        }
        int maxLength = 0;
        int bestStart = -1;
        int left = 0;
        long currentSum = 0;
        for (int right = 0; right < costs.length; right++) {
            currentSum += costs[right];
            while (currentSum > budget && left <= right) {
                currentSum -= costs[left];
                left++;
            }
            if (currentSum <= budget && left <= right) {
                int currentLength = right - left + 1;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    bestStart = left;
                }
            }
        }
        if (maxLength == 0) {
            return new StreakResult(0, -1);
        }
        return new StreakResult(maxLength, bestStart);
    }
    public static void main(String[] args) {
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget1 = 8;
        StreakResult res1 = longestStreak(costs1, budget1);
        System.out.println("Costs: " + Arrays.toString(costs1) + ", Budget: " + budget1);
        System.out.println("Expected: (4, 4), Output: " + res1);
        int[] costs2 = {9, 10};
        long budget2 = 8;
        StreakResult res2 = longestStreak(costs2, budget2);
        System.out.println("Costs: " + Arrays.toString(costs2) + ", Budget: " + budget2);
        System.out.println("Expected: (0, -1), Output: " + res2);
    }
}
