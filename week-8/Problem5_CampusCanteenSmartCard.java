import java.util.*;

public class Problem5_CampusCanteenSmartCard {
    public interface PricingPlan {
        String getPlanName();
        double calculateDiscountedPrice(double basePrice);
    }
    public static class DayScholarPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Day Scholar";
        }
        @Override
        public double calculateDiscountedPrice(double basePrice) {
            return basePrice;
        }
    }
    public static class HostellerPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Hosteller";
        }
        @Override
        public double calculateDiscountedPrice(double basePrice) {
            return basePrice * 0.90;
        }
    }
    public static class StaffPlan implements PricingPlan {
        @Override
        public String getPlanName() {
            return "Staff";
        }
        @Override
        public double calculateDiscountedPrice(double basePrice) {
            return basePrice * 0.80;
        }
    }
    public static class Transaction {
        private final double amount;
        private final String description;
        private final boolean isPurchase;
        private boolean refunded;
        public Transaction(double amount, String description, boolean isPurchase) {
            this.amount = amount;
            this.description = description;
            this.isPurchase = isPurchase;
            this.refunded = false;
        }
        public double getAmount() {
            return amount;
        }
        public String getDescription() {
            return description;
        }
        public boolean isPurchase() {
            return isPurchase;
        }
        public boolean isRefunded() {
            return refunded;
        }
        public void setRefunded(boolean refunded) {
            this.refunded = refunded;
        }
    }
    public static class SmartCard {
        private final String cardId;
        private final PricingPlan plan;
        private boolean isBlocked;
        private final List<Transaction> transactions;
        public SmartCard(String cardId, PricingPlan plan) {
            this.cardId = cardId;
            this.plan = plan;
            this.isBlocked = false;
            this.transactions = new ArrayList<>();
        }
        public String getCardId() {
            return cardId;
        }
        public PricingPlan getPlan() {
            return plan;
        }
        public boolean isBlocked() {
            return isBlocked;
        }
        public void setBlocked(boolean blocked) {
            this.isBlocked = blocked;
        }
        public double getBalance() {
            double sum = 0.0;
            for (Transaction t : transactions) {
                sum += t.getAmount();
            }
            return Math.round(sum * 100.0) / 100.0;
        }
        public boolean topUp(double amount) {
            if (isBlocked) {
                System.out.println("Top-up failed: Card " + cardId + " is blocked.");
                return false;
            }
            if (amount < 100.0) {
                System.out.println("Top-up failed: Minimum top-up amount is ₹100.00.");
                return false;
            }
            if (getBalance() + amount > 5000.0) {
                System.out.println("Top-up failed: Balance cannot exceed ₹5,000.00.");
                return false;
            }
            transactions.add(new Transaction(amount, "Top-up", false));
            System.out.printf(Locale.US, "%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardId, amount, getBalance());
            return true;
        }
        public boolean purchase(String itemName, double basePrice) {
            if (isBlocked) {
                System.out.println("Purchase failed: Card " + cardId + " is blocked.");
                return false;
            }
            double finalPrice = Math.round(plan.calculateDiscountedPrice(basePrice) * 100.0) / 100.0;
            double currentBalance = getBalance();
            if (currentBalance < finalPrice) {
                System.out.printf(Locale.US, "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                    finalPrice, currentBalance);
                return false;
            }
            transactions.add(new Transaction(-finalPrice, itemName, true));
            System.out.printf(Locale.US, "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                itemName, finalPrice, getBalance());
            return true;
        }
        public boolean refund(String itemName) {
            if (isBlocked) {
                System.out.println("Refund failed: Card " + cardId + " is blocked.");
                return false;
            }
            Transaction purchaseTx = null;
            for (int i = transactions.size() - 1; i >= 0; i--) {
                Transaction t = transactions.get(i);
                if (t.isPurchase() && t.getDescription().equalsIgnoreCase(itemName)) {
                    purchaseTx = t;
                    break;
                }
            }
            if (purchaseTx == null) {
                System.out.println("Refund rejected: No purchase found for " + itemName + ".");
                return false;
            }
            if (purchaseTx.isRefunded()) {
                System.out.println("Refund rejected: " + itemName + " has already been refunded.");
                return false;
            }
            double refundAmount = Math.abs(purchaseTx.getAmount());
            if (getBalance() + refundAmount > 5000.0) {
                System.out.println("Refund failed: Account balance would exceed ₹5,000.00.");
                return false;
            }
            purchaseTx.setRefunded(true);
            transactions.add(new Transaction(refundAmount, "Refund: " + itemName, false));
            System.out.printf(Locale.US, "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                refundAmount, itemName, getBalance());
            return true;
        }
        public void printMiniStatement() {
            StringBuilder sb = new StringBuilder();
            sb.append("Mini-statement for ").append(cardId).append(": ");
            for (int i = 0; i < transactions.size(); i++) {
                double amt = transactions.get(i).getAmount();
                if (amt >= 0) {
                    sb.append(String.format(Locale.US, "+%.2f", amt));
                } else {
                    sb.append(String.format(Locale.US, "%.2f", amt));
                }
                if (i < transactions.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(String.format(Locale.US, " = ₹%.2f.", getBalance()));
            System.out.println(sb.toString());
        }
    }
    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500.0);
        card.purchase("Veg Thali", 120.0);
        card.purchase("Cold Coffee", 60.0);
        card.purchase("Assorted Items", 400.0);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.printMiniStatement();
    }
}
