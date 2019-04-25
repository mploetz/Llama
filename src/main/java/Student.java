public class Student {
    private int StudentId;
    private String Major;
    private String Gender;
    private int TestScore;

    public Student() {

    }

    public Student(Integer studentId, String Major, String Gender, int testScores) {
        this.Gender = Gender;
        this.Major = Major;
        this.StudentId = studentId;
        this.TestScore = testScores;
    }

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
        if (this.TestScore < (int) score) {
            this.TestScore = (int) score;
        }
    }
}
