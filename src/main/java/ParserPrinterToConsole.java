import format_helper.ReadFromFileFormatter;
import model.Department;
import model.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserPrinterToConsole {

    private ReadFromFileFormatter formatter;

    public ParserPrinterToConsole() {
        this.formatter = new ReadFromFileFormatter();
    }

    public List<Department> parseAndPrintToConsoleEmployeesInfo(String path) throws IOException {
        Map<String, Department> departmentMap = new HashMap<>();
        try(LineNumberReader reader = new LineNumberReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null) {
                if(formatter.checkEmployeesInfoStringFormat(line)) {
                    String[] employeeInfo = line.split(" ");
                    if (departmentMap.containsKey(employeeInfo[4])) {
                        departmentMap.get(employeeInfo[4]).addEmployee(new Employee(employeeInfo[0], employeeInfo[1], employeeInfo[2], new BigDecimal(employeeInfo[3]), employeeInfo[4]));
                    } else {
                        Department department = new Department(employeeInfo[4]);
                        department.addEmployee(new Employee(employeeInfo[0], employeeInfo[1], employeeInfo[2], new BigDecimal(employeeInfo[3]), employeeInfo[4]));
                        departmentMap.put(employeeInfo[4], department);
                    }
                } else {
                    System.out.println(formatter.getMessageAboutFormattingError(reader.getLineNumber()));
                }
                line = reader.readLine();
            }
        }
        List<Department> departmentsList = new ArrayList<>(departmentMap.values());
        for(Department department : departmentsList) {
            BigDecimal sum = new BigDecimal("0");
            for(Employee employee : department.getEmployees()) {
                sum = sum.add(employee.getSalary());
            }
            System.out.println(formatter.formatInfoOutputToConsole(department.getDepartmentType(),
                                                                   sum.divide(BigDecimal.valueOf(department.getEmployees().size()), 2, RoundingMode.HALF_UP),
                                                                   department.getEmployees()));
        }
        return departmentsList;
    }
}
