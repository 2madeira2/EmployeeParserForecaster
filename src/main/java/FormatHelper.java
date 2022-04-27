import models.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class FormatHelper {
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

    public boolean checkEmployeesInfoStringFormat(String line) {
        return Pattern.matches("[A-ZА-Я][a-zа-я]*\\s[A-ZА-Я][a-zа-я]*\\s[A-ZА-Я][a-zа-я]*\\s[1-9]\\d*\\.?\\d+\\s[A-ZА-Я][A-ZА-Яa-zа-я]*", line);
    }

    public String formatInfoOutputToConsole(String departmentType, BigDecimal averageSalary, List<Employee> employeesInDepartment) {
        StringBuilder output = new StringBuilder();
        output.append("Для отдела ")
              .append(departmentType)
              .append(" средняя зп: ")
              .append(averageSalary)
              .append("\n")
              .append(employeesInDepartment.toString().replaceAll("[\\[\\]]", ""));
        return output.toString();
    }

    public String getMessageAboutFormattingError(int lineNumber) {
        return "Некорректно задана информация о сотруднике на строке номер: " + lineNumber;
    }

}
