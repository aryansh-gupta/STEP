class DeliverySlot {
    private String orderId;
    private String timeSlot;
    public DeliverySlot(String orderId, String timeSlot) {
        this.orderId = orderId;
        this.timeSlot = timeSlot;
    }
    public DeliverySlot(String orderId) {
        this(orderId, "ASAP");
    }
    public boolean isPeakHour() {
        return timeSlot.equals("12:00-13:00")
            || timeSlot.equals("13:00-14:00")
            || timeSlot.equals("19:00-20:00")
            || timeSlot.equals("20:00-21:00");
    }
}
