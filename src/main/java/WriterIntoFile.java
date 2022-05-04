import format_helper.OutputFileFormatter;
import model.Department;
import model.Employee;
import model.Transfer;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WriterIntoFile {

    private OutputFileFormatter formatter;

    public WriterIntoFile() {
        this.formatter = new OutputFileFormatter();
    }

    public void outputInFileBenefitEmployeesTransfersBetweenDepartments(String outputPath, List<Department> departments) throws IOException {
        try(FileWriter f = new FileWriter(outputPath)) {
            f.write(getAllBenefitTransfersBetweenDepartments(departments));
        }
    }

    private String getAllBenefitTransfersBetweenDepartments(List<Department> departmentList) {
        StringBuilder result = new StringBuilder();
        for (Department departmentFrom : departmentList) {
            for (Department departmentTo : departmentList) {
                result.append(getAllBenefitTransfersBetweenTwoDepartments(departmentFrom, departmentTo,0,
                        new ArrayList<>()));
            }
        }
        return result.toString();
    }

    private String getAllBenefitTransfersBetweenTwoDepartments(Department departmentFrom,
                                                               Department departmentTo,
                                                               int startingPosition,
                                                               List<Employee> list){
        StringBuilder result = new StringBuilder();
        for (int i = startingPosition; i < departmentFrom.getEmployees().size(); i++) {
            List<Employee> currentList = new ArrayList<>(list);
            currentList.add(departmentFrom.getEmployees().get(i));
            Department department = new Department();
            department.addAllEmployees(currentList);
            if ((department.getAverageSalary()).compareTo(departmentFrom.getAverageSalary()) < 0) {
                if(isAverageSalaryAfterAddingEmployeesIncreased(currentList, departmentFrom, departmentTo)) {
                    Transfer currentTransfer = new Transfer(currentList, departmentFrom, departmentTo);
                    result.append(currentTransfer.getTransferInfo());
                }
            }
            result.append(getAllBenefitTransfersBetweenTwoDepartments(departmentFrom, departmentTo, i + 1,
                    currentList));
        }
        return result.toString();
    }

    private boolean isAverageSalaryAfterAddingEmployeesIncreased(List<Employee> list, Department departmentFrom, Department departmentTo) {
        BigDecimal totalSalaryInCurrentEmployeesCombination = list.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal totalSalaryInDepartmentToAfterTransfer = departmentTo.getTotalSalary().add(totalSalaryInCurrentEmployeesCombination);
        BigDecimal averageSalaryInDepartmentToAfterTransfer = totalSalaryInDepartmentToAfterTransfer.divide(BigDecimal.valueOf(departmentTo.getEmployees().size() + list.size()), 2, RoundingMode.HALF_UP);
        return departmentTo.getAverageSalary().compareTo(averageSalaryInDepartmentToAfterTransfer) < 0;
    }
}
