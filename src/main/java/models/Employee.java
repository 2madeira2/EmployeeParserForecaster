package models;

public class Employee {

    private final String surname;
    private Long salary; // может быть BigDecimal, пока что оставлю так
    private Integer departmentId;

    public Employee(String surname, long salary, int id) {
        this.surname = surname;
        this.departmentId = id;
        this.salary = salary;
    }

    public String getSurname() {
        return surname;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int id) {
        this.departmentId = id;
    }

    @Override
    public String toString() {
        return "Работник " + surname + " с зарплатой " + salary;
    }
}
