import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeesInfoParser {

    public static void parseAndPrintToConsoleEmployeesInfo(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            Map<String, List<String>> employeesGroupingByDepartment = getEmployeesGroupingByDepartment(lines);
            Map<String, Double> averageSalaryByDepartment = getAverageSalaryByDepartments(lines);
            for (Map.Entry<String, Double> e : averageSalaryByDepartment.entrySet()) {
                System.out.println(e.getKey() + " department. Average salary: " + e.getValue() + "\n" + employeesGroupingByDepartment.get(e.getKey()).toString().replaceAll("[\\[\\]]", ""));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void outputAllGoodEmployeesTransfersBetweenDepartments(String inputPath, String outputPath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            Map<String, List<String>> employeesGroupingByDepartment = getEmployeesGroupingByDepartment(lines);
            for (Map.Entry<String, List<String>> e : employeesGroupingByDepartment.entrySet()) {
                for (String worker : e.getValue()) {
                    for (String departmentId : getDepartmentsId(lines)) {
                        if(!departmentId.equals(e.getKey())) {
                            List<String> listWithAddingEmployee = new ArrayList<>(employeesGroupingByDepartment.get(departmentId));
                            List<String> listWithRemovingEmployee = new ArrayList<>(employeesGroupingByDepartment.get(e.getKey()));
                            listWithRemovingEmployee.remove(worker);
                            listWithAddingEmployee.add(worker);
                            if (getAverageSalaryByDepartment(employeesGroupingByDepartment.get(departmentId)) < getAverageSalaryByDepartment(listWithAddingEmployee)
                                    && getAverageSalaryByDepartment(employeesGroupingByDepartment.get(e.getKey())) < getAverageSalaryByDepartment(listWithRemovingEmployee)) {
                                try (FileWriter f = new FileWriter(outputPath, true)) {
                                    f.write(worker + " from " + e.getKey() + " department to " + departmentId + " department - is Good Thing\n");
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, List<String>> getEmployeesGroupingByDepartment(List<String> list) {
        return list.stream()
                   .map(s -> s.split(" "))
                   .collect(Collectors.groupingBy(s -> s[2], Collectors.mapping(s -> s[0] + " " + s[1], Collectors.toList())));
    }

    private static Map<String, Double> getAverageSalaryByDepartments(List<String> list) {
        return list.stream()
                   .map(s -> s.split(" "))
                   .collect(Collectors.groupingBy(s -> s[2], Collectors.averagingInt(s -> Integer.parseInt(s[1]))));
    }

    private static Double getAverageSalaryByDepartment(List<String> list) {
        if (list.isEmpty()) return 0d;
        return list.stream()
                   .mapToInt(s -> Integer.parseInt(s.split(" ")[1]))
                   .average()
                   .getAsDouble();
    }

    private static List<String> getDepartmentsId(List<String> list) {
        return list.stream()
                   .map(s -> s.split(" ")[2])
                   .distinct()
                   .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        parseAndPrintToConsoleEmployeesInfo(args[0]);
        outputAllGoodEmployeesTransfersBetweenDepartments(args[0], args[1]);
    }
}
