import models.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        if(checkCommandLineArguments(args.length)) {
            ParserPrinterToConsole parserPrinterToConsole = new ParserPrinterToConsole();
            WriterIntoFile writerIntoFile = new WriterIntoFile();
            try {
                Map<String, List<Employee>> employeesGroupingByDepartment = parserPrinterToConsole.parseAndPrintToConsoleEmployeesInfo(args[0]);
                writerIntoFile.outputInFileBenefitEmployeesTransfersBetweenDepartments(args[1], employeesGroupingByDepartment);
            } catch (IOException e) {
                System.out.println("Неверно задан путь к файлам");
            }
        }
    }

    private static boolean checkCommandLineArguments(int argsLength) {
        if(argsLength != 2) {
            switch (argsLength) {
                case 0:
                    System.out.println("Не заданы аргументы командной строки с путями до файлов для чтения и вывода информации");
                    break;
                case 1:
                    System.out.println("Не задан аргумент командной строки с путем до файла вывода информации");
                    break;
                default:
                    System.out.println("Слишком много аргументов командной строки. Требуется 2 аргумента: путь до файла для чтения информации, путь до файла для вывода информации");
            }
            return false;
        }
        return true;
    }

}
