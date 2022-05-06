import model.Department;
import model.Employee;
import model.Transfer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PossibleTransfersGenerator {

    public List<Transfer> getAllBenefitTransfersBetweenDepartments(List<Department> departmentList) {
        List<Transfer> allTransfers = new ArrayList<>();
        departmentList.sort(Comparator.comparing(Department::getAverageSalary, Comparator.reverseOrder()));
        for (int i = 0; i < departmentList.size() - 1; i++) {
            for (int j = i + 1; j < departmentList.size(); j++) {
                allTransfers.addAll(getAllBenefitTransfersBetweenTwoDepartments(departmentList.get(i), departmentList.get(j), 0, new ArrayList<>()));
            }
        }
        return allTransfers;
    }

    private List<Transfer> getAllBenefitTransfersBetweenTwoDepartments(Department departmentFrom,
                                                                       Department departmentTo,
                                                                       int startingPosition,
                                                                       List<Employee> list) {
        List<Transfer> benefitTransfersBetweenCurrentDepartments = new ArrayList<>();
        for (int i = startingPosition; i < departmentFrom.getEmployees().size(); i++) {
            List<Employee> currentList = new ArrayList<>(list);
            currentList.add(departmentFrom.getEmployees().get(i));
            Transfer currentTransfer = new Transfer(currentList, departmentFrom, departmentTo);
            if (currentTransfer.isBenefitTransfer()) {
                benefitTransfersBetweenCurrentDepartments.add(currentTransfer);
            }
            benefitTransfersBetweenCurrentDepartments.addAll(getAllBenefitTransfersBetweenTwoDepartments(departmentFrom, departmentTo, i + 1, currentList));
        }
        return benefitTransfersBetweenCurrentDepartments;
    }
}
