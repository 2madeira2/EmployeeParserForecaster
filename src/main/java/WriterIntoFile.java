import model.Department;
import model.Transfer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterIntoFile {

    private PossibleTransfersGenerator transfersGenerator;

    public WriterIntoFile() {
        this.transfersGenerator = new PossibleTransfersGenerator();
    }

    public void outputInFileBenefitEmployeesTransfersBetweenDepartments(String outputPath, List<Department> departments) throws IOException {
        try(FileWriter f = new FileWriter(outputPath)) {
            List<Transfer> allTransfers = transfersGenerator.getAllBenefitTransfersBetweenDepartments(departments);
            for(Transfer transfer : allTransfers) {
                f.write(transfer.getTransferInfo());
            }
        }
    }
}
