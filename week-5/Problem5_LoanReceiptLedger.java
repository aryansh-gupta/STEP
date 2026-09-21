public class Problem5_LoanReceiptLedger {
    public static class LoanReceipt {
        private static String systemName;
        static {
            systemName = "PageTurner Circulation Ledger";
        }

        protected final String memberId;
        protected final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("memberId cannot be null or blank");
            }
            if (bookIds == null) {
                throw new IllegalArgumentException("bookIds array cannot be null");
            }
            for (String bid : bookIds) {
                if (bid == null || !bid.matches("^BK-\\d{3}$")) {
                    throw new IllegalArgumentException("Invalid book ID format: " + bid);
                }
            }
            this.memberId = memberId;
            this.bookIds = bookIds.clone();
        }

        public String getMemberId() {
            return memberId;
        }

        public String[] getBookIds() {
            return bookIds.clone();
        }

        public LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("Invalid book index: " + index);
            }
            String[] updated = bookIds.clone();
            updated[index] = newId;
            return new LoanReceipt(this.memberId, updated);
        }

        public static String processNightlyCirculation(LoanReceipt[] receipts) {
            if (receipts == null) {
                return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
            }
            int processed = 0;
            int nullSkipped = 0;
            int refOnly = 0;
            int regular = 0;

            for (LoanReceipt r : receipts) {
                if (r == null) {
                    nullSkipped++;
                    continue;
                }
                processed++;
                if (r instanceof ReferenceOnlyLoanReceipt) {
                    refOnly++;
                } else {
                    regular++;
                }
            }
            return processed + " processed | " + nullSkipped + " null skipped | " + refOnly + " reference-only | " + regular + " regular";
        }
    }

    public static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = roomNumber;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        @Override
        public ReferenceOnlyLoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IndexOutOfBoundsException("Invalid book index: " + index);
            }
            String[] updated = bookIds.clone();
            updated[index] = newId;
            return new ReferenceOnlyLoanReceipt(this.memberId, updated, this.roomNumber);
        }
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected as expected: " + e.getMessage());
        }

        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Defensive copy verified: " + r.getBookIds()[0]);

        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(LoanReceipt.processNightlyCirculation(batch));
    }
}
