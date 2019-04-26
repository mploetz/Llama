import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Make calculations of all the students
public class StudentCalculations {

    private Map<Integer, Student> students;
    public StudentCalculations(Map<Integer, Student> students) {
        this.students = students;
    }

    // @returns: Double of the average test score of a Student
    // Computes the Average Test Score of all the Students
    public double GetAverageOfStudentsTestScores() {
        int totalOfScores = 0;
        int totalStudents = this.students.size();
        for (Map.Entry<Integer, Student> student : this.students.entrySet()) {
            totalOfScores += student.getValue().GetTestScore();
        }

        return totalOfScores / totalStudents;
    }

    // @returns: List<Integer> of female computer science Students Id's
    // Gives a List of all Female Computer Science Students Id's
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
