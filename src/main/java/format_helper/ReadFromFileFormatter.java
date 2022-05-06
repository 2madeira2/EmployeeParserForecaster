package format_helper;

import model.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class ReadFromFileFormatter {

    public String formatInfoOutputToConsole(String departmentType, BigDecimal averageSalary, List<Employee> employeesInDepartment) {
        return "\nДля отдела " +
                departmentType +
                " средняя зп: " +
                averageSalary +
                "\n" +
                employeesInDepartment.toString().replaceAll("[\\[\\]]", "");
    }
    public boolean checkEmployeesInfoStringFormat(String line) {
        return Pattern.matches("[A-ZА-Я][a-zа-я]*\\s[A-ZА-Я][a-zа-я]*\\s[A-ZА-Я][a-zа-я]*\\s[1-9]\\d*\\.?\\d+\\s[A-ZА-Я][A-ZА-Яa-zа-я]*", line);
    }

    public String getMessageAboutFormattingError(int lineNumber) {
        return "!Некорректно задана информация о сотруднике на строке номер: " + lineNumber;
    }
}
