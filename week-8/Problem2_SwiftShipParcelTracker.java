import java.util.*;

public class Problem2_SwiftShipParcelTracker {
    public enum ParcelStatus {
        BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }
    public interface ShippingType {
        String getTypeName();
        double calculateCharge(double weightKg);
    }
    public static class StandardShipping implements ShippingType {
        @Override
        public String getTypeName() {
            return "Standard";
        }
        @Override
        public double calculateCharge(double weightKg) {
            return 40.0 + (10.0 * weightKg);
        }
    }
    public static class ExpressShipping implements ShippingType {
        @Override
        public String getTypeName() {
            return "Express";
        }
        @Override
        public double calculateCharge(double weightKg) {
            return 80.0 + (15.0 * weightKg);
        }
    }
    public static class FragileShipping implements ShippingType {
        private final StandardShipping standard = new StandardShipping();
        @Override
        public String getTypeName() {
            return "Fragile";
        }
        @Override
        public double calculateCharge(double weightKg) {
            return standard.calculateCharge(weightKg) + 50.0;
        }
    }
    public interface NotificationChannel {
        void notify(String parcelId, ParcelStatus status);
    }
    public static class SmsChannel implements NotificationChannel {
        @Override
        public void notify(String parcelId, ParcelStatus status) {
            System.out.println("[SMS] " + parcelId + " is now " + status + ".");
        }
    }
    public static class EmailChannel implements NotificationChannel {
        @Override
        public void notify(String parcelId, ParcelStatus status) {
            System.out.println("[Email] " + parcelId + " is now " + status + ".");
        }
    }
    public static class Parcel {
        private final String trackingId;
        private final double weightKg;
        private final ShippingType shippingType;
        private final double charge;
        private ParcelStatus status;
        private final List<NotificationChannel> channels;
        public Parcel(String trackingId, double weightKg, ShippingType shippingType) {
            if (trackingId == null || trackingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Tracking ID cannot be blank");
            }
            if (weightKg <= 0) {
                throw new IllegalArgumentException("Weight must be positive");
            }
            this.trackingId = trackingId.trim();
            this.weightKg = weightKg;
            this.shippingType = shippingType;
            this.charge = shippingType.calculateCharge(weightKg);
            this.status = ParcelStatus.BOOKED;
            this.channels = new ArrayList<>();
        }
        public void subscribeChannel(NotificationChannel channel) {
            if (channel != null && !channels.contains(channel)) {
                channels.add(channel);
            }
        }
        public void notifySubscribers() {
            for (NotificationChannel ch : channels) {
                ch.notify(trackingId, status);
            }
        }
        public boolean moveToStatus(ParcelStatus newStatus) {
            if (!isValidTransition(this.status, newStatus)) {
                System.out.println("Invalid transition: " + this.status + " → " + newStatus + " is not allowed.");
                return false;
            }
            this.status = newStatus;
            notifySubscribers();
            return true;
        }
        public boolean cancel() {
            if (this.status != ParcelStatus.BOOKED) {
                System.out.println("Cancellation failed: " + trackingId + " can be cancelled only while BOOKED.");
                return false;
            }
            this.status = ParcelStatus.CANCELLED;
            System.out.println("Parcel " + trackingId + " has been cancelled.");
            notifySubscribers();
            return true;
        }
        private boolean isValidTransition(ParcelStatus current, ParcelStatus next) {
            switch (current) {
                case BOOKED:
                    return next == ParcelStatus.PICKED_UP;
                case PICKED_UP:
                    return next == ParcelStatus.IN_TRANSIT;
                case IN_TRANSIT:
                    return next == ParcelStatus.OUT_FOR_DELIVERY;
                case OUT_FOR_DELIVERY:
                    return next == ParcelStatus.DELIVERED;
                default:
                    return false;
            }
        }
        public String getTrackingId() {
            return trackingId;
        }
        public double getWeightKg() {
            return weightKg;
        }
        public ShippingType getShippingType() {
            return shippingType;
        }
        public double getCharge() {
            return charge;
        }
        public ParcelStatus getStatus() {
            return status;
        }
    }
    public static class Customer {
        private final String name;
        public Customer(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public Parcel bookParcel(String trackingId, double weightKg, ShippingType shippingType, List<NotificationChannel> channels) {
            Parcel parcel = new Parcel(trackingId, weightKg, shippingType);
            for (NotificationChannel ch : channels) {
                parcel.subscribeChannel(ch);
            }
            System.out.printf(Locale.US, "Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n",
                trackingId, shippingType.getTypeName(), weightKg, parcel.getCharge());
            parcel.notifySubscribers();
            return parcel;
        }
    }
    public static void main(String[] args) {
        Customer customer = new Customer("Rahul");
        List<NotificationChannel> channels = Arrays.asList(new SmsChannel(), new EmailChannel());
        Parcel parcel = customer.bookParcel("P101", 2.0, new ExpressShipping(), channels);
        parcel.moveToStatus(ParcelStatus.PICKED_UP);
        parcel.cancel();
        parcel.moveToStatus(ParcelStatus.IN_TRANSIT);
        parcel.moveToStatus(ParcelStatus.DELIVERED);
    }
}
