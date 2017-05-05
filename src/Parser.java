import java.io.BufferedReader;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Parser {
    Map<String, String> NamesToNumber = new HashMap<>();
    ArrayList<String[]> DuplicateNames = new ArrayList<>();

    public Parser(String fileName) {
        // Set up database
        String csvFile = fileName;
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                String[] nameNum = new String[2];
                String name = country[9] + " " + country[11];
                if (NamesToNumber.containsKey(name)) {
                    nameNum[0] = name;
                    nameNum[1] = country[5];
                    String tempNum = NamesToNumber.get(name);
                    NamesToNumber.remove(name);
                    DuplicateNames.add(0, nameNum);
                    DuplicateNames.add(0, new String[]{name, tempNum});

                } else {
                    NamesToNumber.put(name, country[5]);
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean contains(ArrayList<String[]> data, int indexToCheck, String searchQuery) {
        for (String[] entry : data) {
            if (entry[indexToCheck].equalsIgnoreCase(searchQuery)) {
                return true;
            }
        }
        return false;
    }

    public static String getIndex(ArrayList<String[]> data, int indexToCheck, int valueIndex, String searchQuery) {
        for (int i = 0; i < data.size(); i++) {
            String[] entry = data.get(i);
            if (entry[indexToCheck].equalsIgnoreCase(searchQuery)) {
                return entry[valueIndex];
            }
        }
        return "";
    }

    public String[] fuzzySearch(String who) {
        Set<String> keys = NamesToNumber.keySet();
        int maxFuzzyScore = 0;
        int score;
        String[] name_number = new String[2];
        for (String name : keys) {
            if ((score = FuzzySearch.ratio(who, name)) > maxFuzzyScore) {
                maxFuzzyScore = score;
                name_number[0] = name;
                name_number[1] = NamesToNumber.get(name);
            }

        }
        for (String[] nameAndNumber :DuplicateNames) {


        }

        return name_number;
    }

    public void importStudents(String filePath) {
        String csvNames = filePath;
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvNames))) {

            while ((line = br.readLine()) != null) {
                String[] Name = line.split(cvsSplitBy);
                String firstLastName = Name[0] + " " + Name[1];
                if (NamesToNumber.containsKey(firstLastName)) {
                    System.out.println(firstLastName + " Value: " + NamesToNumber.get(firstLastName));
                } else if (contains(DuplicateNames, 0, firstLastName)) { // What are we doing with duplicate names?
                    System.out.println(firstLastName + getIndex(DuplicateNames, 0, 1, firstLastName)); // currently only outputs one of the duplicate names
                } else {
                    String[] x = fuzzySearch(firstLastName);
                    System.out.println("Closest name to " + firstLastName + " is " + x[0]);

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parser P = new Parser("nerd club.csv");
        P.importStudents("students.csv");
    }

}