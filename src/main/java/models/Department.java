package models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private List<Employee> employees;

    private String departmentType;

    public Department(String departmentType) {
        this.departmentType = departmentType;
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public BigDecimal getAverageSalary() {
        BigDecimal sum = new BigDecimal("0");
        for(Employee e : employees) {
            sum = sum.add(e.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

}
