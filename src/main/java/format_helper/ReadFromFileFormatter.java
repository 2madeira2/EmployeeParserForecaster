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
        switch (line.length) {
            case 1:
                System.out.println("!Не указаны имя, отчество (опционально), отдел и зарплата сотрудника на строке " + lineNumber);
                return false;
            case 2:
                System.out.println("!Не указаны отчество (опционально), отдел и зарплата сотрудника на строке " + lineNumber);
                return false;
            case 3:
                System.out.println("!Не указана зарплата сотрудника на строке " + lineNumber);
                return false;
        }
        return checkInitialsAndDepartmentFormat(line, lineNumber) & checkSalaryFormat(line[line.length - 1], lineNumber);
    }

    private boolean checkInitialsAndDepartmentFormat(String[] line, int lineNumber) {
        boolean isCorrectInitialsAndDepartmentFormat = true;
        for(int i = 0; i < line.length - 1; i++) {
            if (line[i].isBlank()) {
                isCorrectInitialsAndDepartmentFormat = false;
                printFormatErrorInformation(i, lineNumber, line.length);
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
            System.out.println("!Неверно указана зарплата сотрудника на строке " + lineNumber);
        }
        return isCorrectSalaryFormat;
    }

    private void printFormatErrorInformation(int i, int lineNumber, int lineLength) {
        switch (i) {
            case 0:
                printErrorFormatMessage("фамилии", lineNumber);
                break;
            case 1:
                printErrorFormatMessage("имени", lineNumber);
                break;
            case 2:
                if (lineLength == 4)
                    printErrorFormatMessage("отдела", lineNumber);
                else
                    printErrorFormatMessage("отчества", lineNumber);
                break;
            case 3:
                printErrorFormatMessage("отчества", lineNumber);
        }
    }

    private void printErrorFormatMessage(String what, int lineNumber) {
        System.out.println("!Неверный формат " + what + " на строке: " + lineNumber);
    }
}
