package format_helper;

import model.Employee;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public boolean checkEmployeesInfoStringFormAtVersionTwo(String[] line, int lineNumber) {
        boolean correctFormatEmployeesInfoLine = true;
        if(line.length < 4) {
            System.out.println("Не хватает информации о сотруднике на строке " + lineNumber);
            return false;
        }
        for(int i = 0; i < line.length - 1; i++) {
            if (line[i].isBlank()) {
                correctFormatEmployeesInfoLine = false;
                switch (i) {
                    case 0:
                        System.out.println("Неверно указана фамилия на строке: " + lineNumber);
                        break;
                    case 1:
                        System.out.println("Неверно указано имя на строке: " + lineNumber);
                        break;
                    case 2:
                        if(line.length == 4)
                            System.out.println("Неверно указан отдел на строке: " + lineNumber);
                        else
                            System.out.println("Неверно указано отчество на строке: " + lineNumber);
                        break;
                    case 3:
                        System.out.println("Неверно указано отчество на строке: " + lineNumber);
                }
            }
        }
        try {
            BigDecimal currentBigDecimal = new BigDecimal(line[line.length - 1]);
            if (currentBigDecimal.scale() > 2) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            correctFormatEmployeesInfoLine = false;
            System.out.println("Неверно указана зарплата сотрудника на строке " + lineNumber);
        }
        return correctFormatEmployeesInfoLine;
    }

    public String getMessageAboutFormattingError(int lineNumber) {
        return "!Некорректно задана информация о сотруднике на строке номер: " + lineNumber;
    }
}
