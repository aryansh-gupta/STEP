public class Problem3_BookCopyCirculationGuard {
    public static class BookInventory {
        private final int copiesTotal;
        private int copiesAvailable;

        public BookInventory(int copiesTotal) {
            if (copiesTotal <= 0 || copiesTotal > 500) {
                throw new IllegalArgumentException("Total copies must be positive and at most 500");
            }
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        public void checkOut() {
            if (copiesAvailable > 0) {
                copiesAvailable--;
            }
        }

        public void checkIn() {
            if (copiesAvailable < copiesTotal) {
                copiesAvailable++;
            }
        }

        public int getCopiesAvailable() {
            return copiesAvailable;
        }
    }

    public static void main(String[] args) {
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected as expected: " + e.getMessage());
        }

        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // rejected: count never goes negative
        System.out.println("Available after 4 checkouts: " + b.getCopiesAvailable());

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // rejected: cannot exceed total
        System.out.println("Available after 4 checkins: " + b.getCopiesAvailable());
    }
}
