final class SurgeFeeCalculator {
    private final double minimumSurgePercent;
    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }
    public final double calculateSurgeFee(
            double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException(
                "orderValue and delayMinutes cannot be negative");
        }
        if (delayMinutes == 0) return 0.0;
        int firstTier = Math.min(delayMinutes, 5);
        int secondTier = Math.min(Math.max(delayMinutes - 5, 0), 10);
        int thirdTier = Math.max(delayMinutes - 15, 0);
        double fee =
            orderValue * 0.005 * firstTier
          + orderValue * 0.01  * secondTier
          + orderValue * 0.02  * thirdTier;
        double floor = orderValue * minimumSurgePercent / 100.0;
        return Math.max(fee, floor);
    }
}
