import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeesInfoParser {
    public static void parseEmployeesInfo(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            Map<String, List<String>> employeesGroupingByDepartment = lines
                    .stream()
                    .map(s -> s.split(" "))
                    .collect(Collectors.groupingBy(s -> s[2], Collectors.mapping(s -> s[0] + " " + s[1], Collectors.toList())));
            Map<String, Double> averageSalaryByDepartment = lines
                    .stream()
                    .map(s -> s.split(" "))
                    .collect(Collectors.groupingBy(s -> s[2], Collectors.averagingInt(s -> Integer.parseInt(s[1]))));
            for(Map.Entry<String, Double> e : averageSalaryByDepartment.entrySet()) {
                System.out.println(e.getKey()
                        + " department. Average salary: "
                        + e.getValue() + "\n"
                        + employeesGroupingByDepartment.get(e.getKey()).toString().replaceAll("[\\[\\]]", ""));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        parseEmployeesInfo("src/main/java/test.txt");
    }
}
