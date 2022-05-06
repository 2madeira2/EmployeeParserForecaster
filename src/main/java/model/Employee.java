package model;

import java.math.BigDecimal;

public class Employee {

    private String surname;
    private String name;
    private String patronymic;
    private BigDecimal salary;

    public Employee(String surname, String name, String patronymic, BigDecimal salary) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.salary = salary;
    }

    public Employee(String surname, String name, BigDecimal salary) {
        this(surname, name, "", salary);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Работник " + surname + " " + name + " " + patronymic + " с зарплатой " + salary;
    }
}
