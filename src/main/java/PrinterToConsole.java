import format_helper.ReadFromFileFormatter;
import model.Department;
import java.util.List;

public class PrinterToConsole {
    private ReadFromFileFormatter formatter;

    public PrinterToConsole() {
        this.formatter = new ReadFromFileFormatter();
    }

    public void printToConsoleDepartmentsInfo(List<Department> departmentsList) {
        for(Department department : departmentsList) {
            System.out.println(formatter.formatInfoOutputToConsole(department.getDepartmentName(),
                    department.getAverageSalary(),
                    department.getEmployees()));
        }
    }

}
