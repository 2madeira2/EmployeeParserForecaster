import formathelper.ReadFromFileFormatter;
import model.Department;
import model.Employee;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ParserFromFile {

    private ReadFromFileFormatter formatter;

    public ParserFromFile() {
        this.formatter = new ReadFromFileFormatter();
    }

    public Map<String, Department> parseFromFileDepartmentsInfo(String path) throws IOException {
        Map<String, Department> departmentMap = new HashMap<>();
        try (LineNumberReader reader = new LineNumberReader(new FileReader(path))) {
            String line = reader.readLine();
            while (line != null) {
                String[] employeeInfo = line.split(";");
                if(formatter.checkEmployeesInfoStringFormAtVersionTwo(employeeInfo, reader.getLineNumber())) {
                    Employee employeeForAddingInDepartment;
                    if (employeeInfo.length == 4) {
                        employeeForAddingInDepartment = new Employee(employeeInfo[0], employeeInfo[1], new BigDecimal(employeeInfo[3]));
                    } else {
                        employeeForAddingInDepartment = new Employee(employeeInfo[0], employeeInfo[1], employeeInfo[2], new BigDecimal(employeeInfo[4]));
                    }
                    if (departmentMap.containsKey(employeeInfo[employeeInfo.length - 2])) {
                        departmentMap.get(employeeInfo[employeeInfo.length - 2]).addEmployee(employeeForAddingInDepartment);
                    } else {
                        Department department = new Department(employeeInfo[employeeInfo.length - 2]);
                        department.addEmployee(employeeForAddingInDepartment);
                        departmentMap.put(employeeInfo[employeeInfo.length - 2], department);
                    }
                }
                line = reader.readLine();
            }
        }
        return departmentMap;
    }
}
