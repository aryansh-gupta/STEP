public class Problem5_ConnectedHomeControlPanel {
    public interface RemoteControllable {
        String connect(String appId);
    }
    public interface EnergyTrackable {
        double getConsumptionWatts();
    }
    public static abstract class HomeDevice {
        private static int counter = 1000;
        protected final String serialNumber;
        public HomeDevice() {
            counter++;
            this.serialNumber = "HD-" + counter;
        }
        public String getSerialNumber() {
            return serialNumber;
        }
        public abstract String activate();
    }
    public static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
        private final double consumptionWatts;
        public WashingMachine(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption watts must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }
        @Override
        public String activate() {
            return "Washing machine " + serialNumber + " started a cycle";
        }
        @Override
        public String connect(String appId) {
            if (appId == null || appId.trim().isEmpty()) {
                throw new IllegalArgumentException("appId cannot be blank");
            }
            return serialNumber + " connected to " + appId.trim();
        }
        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }
    public static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private final double consumptionWatts;
        public Refrigerator(double consumptionWatts) {
            super();
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption watts must be positive");
            }
            this.consumptionWatts = consumptionWatts;
        }
        @Override
        public String activate() {
            return "Refrigerator " + serialNumber + " started cooling cycle";
        }
        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }
    public static class MobileApp implements RemoteControllable {
        private final String appName;
        public MobileApp(String appName) {
            if (appName == null || appName.trim().isEmpty()) {
                throw new IllegalArgumentException("appName cannot be blank");
            }
            this.appName = appName.trim();
        }
        public String getAppName() {
            return appName;
        }
        @Override
        public String connect(String appId) {
            if (appId == null || appId.trim().isEmpty()) {
                throw new IllegalArgumentException("appId cannot be blank");
            }
            return appName + " connected to " + appId.trim();
        }
    }
    public static void connectAll(RemoteControllable[] items, String appId) {
        if (items == null) return;
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }
    public static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            return ((EnergyTrackable) d).getConsumptionWatts();
        }
        return 0.0;
    }
    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println("Fridge consumption: " + getConsumptionIfTrackable(fridge));
        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));
        HomeDevice ref = wm;
        System.out.println("Trackable consumption via HomeDevice reference: " + getConsumptionIfTrackable(ref));
        System.out.println("Connecting all remote-controllable items:");
        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}
