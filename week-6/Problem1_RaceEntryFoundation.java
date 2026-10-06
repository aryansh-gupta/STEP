public class Problem1_RaceEntryFoundation {
    public static class RaceEntry {
        protected final String bibNumber;
        protected final double entryFee;
        protected double paidAmount;
        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number cannot be blank or shorter than 4 characters");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("Entry fee must be a positive double");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.paidAmount = 0.0;
        }
        public void pay(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
            this.paidAmount += amount;
        }
        public double getBalanceDue() {
            return this.entryFee - this.paidAmount;
        }
        public String getBibNumber() {
            return bibNumber;
        }
        public double getEntryFee() {
            return entryFee;
        }
        public double getPaidAmount() {
            return paidAmount;
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
    }
    public static String registerBatch(String[] bibNumbers, double entryFee) {
        if (bibNumbers == null) {
            return "Registered: 0 | Rejected: 0";
        }
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
            System.out.println("Construction succeeded unexpectedly");
        } catch (IllegalArgumentException e) {
            System.out.println("new RaceEntry(\"B1\", 50) -> construction rejected: " + e.getMessage());
        }
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println("Balance due for " + r.getBibNumber() + ": " + r.getBalanceDue());
        String[] batch = {"BIB1", "B1", "BIB2"};
        String batchResult = registerBatch(batch, 80);
        System.out.println("Batch result: " + batchResult);
    }
}
