// Representation of a Student
public class Student {
    private int StudentId;
    private String Major;
    private String Gender;
    private int TestScore;

    public Student() {
        this.TestScore = 0;
    }

    public Student(Integer studentId, String Major, String Gender, int testScores) {
        this.Gender = Gender;
        this.Major = Major;
        this.StudentId = studentId;
        this.TestScore = testScores;
    }

    // Format the output of a Student
    public String toString() {
        return String.format("%d - %s - %s - %d", StudentId, Major, Gender, TestScore);
    }

    public int GetTestScore() {
        return this.TestScore;
    }

    public int GetStudentId() {
        return this.StudentId;
    }

    public void SetStudentId(double studentId) {
        this.StudentId = (int) studentId;
    }

    public void SetMajor(String Major) {
        this.Major = Major;
    }

    public void SetGender(String Gender) {
        this.Gender = Gender;
    }

    public void SetScore(double score) {
        this.TestScore = (int) score;
    }

    public String GetMajor() {
        return this.Major;
    }

    public String GetGender() {
        return this.Gender;
    }
}
