import java.util.*;

public class Problem4_ElectiveSeatRush {
    public interface StudentType {
        String getTypeName();
        int getMaxCredits();
    }
    public static class RegularType implements StudentType {
        @Override
        public String getTypeName() {
            return "Regular";
        }
        @Override
        public int getMaxCredits() {
            return 24;
        }
    }
    public static class HonorsType implements StudentType {
        @Override
        public String getTypeName() {
            return "Honors";
        }
        @Override
        public int getMaxCredits() {
            return 28;
        }
    }
    public static class ExchangeType implements StudentType {
        @Override
        public String getTypeName() {
            return "Exchange";
        }
        @Override
        public int getMaxCredits() {
            return 20;
        }
    }
    public static class Student {
        private final String name;
        private final StudentType type;
        private int currentCredits;
        public Student(String name, StudentType type, int currentCredits) {
            this.name = name;
            this.type = type;
            this.currentCredits = currentCredits;
        }
        public String getName() {
            return name;
        }
        public StudentType getType() {
            return type;
        }
        public int getCurrentCredits() {
            return currentCredits;
        }
        public int getMaxCredits() {
            return type.getMaxCredits();
        }
        public boolean canAddCredits(int credits) {
            return currentCredits + credits <= getMaxCredits();
        }
        public void addCredits(int credits) {
            this.currentCredits += credits;
        }
        public void removeCredits(int credits) {
            this.currentCredits -= credits;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            Student student = (Student) o;
            return name.equalsIgnoreCase(student.name);
        }
        @Override
        public int hashCode() {
            return name.toLowerCase().hashCode();
        }
    }
    public static class Elective {
        private final String name;
        private final int credits;
        private final int capacity;
        private final List<Student> enrolledStudents;
        private final Queue<Student> waitlist;
        public Elective(String name, int credits, int capacity) {
            this.name = name;
            this.credits = credits;
            this.capacity = capacity;
            this.enrolledStudents = new ArrayList<>();
            this.waitlist = new LinkedList<>();
        }
        public String getName() {
            return name;
        }
        public int getCredits() {
            return credits;
        }
        public int getCapacity() {
            return capacity;
        }
        public boolean enroll(Student student) {
            if (enrolledStudents.contains(student) || waitlist.contains(student)) {
                System.out.println("Enrollment failed: " + student.getName() + " is already enrolled or waitlisted for " + name + ".");
                return false;
            }
            if (!student.canAddCredits(credits)) {
                System.out.println("Enrollment failed: " + student.getName() + " would exceed the " +
                    student.getType().getTypeName() + " credit limit (" +
                    (student.getCurrentCredits() + credits) + "/" + student.getMaxCredits() + ").");
                return false;
            }
            if (enrolledStudents.size() < capacity) {
                enrolledStudents.add(student);
                student.addCredits(credits);
                System.out.println(student.getName() + " enrolled in " + name + " (credits: " +
                    student.getCurrentCredits() + "/" + student.getMaxCredits() + ").");
                return true;
            } else {
                waitlist.offer(student);
                System.out.println(name + " is full. " + student.getName() + " added to waitlist (position " + waitlist.size() + ").");
                return false;
            }
        }
        public boolean drop(Student student) {
            if (!enrolledStudents.contains(student)) {
                System.out.println("Drop failed: " + student.getName() + " is not enrolled in " + name + ".");
                return false;
            }
            enrolledStudents.remove(student);
            student.removeCredits(credits);
            System.out.println(student.getName() + " dropped " + name + " (credits: " +
                student.getCurrentCredits() + "/" + student.getMaxCredits() + ").");
            while (!waitlist.isEmpty()) {
                Student candidate = waitlist.poll();
                if (candidate.canAddCredits(credits)) {
                    enrolledStudents.add(candidate);
                    candidate.addCredits(credits);
                    System.out.println(candidate.getName() + " promoted from waitlist and enrolled in " + name +
                        " (credits: " + candidate.getCurrentCredits() + "/" + candidate.getMaxCredits() + ").");
                    break;
                } else {
                    System.out.println("Waitlist promotion skipped for " + candidate.getName() +
                        ": exceeds credit limit.");
                }
            }
            return true;
        }
    }
    public static void main(String[] args) {
        Elective cloudComputing = new Elective("Cloud Computing", 4, 2);
        Student asha = new Student("Asha", new RegularType(), 20);
        cloudComputing.enroll(asha);
        Student ravi = new Student("Ravi", new HonorsType(), 22);
        cloudComputing.enroll(ravi);
        Student neha = new Student("Neha", new ExchangeType(), 12);
        cloudComputing.enroll(neha);
        Student kiran = new Student("Kiran", new RegularType(), 22);
        cloudComputing.enroll(kiran);
        cloudComputing.drop(asha);
    }
}
