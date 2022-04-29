package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {

    private List<Employee> employees;
    private String departmentName;

    public Department(String departmentType) {
        this.departmentName = departmentType;
        this.employees = new ArrayList<>();
    }

    public Department() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public String getDepartmentType() {
        return departmentName;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addAllEmployees(List<Employee> list) {
        employees.addAll(list);
    }

    public BigDecimal getAverageSalary() {
        if(employees.isEmpty()) return BigDecimal.ZERO;
        BigDecimal sum = new BigDecimal("0");
        for(Employee e : employees) {
            sum = sum.add(e.getSalary());
        }
        return sum.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

}
