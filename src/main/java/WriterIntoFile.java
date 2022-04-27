import models.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WriterIntoFile {

    private FormatHelper formatter;

    public WriterIntoFile() {
        this.formatter = new FormatHelper();
    }

    private String generateBenefitEmployeesTransfersBetweenDepartments(Map<String, List<Employee>> employeesGroupingByDepartment) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Employee>> entry : employeesGroupingByDepartment.entrySet()) {
            List<List<Employee>> allVariantsForCurrentDepartment = getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(getAverageSalaryByDepartment(entry.getValue()), 0, entry.getValue(),
                    new ArrayList<>());

            for(Map.Entry<String, List<Employee>> innerEntry : employeesGroupingByDepartment.entrySet()) {
                List<Employee> currentInnerList = innerEntry.getValue();
                for(List<Employee> list : allVariantsForCurrentDepartment) {
                    List<Employee> currentInnerListWithAddedEmployees = new ArrayList<>(currentInnerList);
                    currentInnerListWithAddedEmployees.addAll(list);
                    if (getAverageSalaryByDepartment(currentInnerList).compareTo(getAverageSalaryByDepartment(currentInnerListWithAddedEmployees)) < 0) {
                        List<Employee> entryListWithRemovingEmployee = new ArrayList<>(entry.getValue());
                        entryListWithRemovingEmployee.removeAll(list);
                        result.append(formatter.formatStringForOutputInFile(entry.getKey(), innerEntry.getKey(), list,
                                getAverageSalaryByDepartment(entry.getValue()),
                                getAverageSalaryByDepartment(currentInnerList),
                                getAverageSalaryByDepartment(entryListWithRemovingEmployee),
                                getAverageSalaryByDepartment(currentInnerListWithAddedEmployees)
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
            if (getAverageSalaryByDepartment(currentList).compareTo(averageSalaryInDepAtThisMoment) < 0) {
                allEmployeesCombinationsForRemovingFromDepartment.add(currentList);
            }
            allEmployeesCombinationsForRemovingFromDepartment.addAll(getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(averageSalaryInDepAtThisMoment, i + 1,
                    sourceEmployeesListInDep, currentList));
        }
        return allEmployeesCombinationsForRemovingFromDepartment;
    }

    private BigDecimal getAverageSalaryByDepartment(List<Employee> list) {
        if (list.isEmpty()) return new BigDecimal("0");
        return list.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal::add)
                .orElseThrow(IllegalStateException::new)
                .divide(BigDecimal.valueOf(list.size()), 2, RoundingMode.HALF_UP);
    }

    public void outputInFileBenefitEmployeesTransfersBetweenDepartments(String outputPath, Map<String, List<Employee>> employeesGroupingByDepartment) throws IOException {
        try(FileWriter f = new FileWriter(outputPath)) {
            f.write(generateBenefitEmployeesTransfersBetweenDepartments(employeesGroupingByDepartment));
        }
    }

}
