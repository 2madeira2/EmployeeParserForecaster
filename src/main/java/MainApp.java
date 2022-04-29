import model.Department;

import java.io.IOException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        if(checkCommandLineArguments(args.length)) {
            ParserFromFile parserFromFile = new ParserFromFile();
            PrinterToConsole printerToConsole = new PrinterToConsole();
            WriterIntoFile writerIntoFile = new WriterIntoFile();
            try {
                List<Department> departments = parserFromFile.parseFromFileDepartmentsInfo(args[0]);
                printerToConsole.printToConsoleDepartmentsInfo(departments);
                writerIntoFile.outputInFileBenefitEmployeesTransfersBetweenDepartments(args[1], departments);
            } catch (IOException e) {
                System.out.println("Неверно задан путь к файлам");
            }
        }
    }

    private static boolean checkCommandLineArguments(int argsLength) {
        switch (argsLength) {
            case 0:
                System.out.println("Не заданы аргументы командной строки с путями до файлов для чтения и вывода информации");
                return false;
            case 1:
                System.out.println("Не задан аргумент командной строки с путем до файла вывода информации");
                return false;
            case 2:
                return true;
            default:
                System.out.println("Слишком много аргументов командной строки. Требуется 2 аргумента: путь до файла для чтения информации, путь до файла для вывода информации");
                return false;
        }
    }

}
