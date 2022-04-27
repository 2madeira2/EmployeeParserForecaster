import models.Employee;

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

    private FormatHelper formatter;

    public ParserPrinterToConsole() {
        this.formatter = new FormatHelper();
    }

    public Map<String, List<Employee>> parseAndPrintToConsoleEmployeesInfo(String path) throws IOException {
        Map<String, List<Employee>> employeesGroupingByDepartment = new HashMap<>();
        try(LineNumberReader reader = new LineNumberReader(new FileReader(path))) {
            String line = reader.readLine();
            while(line != null) {
                if(formatter.checkEmployeesInfoStringFormat(line)) {
                    String[] employeeInfo = line.split(" ");
                    if (employeesGroupingByDepartment.containsKey(employeeInfo[4])) {
                        employeesGroupingByDepartment.get(employeeInfo[4]).add(new Employee(employeeInfo[0], employeeInfo[1], employeeInfo[2], new BigDecimal(employeeInfo[3]), employeeInfo[4]));
                    } else {
                        employeesGroupingByDepartment.put(employeeInfo[4], new ArrayList<>(List.of(new Employee(employeeInfo[0], employeeInfo[1], employeeInfo[2], new BigDecimal(employeeInfo[3]), employeeInfo[4]))));
                    }
                } else {
                    System.out.println(formatter.getMessageAboutFormattingError(reader.getLineNumber()));
                }
                line = reader.readLine();
            }
        }
        for(Map.Entry<String, List<Employee>> entry : employeesGroupingByDepartment.entrySet()) {
            BigDecimal sum = new BigDecimal("0");
            for(Employee employee : entry.getValue()) {
                sum = sum.add(employee.getSalary());
            }
            System.out.println(formatter.formatInfoOutputToConsole(entry.getKey(),
                                                                   sum.divide(BigDecimal.valueOf(entry.getValue().size()), 2, RoundingMode.HALF_UP),
                                                                   entry.getValue()));
        }
        return employeesGroupingByDepartment;
    }
}
