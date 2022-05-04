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

    public boolean isBenefitTransfer() {
        return (getAverageSalaryInDepartmentFromAfterTransfer().compareTo(departmentFrom.getAverageSalary()) > 0) &&
                (getAverageSalaryInDepartmentToAfterTransfer().compareTo(departmentTo.getAverageSalary()) > 0);
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

    private BigDecimal getAverageSalaryInDepartmentToAfterTransfer() {
        BigDecimal totalSalaryInDepartmentToAfterTransfer = employees.stream()
                                                                     .map(Employee::getSalary)
                                                                     .reduce(departmentTo.getTotalSalary(), BigDecimal::add);
       return totalSalaryInDepartmentToAfterTransfer.divide(BigDecimal.valueOf(departmentTo.getEmployees().size() + employees.size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAverageSalaryInDepartmentFromAfterTransfer() {
        if (departmentFrom.getEmployees().size() - employees.size() == 0) return BigDecimal.ZERO;
        BigDecimal totalSalaryInCurrentEmployeesCombination = employees.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal totalSalaryInDepartmentFromAfterTransfer = departmentFrom.getTotalSalary().subtract(totalSalaryInCurrentEmployeesCombination);
        return totalSalaryInDepartmentFromAfterTransfer.divide(BigDecimal.valueOf(departmentFrom.getEmployees().size() - employees.size()), 2, RoundingMode.HALF_UP);
    }
}
