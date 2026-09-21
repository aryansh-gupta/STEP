import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Problem4_LibraryMemberJavaBean {
    public static class LibraryMember {
        private String membershipId;
        private boolean membershipIdLocked = false;
        private String name;
        private boolean premiumMember;
        private String securityAnswerHash;

        public LibraryMember() {
            this(null, null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            if (membershipId != null) {
                this.membershipId = membershipId;
                this.membershipIdLocked = true;
            }
            this.name = name;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            if (!membershipIdLocked) {
                this.membershipId = id;
                this.membershipIdLocked = true;
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            if (answer == null) {
                this.securityAnswerHash = null;
                return;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(answer.getBytes(StandardCharsets.UTF_8));
                StringBuilder hex = new StringBuilder();
                for (byte b : hash) {
                    hex.append(String.format("%02x", b));
                }
                this.securityAnswerHash = hex.toString();
            } catch (Exception e) {
                this.securityAnswerHash = String.valueOf(answer.hashCode());
            }
        }
    }

    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember("Priya Nair");
        System.out.println("m1 ID: " + m1.getMembershipId());

        LibraryMember m2 = new LibraryMember("LIB-8841", "Priya Nair");
        System.out.println("m2 ID: " + m2.getMembershipId());

        LibraryMember m3 = new LibraryMember();
        m3.setMembershipId("LIB-8841");
        m3.setMembershipId("FAKE-0000"); // ignored
        System.out.println("m3 ID: " + m3.getMembershipId());
    }
}
