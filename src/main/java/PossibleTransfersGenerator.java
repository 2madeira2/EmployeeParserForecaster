import model.Department;
import model.Employee;
import model.Transfer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PossibleTransfersGenerator {

    public void printAllBenefitTransfersBetweenDepartments(List<Department> departmentList, String outputPath) throws IOException {
        departmentList.sort(Comparator.comparing(Department::getAverageSalary, Comparator.reverseOrder()));
        try(FileWriter f = new FileWriter(outputPath)) {
            for (int i = 0; i < departmentList.size() - 1; i++) {
                for (int j = i + 1; j < departmentList.size(); j++) {
                    printAllBenefitTransfersBetweenTwoDepartments(departmentList.get(i), departmentList.get(j), 0, new ArrayList<>(), f);
                }
            }
        }
    }

    private void printAllBenefitTransfersBetweenTwoDepartments(Department departmentFrom,
                                                               Department departmentTo,
                                                               int startingPosition,
                                                               List<Employee> list,
                                                               FileWriter f) throws IOException {
        List<Transfer> benefitTransfersBetweenCurrentDepartments = new ArrayList<>();
        for (int i = startingPosition; i < departmentFrom.getEmployees().size(); i++) {
            List<Employee> currentList = new ArrayList<>(list);
            currentList.add(departmentFrom.getEmployees().get(i));
            Transfer currentTransfer = new Transfer(currentList, departmentFrom, departmentTo);
            if (currentTransfer.isBenefitTransfer()) {
                f.write(currentTransfer.getTransferInfo());
            }
            printAllBenefitTransfersBetweenTwoDepartments(departmentFrom, departmentTo, i + 1, currentList, f);
        }
    }
}
