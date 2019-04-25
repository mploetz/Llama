import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ParseExcel {

    private Map<Integer, Student> studentMap = new HashMap<Integer, Student>();

    private Object GetCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }

        return null;
    }

    public void ReadStudentsFromExcelFile(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Student student = new Student();

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

    public static void main(String[] args) throws IOException {
        String excelFilePath = System.getProperty("user.dir") + "\\src\\Data";
        ParseExcel parser = new ParseExcel();
        parser.ReadStudentsFromExcelFile(excelFilePath + "\\Student Info.xlsx");
        parser.ReadStudentsFromExcelFile(excelFilePath + "\\Test Scores.xlsx");
        parser.ReadStudentsFromExcelFile(excelFilePath + "\\Test Retake Scores.xlsx");



//        for (int i = 0; i < initStudents.size(); i++) {
//            System.out.println(initStudents.get(i).toString());
//        }

    }
}
