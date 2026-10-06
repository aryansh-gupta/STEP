public class Problem4_ArenaBattleSimulator {
    public interface Attackable {
        String attack();
        String attack(String weaponName);
    }
    public interface Defendable {
        String defend();
    }
    public static abstract class GameCharacter {
        private static int counter = 0;
        private final String characterId;
        public GameCharacter() {
            counter++;
            this.characterId = "CHAR-" + counter;
        }
        public String getCharacterId() {
            return characterId;
        }
        public abstract String getSpecialMove();
    }
    public static class Warrior extends GameCharacter implements Attackable, Defendable {
        private final String name;
        public Warrior(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Warrior name cannot be blank");
            }
            this.name = name.trim();
        }
        public String getName() {
            return name;
        }
        @Override
        public String attack() {
            return name + " strikes with a blade";
        }
        @Override
        public String attack(String weaponName) {
            if (weaponName == null || weaponName.trim().isEmpty()) {
                return attack();
            }
            String trimmed = weaponName.trim();
            char first = Character.toLowerCase(trimmed.charAt(0));
            String article = (first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u') ? "an " : "a ";
            return name + " strikes with " + article + trimmed;
        }
        @Override
        public String defend() {
            return name + " raises a shield";
        }
        @Override
        public String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }
    }
    public static class Trap implements Defendable {
        private final String trapType;
        public Trap(String trapType) {
            if (trapType == null || trapType.trim().isEmpty()) {
                throw new IllegalArgumentException("Trap type cannot be blank");
            }
            this.trapType = trapType.trim();
        }
        public String getTrapType() {
            return trapType;
        }
        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }
    public static void resolveDefense(Defendable[] combatants) {
        if (combatants == null) return;
        for (Defendable combatant : combatants) {
            if (combatant != null) {
                System.out.println(combatant.defend());
            }
        }
    }
    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());
        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());
        System.out.println("Resolving mixed defense:");
        resolveDefense(new Defendable[]{w, t});
    }
}
