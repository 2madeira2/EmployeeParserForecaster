import models.Employee;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeesInfoParser {

    public static void parseAndPrintToConsoleEmployeesInfo(String path) throws IOException {
            List<String> lines = Files.readAllLines(Paths.get(path));
            Map<Integer, List<Employee>> employeesGroupingByDepartment = getEmployeesGroupingByDepartment(lines);
            for(Map.Entry<Integer, List<Employee>> entry : employeesGroupingByDepartment.entrySet()) {
                int sum = 0;
                for(Employee employee : entry.getValue()) {
                    sum += employee.getSalary();
                }
                System.out.println("Для отдела " + entry.getKey() + " средняя зп: " + sum / entry.getValue().size());
                System.out.println(entry.getValue().toString().replaceAll("[\\[\\]]", ""));
            }
        }

    public static void outputInFileBenefitEmployeesTransfersBetweenDepartments(String inputPath, String outputPath) throws IOException {
        try(FileWriter f = new FileWriter(outputPath)) {
            f.write(generateBenefitEmployeesTransfersBetweenDepartments(inputPath));
        }
    }

    public static String generateBenefitEmployeesTransfersBetweenDepartments(String inputPath) throws IOException {

                StringBuilder result = new StringBuilder();
                List<String> lines = Files.readAllLines(Paths.get(inputPath));
                Map<Integer, List<Employee>> employeesGroupingByDepartment = getEmployeesGroupingByDepartment(lines);
                for (Map.Entry<Integer, List<Employee>> entry : employeesGroupingByDepartment.entrySet()) {
                    List<Employee> currentList = entry.getValue();
                    currentList.sort((e1, e2) -> (int) (e1.getSalary() - e2.getSalary()));
                    for (Map.Entry<Integer, List<Employee>> innerEntry : employeesGroupingByDepartment.entrySet()) {
                        List<Employee> addedEmployees = new ArrayList<>();
                        if (!innerEntry.getKey().equals(entry.getKey())) {
                            List<Employee> targetList = innerEntry.getValue();
                            Double averageSalaryInCurrentTargetDep = getAverageSalaryByDepartment(targetList);
                            List<Employee> copyOfCurrentList = new ArrayList<>(currentList);
                            for (int i = 0; i < currentList.size() - 1; i++) {
                                for (int j = i + 1; j < currentList.size(); j++) {

                                }
                                //targetList.add(employee);
                               // copyOfCurrentList.remove(employee);
                                if (getAverageSalaryByDepartment(copyOfCurrentList) > getAverageSalaryByDepartment(currentList)
                                        && getAverageSalaryByDepartment(targetList) > averageSalaryInCurrentTargetDep) {
                                 //   addedEmployees.add(employee);
                                }
                            }
                        }
                        if (!addedEmployees.isEmpty()) {
                              result.append("При переходе следующих сотрудников из отдела ")
                                      .append(entry.getKey())
                                      .append(" в отдел ")
                                      .append(innerEntry.getKey())
                                      .append(" средняя зарплата в обоих отделах увеличится:\n")
                                      .append(addedEmployees.toString().replaceAll("[\\[\\]]", ""))
                                      .append("\n")
                                      .append("------------------------------------------------------------------------\n");
                        }
                    }
                }
                return result.toString();
    }

    private static Map<Integer, List<Employee>> getEmployeesGroupingByDepartment(List<String> list) {
        return list.stream()
                   .map(s -> s.split(" "))
                   .map(s -> new Employee(s[0], Long.parseLong(s[1]), Integer.parseInt(s[2])))
                   .collect(Collectors.groupingBy(Employee::getDepartmentId));
    }

    private static Double getAverageSalaryByDepartment(List<Employee> list) {
        if (list.isEmpty()) return 0d;
        return list.stream()
                .mapToLong(Employee::getSalary)
                .average()
                .getAsDouble();
    }

    public static void main(String[] args) {
        try {
            parseAndPrintToConsoleEmployeesInfo(args[0]);
           // outputInFileBenefitEmployeesTransfersBetweenDepartments(args[0], args[1]);
        } catch (IOException e) {
            throw new RuntimeException("Неверно задан путь к файлам", e.getCause());
        }
    }
}
