import java.util.ArrayList;
// This class is used to format into JSON to POST to the server
public class GsonData {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public ArrayList<String> getFemaleCSStudents() {
        return femaleCSStudents;
    }

    public void setFemaleCSStudents(ArrayList<String> femaleCSStudents) {
        this.femaleCSStudents = femaleCSStudents;
    }

    private String id = "mattploetz@yahoo.com";
    private String name = "Matthew Ploetz";
    private Double average;
    private ArrayList<String> femaleCSStudents;

    public GsonData(Double average, ArrayList<String> femaleCSStudents) {
        this.average = average;
        this.femaleCSStudents = femaleCSStudents;
    }
}
