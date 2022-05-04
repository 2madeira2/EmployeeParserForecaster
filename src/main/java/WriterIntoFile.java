import format_helper.OutputFileFormatter;
import model.Department;
import model.Employee;

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

    private String generateBenefitEmployeesTransfersBetweenDepartments(List<Department> departmentList) {
        //TODO: подумать о том, что лучше - не создавать Department,
        // а считать зп по департаменту плюс зп сотрудников комбинации делить на department.size + list.size
        // или же все таки создавать в методе новые объекты Department, используя их метод getAverageSalary
        StringBuilder result = new StringBuilder();
        for (Department departmentFrom : departmentList) {
            List<List<Employee>> allVariantsForCurrentDepartment = getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(departmentFrom.getAverageSalary(), 0, departmentFrom.getEmployees(),
                    new ArrayList<>());

            for(Department departmentTo : departmentList) {
                for(List<Employee> list : allVariantsForCurrentDepartment) {
                    BigDecimal totalSalaryInDepartmentTo = new BigDecimal("0");
                    totalSalaryInDepartmentTo = departmentTo.getEmployees().stream()
                                                                           .map(Employee::getSalary)
                                                                           .reduce(BigDecimal::add)
                                                                           .orElseThrow(IllegalArgumentException::new);
                    totalSalaryInDepartmentTo = list.stream()
                                                    .map(Employee::getSalary)
                                                    .reduce(totalSalaryInDepartmentTo, BigDecimal::add);

                    BigDecimal averageSalaryInDepartmentToAfterTransfer = totalSalaryInDepartmentTo.divide(BigDecimal.valueOf(departmentTo.getEmployees().size() + list.size()), 2, RoundingMode.HALF_UP);
                    if(departmentTo.getAverageSalary().compareTo(averageSalaryInDepartmentToAfterTransfer) < 0) {
                        List<Employee> entryListWithRemovingEmployee = new ArrayList<>(departmentFrom.getEmployees());
                        entryListWithRemovingEmployee.removeAll(list);
                        Department departmentFromAfterTransfer = new Department(departmentFrom.getDepartmentType());
                        departmentFromAfterTransfer.addAllEmployees(entryListWithRemovingEmployee);
                        result.append(formatter.formatStringForOutputInFile(departmentFrom.getDepartmentType(), departmentTo.getDepartmentType(), list,
                                departmentFrom.getAverageSalary(),
                                departmentTo.getAverageSalary(),
                                departmentFromAfterTransfer.getAverageSalary(),
                                averageSalaryInDepartmentToAfterTransfer
                        ));
                    }
                }
            }
        }
        return result.toString();
    }

    private List<List<Employee>> getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(BigDecimal averageSalaryInDepAtThisMoment,
                                                                                                           int startingPosition,
                                                                                                           List<Employee> sourceEmployeesListInDep,
                                                                                                           List<Employee> list){
        List<List<Employee>> allEmployeesCombinationsForRemovingFromDepartment = new ArrayList<>();
        for (int i = startingPosition; i < sourceEmployeesListInDep.size(); i++) {
            List<Employee> currentList = new ArrayList<>(list);
            currentList.add(sourceEmployeesListInDep.get(i));
            Department department = new Department();
            department.addAllEmployees(currentList);
            if ((department.getAverageSalary()).compareTo(averageSalaryInDepAtThisMoment) < 0) {
                allEmployeesCombinationsForRemovingFromDepartment.add(currentList);
            }
            allEmployeesCombinationsForRemovingFromDepartment.addAll(getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(averageSalaryInDepAtThisMoment, i + 1,
                    sourceEmployeesListInDep, currentList));
        }
        return allEmployeesCombinationsForRemovingFromDepartment;
    }

    public void outputInFileBenefitEmployeesTransfersBetweenDepartments(String outputPath, List<Department> departments) throws IOException {
        /*TODO: подумать либо о разделении WriterIntoFile на два класса - класс подсчета вариантов и класс
         *TODO: для вывода в файл. Либо комбинации не хранить в памяти, а тут же каждую комбинацию проверять на деле */
        try(FileWriter f = new FileWriter(outputPath)) {
            f.write(generateBenefitEmployeesTransfersBetweenDepartments(departments));
        }
    }

}
