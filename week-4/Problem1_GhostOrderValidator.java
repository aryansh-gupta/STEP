class FoodOrder {
    private final String studentName;
    private final String dishName;
    private boolean delivered;
    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }
    public void markDelivered() {
        if (!delivered) {
            delivered = true;
            System.out.println("Order marked as delivered.");
        } else {
            System.out.println("Order was already delivered.");
        }
    }
    public static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;
        for (String[] raw : rawOrders) {
            try {
                if (raw == null || raw.length < 2) {
                    throw new IllegalArgumentException("Invalid order data");
                }
                new FoodOrder(raw[0], raw[1]);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}
