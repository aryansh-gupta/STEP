public class Problem4_RaceDayAnnouncerBoard {
    public static class RaceEntry {
        protected final String bibNumber;
        protected final double entryFee;
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
        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
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
        public String announce() {
            return "Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue();
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
        @Override
        public String announce() {
            return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
        }
    }
    public static String announceAll(RaceEntry[] entries) {
        if (entries == null) return "";
        StringBuilder sb = new StringBuilder();
        for (RaceEntry entry : entries) {
            sb.append(entry.announce());
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry rte = (RelayTeamEntry) entry;
                sb.append(" [Team size via downcast: ").append(rte.getTeamSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        RaceEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        RaceEntry[] fleet = {runnerEntry, relayEntry};
        System.out.println("Announcement:\n" + announceAll(fleet));
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println("Cast succeeded unexpectedly: " + bad);
        } catch (ClassCastException e) {
            System.out.println("Unguarded downcast threw ClassCastException as expected: " + e.getMessage());
        }
    }
}
