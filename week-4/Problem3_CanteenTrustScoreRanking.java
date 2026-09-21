class Canteen {
    private String canteenCode;
    private String canteenName;
    private int trustScore;
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }
    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }
        int codeCompare =
            this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCompare != 0) return codeCompare;
        int lengthCompare =
            Integer.compare(this.canteenName.length(), other.canteenName.length());
        if (lengthCompare != 0) return lengthCompare;
        int exactCodeCompare = this.canteenCode.compareTo(other.canteenCode);
        if (exactCodeCompare != 0) return exactCodeCompare;
        return this.canteenName.compareTo(other.canteenName);
    }
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] result = canteens.clone();
        for (int i = 0; i < result.length - 1; i++) {
            int best = i;
            for (int j = i + 1; j < result.length; j++) {
                if (result[j].compareTo(result[best]) < 0) {
                    best = j;
                }
            }
            Canteen temp = result[i];
            result[i] = result[best];
            result[best] = temp;
        }
        return result;
    }
}
