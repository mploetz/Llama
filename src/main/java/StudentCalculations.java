import java.util.List;

public class StudentCalculations {

    private List<Student> students;
    public StudentCalculations(List<Student> students) {
        this.students = students;
    }

    private double GetAverageOfStudentsTestScores() {
        int totalOfScores = 0;
        int totalStudents = this.students.size();
        for (int i = 0; i < totalStudents; i++) {
            totalOfScores += this.students.get(i).GetTestScore();
        }

        return totalOfScores / totalStudents;
    }
}
