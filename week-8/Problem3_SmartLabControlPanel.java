import java.util.*;

public class Problem3_SmartLabControlPanel {
    public interface Capability {
        String getCapabilityName();
    }
    public static class PowerCapability implements Capability {
        private boolean on = false;
        @Override
        public String getCapabilityName() {
            return "Power";
        }
        public boolean isOn() {
            return on;
        }
        public String setPower(boolean state) {
            this.on = state;
            return state ? "ON" : "OFF";
        }
    }
    public static class BrightnessCapability implements Capability {
        private int brightness = 100;
        @Override
        public String getCapabilityName() {
            return "Brightness";
        }
        public int getBrightness() {
            return brightness;
        }
        public boolean setBrightness(int level) {
            if (level < 0 || level > 100) {
                return false;
            }
            this.brightness = level;
            return true;
        }
    }
    public static class TemperatureCapability implements Capability {
        private int temperature = 24;
        @Override
        public String getCapabilityName() {
            return "Temperature";
        }
        public int getTemperature() {
            return temperature;
        }
        public boolean setTemperature(int temp) {
            if (temp < 16 || temp > 30) {
                return false;
            }
            this.temperature = temp;
            return true;
        }
    }
    public static class Device {
        private final String name;
        private final Map<String, Capability> capabilities;
        public Device(String name) {
            this.name = name;
            this.capabilities = new LinkedHashMap<>();
        }
        public String getName() {
            return name;
        }
        public void addCapability(Capability capability) {
            capabilities.put(capability.getCapabilityName(), capability);
        }
        public boolean hasCapability(String name) {
            return capabilities.containsKey(name);
        }
        @SuppressWarnings("unchecked")
        public <T extends Capability> T getCapability(String name) {
            return (T) capabilities.get(name);
        }
        public Collection<Capability> getCapabilities() {
            return capabilities.values();
        }
    }
    public interface SceneStep {
        int apply(List<Device> devices);
    }
    public static class PowerStep implements SceneStep {
        private final boolean state;
        public PowerStep(boolean state) {
            this.state = state;
        }
        @Override
        public int apply(List<Device> devices) {
            int count = 0;
            for (Device d : devices) {
                if (d.hasCapability("Power")) {
                    PowerCapability pc = d.getCapability("Power");
                    String status = pc.setPower(state);
                    System.out.println(d.getName() + ": " + status + ".");
                    count++;
                }
            }
            return count;
        }
    }
    public static class BrightnessStep implements SceneStep {
        private final int level;
        public BrightnessStep(int level) {
            this.level = level;
        }
        @Override
        public int apply(List<Device> devices) {
            int count = 0;
            for (Device d : devices) {
                if (d.hasCapability("Brightness")) {
                    BrightnessCapability bc = d.getCapability("Brightness");
                    if (bc.setBrightness(level)) {
                        System.out.println(d.getName() + ": brightness set to " + level + "%.");
                        count++;
                    }
                }
            }
            return count;
        }
    }
    public static class TemperatureStep implements SceneStep {
        private final int temp;
        public TemperatureStep(int temp) {
            this.temp = temp;
        }
        @Override
        public int apply(List<Device> devices) {
            int count = 0;
            for (Device d : devices) {
                if (d.hasCapability("Temperature")) {
                    TemperatureCapability tc = d.getCapability("Temperature");
                    if (tc.setTemperature(temp)) {
                        System.out.println(d.getName() + ": temperature set to " + temp + "°C.");
                        count++;
                    }
                }
            }
            return count;
        }
    }
    public static class Scene {
        private final String name;
        private final List<SceneStep> steps;
        public Scene(String name) {
            this.name = name;
            this.steps = new ArrayList<>();
        }
        public void addStep(SceneStep step) {
            steps.add(step);
        }
        public void execute(List<Device> devices) {
            System.out.println("Scene '" + name + "' started.");
            int totalActions = 0;
            for (SceneStep step : steps) {
                totalActions += step.apply(devices);
            }
            System.out.println("Scene '" + name + "' completed: " + totalActions + " actions applied.");
        }
    }
    public static void main(String[] args) {
        Device labAc = new Device("Lab AC");
        labAc.addCapability(new PowerCapability());
        labAc.addCapability(new TemperatureCapability());
        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());
        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());
        List<Device> devices = Arrays.asList(labAc, ceilingLights, projector);
        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep(new PowerStep(true));
        lectureMode.addStep(new BrightnessStep(40));
        lectureMode.addStep(new TemperatureStep(24));
        lectureMode.execute(devices);
        TemperatureCapability acTemp = labAc.getCapability("Temperature");
        if (acTemp != null && !acTemp.setTemperature(12)) {
            System.out.println("Rejected: Lab AC temperature must be between 16°C and 30°C.");
        }
        BrightnessCapability projectorBrightness = new BrightnessCapability();
        projector.addCapability(projectorBrightness);
        System.out.println("Projector: Brightness capability added.");
        if (projectorBrightness.setBrightness(70)) {
            System.out.println("Projector: brightness set to 70%.");
        }
    }
}
