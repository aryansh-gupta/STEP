public class Problem5_RaceWideBibSettlementEngine {
    public static class RaceEntry {
        private static int bibCounter = 0;
        protected final String bibNumber;
        protected final double entryFee;
        protected final String entryCode;
        protected double paidAmount;
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
            bibCounter++;
            this.entryCode = "ENTRY-" + bibCounter;
        }
        public void pay(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }
            this.paidAmount += amount;
        }
        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
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
        public String getEntryCode() {
            return entryCode;
        }
        public static int getBibCounter() {
            return bibCounter;
        }
        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) {
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) ||
                !Character.isDigit(code.charAt(2)) ||
                !Character.isDigit(code.charAt(3))) {
                return false;
            }
            if (!Character.isUpperCase(code.charAt(4))) {
                return false;
            }
            return true;
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
    public static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;
        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }
        public double getSponsorBonus() {
            return sponsorBonus;
        }
    }
    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;
        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("Team size must be positive");
            }
            this.teamSize = teamSize;
        }
        public int getTeamSize() {
            return teamSize;
        }
    }
    public static String settleNight(RaceEntry[] entries) {
        if (entries == null) {
            return "0 processed | 0 null skipped | 0 relay | 0 individual";
        }
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;
        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }
    public static void main(String[] args) {
        System.out.println("isValidDiscountCode(\"M123A\"): " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("isValidDiscountCode(\"M12A\"): " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("isValidDiscountCode(\"X123A\"): " + RaceEntry.isValidDiscountCode("X123A"));
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(10, "UPI");
        System.out.println("Balance after payment: " + runner.getBalanceDue());
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite 42K", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] batch = {eliteEntry, null, relayEntry};
        System.out.println("Settlement: " + settleNight(batch));
        System.out.println("Total bibs created: " + RaceEntry.getBibCounter());
    }
}
