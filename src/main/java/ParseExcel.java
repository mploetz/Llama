import com.google.gson.Gson;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.*;

public class ParseExcel {

    private Map<Integer, Student> studentMap = new HashMap<Integer, Student>();

    // @params: Cell cell
    // @returns: Object
    // Gets the value of cell in the excel sheet
    private Object GetCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }

        return null;
    }

    // @params: String excelFilePath
    // Takes a excel file path that contains basic student information and creates entries in the table
    public void ReadStudentsFromExcelFile(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Student student = new Student();

            // Iterate the through the row
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int colIndex = nextCell.getColumnIndex();

                switch (colIndex) {
                    case 0:
                        if (GetCellValue(nextCell) instanceof String) {
                            break;
                        }
                        student.SetStudentId((Double) GetCellValue(nextCell));
                        break;
                    case 1:
                        if (GetCellValue(nextCell) instanceof String) {
                            student.SetMajor((String) GetCellValue(nextCell));
                            break;
                        } else if (GetCellValue(nextCell) instanceof Double) {
                            student.SetScore((Double) GetCellValue(nextCell));
                        }
                    case 2:
                        student.SetGender((String) GetCellValue(nextCell));
                        break;
                }
            }
            studentMap.put(student.GetStudentId(), student);
        }

        workbook.close();
        inputStream.close();
    }

    // @params: String excelFilePath
    // Takes an excel file that contains a student id and test score and updates the students score in the table based on highest value
    public void ReadAndUpdateTestScores(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        // Go through the sheet
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            final Student student = new Student();

            // Iterate the through the row
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int colIndex = nextCell.getColumnIndex();

                switch (colIndex) {
                    case 0:
                        if (GetCellValue(nextCell) instanceof String) {
                            break;
                        }
                        student.SetStudentId((Double) GetCellValue(nextCell));
                        break;
                    case 1:
                        if (GetCellValue(nextCell) instanceof String) { // skip the header
                            break;
                        } else if (GetCellValue(nextCell) instanceof Double) {
                            student.SetScore((Double) GetCellValue(nextCell));
                        }
                }
                Student currentStudent = studentMap.get(student.GetStudentId());
                if (currentStudent.GetTestScore() < student.GetTestScore()) {
                    currentStudent.SetScore(student.GetTestScore());
                    studentMap.put(student.GetStudentId(), currentStudent);
                }

            }

            workbook.close();
            inputStream.close();

        }
    }

    public static void main(String[] args) throws IOException {
        String excelFilePath = System.getProperty("user.dir") + "\\src\\Data";
        ParseExcel parser = new ParseExcel();
        parser.ReadStudentsFromExcelFile(excelFilePath + "\\Student Info.xlsx");
        parser.ReadAndUpdateTestScores(excelFilePath + "\\Test Scores.xlsx");
        parser.ReadAndUpdateTestScores(excelFilePath + "\\Test Retake Scores.xlsx");

        StudentCalculations calcs = new StudentCalculations(parser.studentMap);
        double classAverage = calcs.GetAverageOfStudentsTestScores();

        List<Integer> femaleCSStudents = calcs.GetAllFemaleCSStudents();
        // Sort the student ids from smallest to largest
        Collections.sort(femaleCSStudents);
        ArrayList<String> femaleCSStudentsString = new ArrayList<String>();
        for (Integer i : femaleCSStudents) {
            femaleCSStudentsString.add(String.valueOf(i));
        }
        String jsonFemaleCSStudentsArray = new Gson().toJson(femaleCSStudentsString.toArray());
        // Make request to send data
        // Sending JSON data
        /*
            Key	Value
            "id"	Your email address
            "name"	Your name
            "average"	The class average. Round to the nearest whole number. Make sure this is sent as an integer.
            "studentIds"	The array of IDs (as strings) corresponding to female computer science majors, sorted from smallest to largest (e.g., an ID of "11000" should be before an ID of "12000")
         */
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://3.86.140.38:5000/challenge");
        Gson gson = new Gson();
        GsonData data = new GsonData(classAverage, femaleCSStudentsString);
        StringEntity postingString = new StringEntity(gson.toJson(data));
        request.setEntity(postingString);
        request.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());

    }
}
