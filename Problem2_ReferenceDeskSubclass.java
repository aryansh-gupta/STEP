public class Problem2_ReferenceDeskSubclass {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) return "ALLOWED";
        if ("SAME_CLASS".equals(accessorContext)) return "ALLOWED";
        if ("SAME_PACKAGE".equals(accessorContext)) {
            return "private".equals(fieldModifier) ? "DENIED" : "ALLOWED";
        }
        if ("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
            return "protected".equals(fieldModifier) ? "ALLOWED" : "DENIED";
        }
        if ("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE".equals(accessorContext)) {
            return "DENIED";
        }
        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        String[] words = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase());
            }
            if (i < words.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}
