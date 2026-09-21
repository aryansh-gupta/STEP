public class Problem1_MembershipFieldReach {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) return "ALLOWED";
        if ("SAME_CLASS".equals(accessorContext)) return "ALLOWED";
        if ("SAME_PACKAGE".equals(accessorContext)) {
            return "private".equals(fieldModifier) ? "DENIED" : "ALLOWED";
        }
        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        int[] allowed = new int[4];
        int[] denied = new int[4];

        for (String[] attempt : attempts) {
            String mod = attempt[0];
            String ctx = attempt[1];
            String result = classifyAccess(mod, ctx);
            for (int i = 0; i < 4; i++) {
                if (modifiers[i].equals(mod)) {
                    if ("ALLOWED".equals(result)) allowed[i]++;
                    else denied[i]++;
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(modifiers[i]).append(": ").append(allowed[i]).append(" allowed / ").append(denied[i]).append(" denied");
            if (i < 3) sb.append(" | ");
        }
        return sb.toString();
    }

    static class LibraryMember {
        private String membershipId;
        protected String branchCode;
        double finesOwed;
        public String displayName;

        private LibraryMember() {}

        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null || membershipId.trim().isEmpty() || membershipId.trim().length() < 4) {
                throw new IllegalArgumentException("membershipId must not be blank and must be at least 4 characters");
            }
            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected as expected: " + e.getMessage());
        }
    }
}
