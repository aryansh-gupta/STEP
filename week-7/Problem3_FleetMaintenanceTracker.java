public class Problem3_FleetMaintenanceTracker {
    public interface Insurable {
        String getInsuranceInfo();
    }
    public static abstract class ServiceableVehicle {
        private double mileage;
        public ServiceableVehicle() {
            this.mileage = 0.0;
        }
        public double getMileage() {
            return mileage;
        }
        public void addMileage(double km) {
            if (km < 0) {
                throw new IllegalArgumentException("Distance to add must be zero or positive");
            }
            this.mileage += km;
        }
        public abstract String performMaintenance();
    }
    public static class Forklift extends ServiceableVehicle implements Insurable {
        protected final String assetTag;
        public Forklift(String assetTag) {
            if (assetTag == null || assetTag.trim().isEmpty()) {
                throw new IllegalArgumentException("Asset tag cannot be blank");
            }
            this.assetTag = assetTag.trim();
        }
        public String getAssetTag() {
            return assetTag;
        }
        @Override
        public String performMaintenance() {
            return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
        }
        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + assetTag;
        }
    }
    public static class HeavyDutyForklift extends Forklift {
        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }
        @Override
        public String performMaintenance() {
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }
    public static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            return ((Insurable) v).getInsuranceInfo();
        }
        return "No insurance record exists";
    }
    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Forklift mileage: " + f.getMileage());
        System.out.println(f.performMaintenance());
        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());
        System.out.println(getInsuranceIfApplicable(f));
        System.out.println(getInsuranceIfApplicable(hd));
    }
}
