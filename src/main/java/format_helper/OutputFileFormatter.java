package format_helper;

import model.Employee;

import java.math.BigDecimal;
import java.util.List;

public class OutputFileFormatter {
    public String formatStringForOutputInFile(String departmentFrom, String departmentTo, List<Employee> employees,
                                              BigDecimal averageSalaryDepartmentFromBeforeTransfer,
                                              BigDecimal averageSalaryDepartmentToBeforeTransfer,
                                              BigDecimal averageSalaryDepartmentFromAfterTransfer,
                                              BigDecimal averageSalaryDepartmentToAfterTransfer) {
        StringBuilder result = new StringBuilder();
        result.append("Из отдела ")
                .append(departmentFrom)
                .append(" в отдел ")
                .append(departmentTo)
                .append(" с увеличением средней зп в обоих отделах возможен перевод следующих сотрудников: \n")
                .append(employees.toString().replaceAll("[\\[\\]]", ""))
                .append("\n")
                .append("Зп до перевода в ")
                .append(departmentFrom)
                .append(" отделе: ")
                .append(averageSalaryDepartmentFromBeforeTransfer)
                .append(". Зп до перевода в ")
                .append(departmentTo)
                .append(" отделе: ")
                .append(averageSalaryDepartmentToBeforeTransfer)
                .append(". \nЗп после перевода в ")
                .append(departmentFrom)
                .append(" отделе: ")
                .append(averageSalaryDepartmentFromAfterTransfer)
                .append(". Зп после перевода в ")
                .append(departmentTo)
                .append(" отделе: ")
                .append(averageSalaryDepartmentToAfterTransfer)
                .append("\n\n");
        return result.toString();
    }
}
