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
        return "Из отдела " +
                departmentFrom +
                " в отдел " +
                departmentTo +
                " с увеличением средней зп в обоих отделах возможен перевод следующих сотрудников: \n" +
                employees.toString().replaceAll("[\\[\\]]", "") +
                "\n" +
                "Зп до перевода в " +
                departmentFrom +
                " отделе: " +
                averageSalaryDepartmentFromBeforeTransfer +
                ". Зп до перевода в " +
                departmentTo +
                " отделе: " +
                averageSalaryDepartmentToBeforeTransfer +
                ". \nЗп после перевода в " +
                departmentFrom +
                " отделе: " +
                averageSalaryDepartmentFromAfterTransfer +
                ". Зп после перевода в " +
                departmentTo +
                " отделе: " +
                averageSalaryDepartmentToAfterTransfer +
                "\n\n";
    }
}
