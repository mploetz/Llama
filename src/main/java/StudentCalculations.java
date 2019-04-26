import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentCalculations {

    private Map<Integer, Student> students;
    public StudentCalculations(Map<Integer, Student> students) {
        this.students = students;
    }

    public double GetAverageOfStudentsTestScores() {
        int totalOfScores = 0;
        int totalStudents = this.students.size();
        for (Map.Entry<Integer, Student> student : this.students.entrySet()) {
            totalOfScores += student.getValue().GetTestScore();
        }

        return totalOfScores / totalStudents;
    }

    public List<Integer> GetAllFemaleCSStudents() {
        List<Integer> femaleCSStudents = new ArrayList<Integer>();
        for (Map.Entry<Integer, Student> student : this.students.entrySet()) {
            Student currentStudent = student.getValue();
            if (currentStudent.GetGender().equals("F") && currentStudent.GetMajor().equals("computer science")) {
                femaleCSStudents.add(currentStudent.GetStudentId());
            }
        }

        return femaleCSStudents;
    }
}
