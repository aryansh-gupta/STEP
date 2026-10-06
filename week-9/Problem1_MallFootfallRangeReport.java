import java.util.*;

public class Problem1_MallFootfallRangeReport {
    public static List<Long> footfallReport(int[] visitors, int[][] queries) {
        if (visitors == null || queries == null) {
            return Collections.emptyList();
        }
        int n = visitors.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + visitors[i];
        }
        List<Long> result = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];
            if (start >= 0 && end < n && start <= end) {
                long total = prefixSum[end + 1] - prefixSum[start];
                result.add(total);
            } else {
                throw new IndexOutOfBoundsException("Query range [" + start + ", " + end + "] out of bounds for length " + n);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {
            {0, 2},
            {2, 5},
            {4, 6},
            {3, 3}
        };
        List<Long> report = footfallReport(visitors, queries);
        System.out.println("Input visitors: " + Arrays.toString(visitors));
        System.out.println("Queries: " + Arrays.deepToString(queries));
        System.out.println("Footfall Report: " + report);
    }
}
