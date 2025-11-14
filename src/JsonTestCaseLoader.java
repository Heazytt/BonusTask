import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;


public class JsonTestCaseLoader {


    private static class TestCaseListWrapper {
        List<TestCase> tests;
    } // --> Wrapper for JSON structure


    public static List<TestCase> loadFromFile(String jsonPath) { // --> Load test cases from JSON file
        Gson gson = new Gson(); // --> Gson instance for JSON parsing

        try (Reader reader = Files.newBufferedReader(Paths.get(jsonPath), StandardCharsets.UTF_8)) { // --> Read file
            TestCaseListWrapper wrapper = gson.fromJson(reader, TestCaseListWrapper.class); // --> Parse JSON into wrapper
            if (wrapper == null || wrapper.tests == null) { // --> Check for null
                return Collections.emptyList();
            }
            return wrapper.tests; // --> Return list of test cases
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("JSON parsing error: " + e.getMessage()); // --> Handle JSON errors
            return Collections.emptyList();
        } catch (IOException e) {
            System.err.println("JSON read error: " + e.getMessage()); // --> Handle IO errors
            return Collections.emptyList();
        }
    }
}
