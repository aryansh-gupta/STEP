import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Problem3_NetBalancePeriodCounter {
    public static long countPeriods(int[] transactions, long k) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }
        Map<Long, Long> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1L);
        long currentRunningSum = 0;
        long totalMatchingPeriods = 0;
        for (int tx : transactions) {
            currentRunningSum += tx;
            long neededPrefix = currentRunningSum - k;
            if (prefixCounts.containsKey(neededPrefix)) {
                totalMatchingPeriods += prefixCounts.get(neededPrefix);
            }
            prefixCounts.put(currentRunningSum, prefixCounts.getOrDefault(currentRunningSum, 0L) + 1L);
        }
        return totalMatchingPeriods;
    }
    public static void main(String[] args) {
        int[] tx1 = {3, 4, -7, 1, 3, 3, 1, -4};
        long k1 = 7;
        long res1 = countPeriods(tx1, k1);
        System.out.println("Transactions: " + Arrays.toString(tx1) + ", Target k: " + k1);
        System.out.println("Expected: 4, Output: " + res1);
        int[] tx2 = {1, 2, 3};
        long k2 = 10;
        long res2 = countPeriods(tx2, k2);
        System.out.println("Transactions: " + Arrays.toString(tx2) + ", Target k: " + k2);
        System.out.println("Expected: 0, Output: " + res2);
    }
}
