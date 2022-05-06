package format_helper;

import model.Employee;

import java.math.BigDecimal;
import java.util.List;

public class ReadFromFileFormatter {

    public String formatInfoOutputToConsole(String departmentType, BigDecimal averageSalary, List<Employee> employeesInDepartment) {
        return "\nДля отдела " +
                departmentType +
                " средняя зп: " +
                averageSalary +
                "\n" +
                employeesInDepartment.toString().replaceAll("[\\[\\]]", "");
    }

    public boolean checkEmployeesInfoStringFormAtVersionTwo(String[] line, int lineNumber) {
        if(line.length < 4) {
            System.out.println("Не хватает информации о сотруднике на строке " + lineNumber);
            return false;
        }
        return checkInitialsAndDepartmentFormat(line, lineNumber) & checkSalaryFormat(line[line.length - 1], lineNumber);
    }

    private boolean checkInitialsAndDepartmentFormat(String[] line, int lineNumber) {
        boolean isCorrectInitialsAndDepartmentFormat = true;
        for(int i = 0; i < line.length - 1; i++) {
            if (line[i].isBlank()) {
                isCorrectInitialsAndDepartmentFormat = false;
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
        return isCorrectInitialsAndDepartmentFormat;
    }

    private boolean checkSalaryFormat(String salary, int lineNumber) {
        boolean isCorrectSalaryFormat = true;
        try {
            BigDecimal currentBigDecimal = new BigDecimal(salary);
            if (currentBigDecimal.scale() > 2) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            isCorrectSalaryFormat = false;
            System.out.println("Неверно указана зарплата сотрудника на строке " + lineNumber);
        }
        return isCorrectSalaryFormat;
    }
}
