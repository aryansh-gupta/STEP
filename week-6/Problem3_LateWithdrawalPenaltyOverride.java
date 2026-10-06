import java.util.Arrays;

public class Problem3_LateWithdrawalPenaltyOverride {
    public static class RaceEntry {
        protected final String bibNumber;
        protected final double entryFee;
        protected double paidAmount;
        protected double totalLateFees;
        private final double[] lateFeeHistory;
        private int lateFeeCount;
        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number cannot be blank or shorter than 4 characters");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("Entry fee must be positive");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.paidAmount = 0.0;
            this.totalLateFees = 0.0;
            this.lateFeeHistory = new double[10];
            this.lateFeeCount = 0;
        }
        public void pay(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
            this.paidAmount += amount;
        }
        protected void applyLateFee(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Late fee amount must be positive");
            }
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount++] = amount;
            }
            this.totalLateFees += amount;
        }
        public double getBalanceDue() {
            return (this.entryFee - this.paidAmount) + this.totalLateFees;
        }
        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
        public String getBibNumber() {
            return bibNumber;
        }
        public double getEntryFee() {
            return entryFee;
        }
    }
    public static class RunnerEntry extends RaceEntry {
        private final String category;
        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalArgumentException("Category cannot be blank");
            }
            this.category = category.trim();
        }
        public String getCategory() {
            return category;
        }
        @Override
        public void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println("Balance due after penalty: " + r.getBalanceDue());
        double[] history = r.getLateFeeHistory();
        System.out.println("Recorded history: " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("History after tampering: " + Arrays.toString(r.getLateFeeHistory()));
    }
}
