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
            List<List<Employee>> allVariantsForCurrentDepartment = getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(getAverageSalaryByDepartment(entry.getValue()), 0, entry.getValue(),
                    new ArrayList<>());

            for(Map.Entry<Integer, List<Employee>> innerEntry : employeesGroupingByDepartment.entrySet()) {
                List<Employee> currentInnerList = innerEntry.getValue();
                for(List<Employee> list : allVariantsForCurrentDepartment) {
                    List<Employee> currentInnerListWithAddedEmployees = new ArrayList<>(currentInnerList);
                    currentInnerListWithAddedEmployees.addAll(list);
                    if (getAverageSalaryByDepartment(currentInnerList).compareTo(getAverageSalaryByDepartment(currentInnerListWithAddedEmployees)) < 0) {
                        List<Employee> entryListWithRemovingEmployee = new ArrayList<>(entry.getValue());
                        entryListWithRemovingEmployee.removeAll(list);
                        result.append("Из отдела ")
                                .append(entry.getKey())
                                .append(" в отдел ")
                                .append(innerEntry.getKey())
                                .append(" с увеличением средней зп в обоих отделах возможен перевод следующих сотрудников: \n")
                                .append(list.toString().replaceAll("[\\[\\]]", ""))
                                .append("\n")
                                .append("Зп до перевода в ")
                                .append(entry.getKey())
                                .append(" отделе: ")
                                .append(getAverageSalaryByDepartment(entry.getValue()))
                                .append(". Зп до перевода в ")
                                .append(innerEntry.getKey())
                                .append(" отделе: ")
                                .append(getAverageSalaryByDepartment(currentInnerList))
                                .append(". \nЗп после перевода в ")
                                .append(entry.getKey())
                                .append(" отделе: ")
                                .append(getAverageSalaryByDepartment(entryListWithRemovingEmployee))
                                .append(". Зп после перевода в ")
                                .append(innerEntry.getKey())
                                .append(" отделе: ")
                                .append(getAverageSalaryByDepartment(currentInnerListWithAddedEmployees))
                                .append("\n\n");
                    }
                }
            }
        }
        return result.toString();
    }

    private static List<List<Employee>> getAllEmployeesCombinationsWhoseRemovingIncreaseAverageSalaryInDep(Double averageSalaryInDepAtThisMoment,
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
            outputInFileBenefitEmployeesTransfersBetweenDepartments(args[0], args[1]);
        } catch (IOException e) {
            throw new RuntimeException("Неверно задан путь к файлам", e.getCause());
        }
    }
}
