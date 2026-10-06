public class Problem2_ThreeShapesOfOneRaceFamily {
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
        public double getEntryFee() {
            return entryFee;
        }
        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }
    public static class RunnerEntry extends RaceEntry {
        protected final String category;
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
    public static class EliteRunnerEntry extends RunnerEntry {
        private final double sponsorBonus;
        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            if (sponsorBonus < 0) {
                throw new IllegalArgumentException("Sponsor bonus cannot be negative");
            }
            this.sponsorBonus = sponsorBonus;
        }
        public double getSponsorBonus() {
            return sponsorBonus;
        }
        @Override
        public String announce() {
            return "Elite Runner | Bib: " + bibNumber + " | Category: " + category + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
        }
    }
    public static class RelayTeamEntry extends RaceEntry {
        private final int teamSize;
        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("Team size must be a positive integer");
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
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single-inheritance child (2 generations deep)";
        } else if (entry != null) {
            return "Base generation";
        }
        return "Unknown";
    }
    public static double getTotalBalanceDue(RaceEntry[] entries) {
        if (entries == null) return 0.0;
        double total = 0.0;
        for (RaceEntry entry : entries) {
            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }
        return total;
    }
    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());
        System.out.println("eliteEntry classification: " + classifyGeneration(eliteEntry));
        System.out.println("relayEntry classification: " + classifyGeneration(relayEntry));
        RaceEntry[] entries = {runnerEntry, eliteEntry, relayEntry};
        System.out.println("Total balance due: " + getTotalBalanceDue(entries));
    }
}
