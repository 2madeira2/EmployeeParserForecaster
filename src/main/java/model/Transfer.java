package model;

import format_helper.OutputFileFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Transfer {

    private List<Employee> employees;
    private Department departmentFrom;
    private Department departmentTo;
    private OutputFileFormatter formatter;

    public Transfer(List<Employee> employees, Department departmentFrom, Department departmentTo) {
        this.employees = employees;
        this.departmentFrom = departmentFrom;
        this.departmentTo = departmentTo;
        this.formatter = new OutputFileFormatter();
    }

    private BigDecimal getAverageSalaryInDepartmentToAfterTransfer() {
        return employees.stream()
                        .map(Employee::getSalary)
                        .reduce(departmentTo.getAverageSalary(), BigDecimal::add);
    }

    private BigDecimal getAverageSalaryInDepartmentFromAfterTransfer() {
        BigDecimal totalSalaryInCurrentEmployeesCombination = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal totalSalaryInDepartmentFromAfterTransfer = departmentFrom.getTotalSalary().subtract(totalSalaryInCurrentEmployeesCombination);
        return totalSalaryInDepartmentFromAfterTransfer.divide(BigDecimal.valueOf(departmentFrom.getEmployees().size() - employees.size()), 2, RoundingMode.HALF_UP);
    }

    public String getTransferInfo() {
        StringBuilder result = new StringBuilder();
        return result.append(formatter.formatStringForOutputInFile(departmentFrom.getDepartmentName(),
                departmentTo.getDepartmentName(),
                employees,
                departmentFrom.getAverageSalary(),
                departmentTo.getAverageSalary(),
                getAverageSalaryInDepartmentFromAfterTransfer(),
                getAverageSalaryInDepartmentToAfterTransfer()
        )).toString();
    }
}
