public class Problem2_OneClickDataExport {
    private static int totalExports = 0;
    public interface Exportable {
        String exportData();
    }
    public static synchronized void recordExport() {
        totalExports++;
    }
    public static synchronized int getTotalExports() {
        return totalExports;
    }
    public static class ReportGenerator implements Exportable {
        private final String reportName;
        public ReportGenerator(String reportName) {
            if (reportName == null || reportName.trim().isEmpty()) {
                throw new IllegalArgumentException("Report name cannot be blank");
            }
            this.reportName = reportName.trim();
        }
        public String getReportName() {
            return reportName;
        }
        @Override
        public String exportData() {
            recordExport();
            return "Exported report: " + reportName;
        }
    }
    public static class UserProfile implements Exportable {
        private final String username;
        public UserProfile(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be blank");
            }
            this.username = username.trim();
        }
        public String getUsername() {
            return username;
        }
        @Override
        public String exportData() {
            recordExport();
            return "Exported profile: " + username;
        }
    }
    public static void exportAll(Exportable[] items) {
        if (items == null) return;
        for (Exportable item : items) {
            if (item != null) {
                System.out.println(item.exportData());
            }
        }
    }
    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());
        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());
        System.out.println("Total exports so far: " + getTotalExports());
        Exportable ref = r;
        System.out.println("Running exportAll:");
        exportAll(new Exportable[]{ref, u});
        System.out.println("Total exports now: " + getTotalExports());
    }
}
