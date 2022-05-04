import model.Department;
import model.Employee;
import model.Transfer;

import java.util.ArrayList;
import java.util.List;

public class PossibleTransfersGenerator {

    public List<Transfer> getAllBenefitTransfersBetweenDepartments(List<Department> departmentList) {
        List<Transfer> allTransfers = new ArrayList<>();
        for (Department departmentFrom : departmentList) {
            for (Department departmentTo : departmentList) {
                if(!departmentFrom.equals(departmentTo)) {
                    allTransfers.addAll(getAllBenefitTransfersBetweenTwoDepartments(departmentFrom, departmentTo, 0, new ArrayList<>()));
                }
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
