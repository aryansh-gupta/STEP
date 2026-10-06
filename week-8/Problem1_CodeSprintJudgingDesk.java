import java.util.*;

public class Problem1_CodeSprintJudgingDesk {
    public interface ScoringTrack {
        String getTrackName();
        double calculateFinalScore(double idea, double execution, double presentation);
    }
    public static class InnovationTrack implements ScoringTrack {
        @Override
        public String getTrackName() {
            return "Innovation";
        }
        @Override
        public double calculateFinalScore(double idea, double execution, double presentation) {
            return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
        }
    }
    public static class OpenTrack implements ScoringTrack {
        @Override
        public String getTrackName() {
            return "Open";
        }
        @Override
        public double calculateFinalScore(double idea, double execution, double presentation) {
            return (idea + execution + presentation) / 3.0;
        }
    }
    public static class Student {
        private final String name;
        public Student(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Student name cannot be blank");
            }
            this.name = name.trim();
        }
        public String getName() {
            return name;
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
    public static class Project {
        private final String title;
        private Score score;
        public Project(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
        public Score getScore() {
            return score;
        }
        public void setScore(Score score) {
            this.score = score;
        }
    }
    public static class Score {
        private double idea;
        private double execution;
        private double presentation;
        private double finalScore;
        public Score(double idea, double execution, double presentation, ScoringTrack track) {
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
            this.finalScore = track.calculateFinalScore(idea, execution, presentation);
        }
        public double getFinalScore() {
            return finalScore;
        }
        public void updateScore(double idea, double execution, double presentation, ScoringTrack track) {
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
            this.finalScore = track.calculateFinalScore(idea, execution, presentation);
        }
    }
    public static class Team {
        private final String teamName;
        private final List<Student> members;
        private final ScoringTrack track;
        private Project project;
        public Team(String teamName, List<Student> members, ScoringTrack track) {
            this.teamName = teamName;
            this.members = new ArrayList<>(members);
            this.track = track;
        }
        public String getTeamName() {
            return teamName;
        }
        public List<Student> getMembers() {
            return Collections.unmodifiableList(members);
        }
        public ScoringTrack getTrack() {
            return track;
        }
        public Project getProject() {
            return project;
        }
        public void setProject(Project project) {
            this.project = project;
        }
    }
    public enum HackathonState {
        OPEN, JUDGING, PUBLISHED
    }
    public static class Hackathon {
        private final String name;
        private HackathonState state;
        private final Map<String, Team> teams;
        private final Set<Student> registeredStudents;
        public Hackathon(String name) {
            this.name = name;
            this.state = HackathonState.OPEN;
            this.teams = new HashMap<>();
            this.registeredStudents = new HashSet<>();
        }
        public boolean registerTeam(String teamName, List<Student> members, ScoringTrack track) {
            if (members == null || members.size() < 2 || members.size() > 4) {
                System.out.println("Registration failed: A team must have 2 to 4 members.");
                return false;
            }
            for (Student s : members) {
                if (registeredStudents.contains(s)) {
                    System.out.println("Registration failed: Student " + s.getName() + " is already in another team.");
                    return false;
                }
            }
            Team team = new Team(teamName, members, track);
            teams.put(teamName, team);
            registeredStudents.addAll(members);
            System.out.println("Team " + teamName + " registered (" + members.size() + " members, " + track.getTrackName() + " track).");
            return true;
        }
        public boolean submitProject(String teamName, String projectTitle) {
            Team team = teams.get(teamName);
            if (team == null) {
                System.out.println("Submission failed: Team " + teamName + " not found.");
                return false;
            }
            if (team.getProject() != null) {
                System.out.println("Submission failed: Team " + teamName + " already submitted a project.");
                return false;
            }
            Project project = new Project(projectTitle);
            team.setProject(project);
            System.out.println("Project '" + projectTitle + "' submitted by " + teamName + ".");
            return true;
        }
        public boolean scoreProject(String projectTitle, double idea, double execution, double presentation) {
            if (state == HackathonState.PUBLISHED) {
                System.out.println("Rescore rejected: Results have already been published.");
                return false;
            }
            Team targetTeam = null;
            for (Team t : teams.values()) {
                if (t.getProject() != null && t.getProject().getTitle().equalsIgnoreCase(projectTitle)) {
                    targetTeam = t;
                    break;
                }
            }
            if (targetTeam == null) {
                System.out.println("Scoring failed: Project '" + projectTitle + "' not found.");
                return false;
            }
            Score score = new Score(idea, execution, presentation, targetTeam.getTrack());
            targetTeam.getProject().setScore(score);
            System.out.printf(Locale.US, "Score recorded for '%s'. Final score: %.2f.%n", projectTitle, score.getFinalScore());
            return true;
        }
        public void publishResults() {
            this.state = HackathonState.PUBLISHED;
            System.out.println("Results published.");
        }
    }
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint 2026");
        List<Student> byteBustersMembers = Arrays.asList(
            new Student("Asha"), new Student("Ravi"), new Student("Neha")
        );
        hackathon.registerTeam("ByteBusters", byteBustersMembers, new InnovationTrack());
        List<Student> soloMembers = Collections.singletonList(new Student("Kiran"));
        hackathon.registerTeam("SoloCoder", soloMembers, new OpenTrack());
        hackathon.submitProject("ByteBusters", "SmartAttend");
        hackathon.scoreProject("SmartAttend", 8, 7, 9);
        hackathon.publishResults();
        hackathon.scoreProject("SmartAttend", 10, 7, 9);
    }
}
