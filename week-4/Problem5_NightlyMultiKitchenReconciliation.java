class DeliveryAccount {
    private static String systemName;
    static {
        systemName = "Campus Delivery Reconciliation";
    }
    protected final String studentId;
    protected final double orderValue;
    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }
    public final double calculateSurgeFee(int delayMinutes) {
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
        double floor = orderValue * 0.01;
        return Math.max(fee, floor);
    }
    public static class PremiumAccount extends DeliveryAccount {
        public PremiumAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }
    }
    public void processAccount(
            DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            System.out.println("Null account skipped.");
            return;
        }
        double fee = account.calculateSurgeFee(delayMinutes);
        if (account instanceof PremiumAccount) {
            fee = fee * 0.50;
            System.out.println(
                "Premium: " + account.studentId + " | Surge = Rs " + fee);
        } else {
            System.out.println(
                "Regular: " + account.studentId + " | Surge = Rs " + fee);
        }
    }
    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {
        if (accounts == null || amounts == null
                || delayMinutesArray == null) {
            throw new IllegalArgumentException("Input arrays cannot be null");
        }
        int limit = Math.min(
            accounts.length,
            Math.min(amounts.length, delayMinutesArray.length)
        );
        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double grandTotal = 0.0;
        for (int i = 0; i < limit; i++) {
            DeliveryAccount account = accounts[i];
            if (account == null) {
                nullSkipped++;
                continue;
            }
            double fee = account.calculateSurgeFee(delayMinutesArray[i]);
            if (account instanceof PremiumAccount) {
                fee *= 0.50;
                premium++;
            } else {
                regular++;
            }
            grandTotal += fee;
            processed++;
        }
        System.out.println(processed + " processed | "
            + nullSkipped + " null skipped | "
            + premium + " premium | "
            + regular + " regular | "
            + "grand total surge fees = Rs " + grandTotal);
    }
}
