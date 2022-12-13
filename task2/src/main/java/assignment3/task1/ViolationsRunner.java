package assignment3.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ViolationsRunner {
    public static void finesStatistics(String nameOfDirectory) throws ExecutionException, InterruptedException {
        //value to check in the end time the program was working
        long time = System.currentTimeMillis();

        ClassLoader classLoader = ViolationsRunner.class.getClassLoader();
        File[] listOfFiles = new File(classLoader.getResource(nameOfDirectory).getPath()).listFiles();

        //take all xml files from directory
        List<File> violations = Arrays.stream(listOfFiles)
                .filter(el -> el.getName().endsWith(".xml"))
                .collect(Collectors.toList());

        int numberOfThreads = 2;
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);

        List<CompletableFuture<List<Violation>>> tasks = new ArrayList<>();

        List<Violation> violationsList = new ArrayList<>();

        //create and run tasks
        //threads only read violations from files and returns list
        for (File violation : violations) {
            tasks.add(CompletableFuture.supplyAsync(() -> getViolationsFromXml(violation), pool));
        }

        //get the result of tasks
        for (CompletableFuture<List<Violation>> task : tasks) {
            violationsList.addAll(task.get());
        }

        violationsToJson(getResultsForViolations(violationsList));

        pool.shutdown();

        System.out.println(System.currentTimeMillis() - time);
    }

    private static List<Violation> getViolationsFromXml(File violation) {
        XmlMapper mapper = new XmlMapper();
        List<Violation> violationList = new ArrayList<>();

        try {
            //get all violations from the file according to the template Violation
            Violations violationsClass = mapper.readValue(violation, Violations.class);
            violationList.addAll(violationsClass.violation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return violationList;
    }

    private static List<ViolationResult> getResultsForViolations(List<Violation> violations) {
        //BigDecimal for correct displaying big numbers
        Map<String, BigDecimal> fines = new HashMap<>();
        List<ViolationResult> results = new ArrayList<>();

        //put all keys to the map and count their values
        //if key already exist - add current value to existed
        for (Violation violation : violations) {
            if (fines.containsKey(violation.type)) {
                fines.put(violation.type, fines.get(violation.type)
                        .add(BigDecimal.valueOf(Double.parseDouble(violation.fine_amount))));
            } else {
                fines.put(violation.type, BigDecimal.valueOf(Double.parseDouble(violation.fine_amount)));
            }
        }

        //make a template ViolationResult from the map
        for (String type : fines.keySet()) {
            results.add(new ViolationResult(type, fines.get(type)));
        }

        //sort by the fine_amount
        return results.stream()
                .sorted(Comparator.comparing(ViolationResult::getFine_amount).reversed())
                .collect(Collectors.toList());
    }

    private static void violationsToJson(List<ViolationResult> violationResults) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //write result to a json by a template
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("violationsStatistics.json"), violationResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
