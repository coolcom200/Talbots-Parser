package creditparser;

import java.io.BufferedReader;

import me.xdrop.fuzzywuzzy.FuzzySearch;

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
                    //new guy
                    DuplicateNames.add(0, nameNum);
                    //old guy
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

    public static ArrayList<String[]> getValue(ArrayList<String[]> data, int indexToCheck, int valueIndex, String searchQuery) {
        ArrayList<String[]> matchingNames = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String[] entry = data.get(i);
            if (entry[indexToCheck].equalsIgnoreCase(searchQuery)) {
                matchingNames.add(entry);
            }
        }
        return matchingNames;
    }

    public ArrayList<String[]> typoNameSearch(String who) {
        Set<String> keys = NamesToNumber.keySet();
        int maxFuzzyScore = 0;
        int score;
//        ArrayList<String[]> nameNumber = new ArrayList<>();
        ArrayList<String[]> pos = new ArrayList<>();
        for (String name : keys) {
            if ((score = FuzzySearch.ratio(who, name)) > maxFuzzyScore) {
                maxFuzzyScore = score;
//                nameNumber[0] = name;
//                nameNumber[1] = NamesToNumber.get(name);
            }

        }
        for (String[] nameAndNumber : DuplicateNames) {
            if ((score = FuzzySearch.ratio(who, nameAndNumber[0])) > maxFuzzyScore) {
                maxFuzzyScore = score;
//                nameNumber = nameAndNumber;
            }


        }
        for (String name : keys) {
            if ((FuzzySearch.ratio(who, name)) == maxFuzzyScore) {
                pos.add(new String[]{name, NamesToNumber.get(name)});
            }
        }
        for (String[] nameAndNumber :DuplicateNames) {
            if ((FuzzySearch.ratio(who,nameAndNumber[0])) == maxFuzzyScore){
                pos.add(nameAndNumber);
            }
        }
        return pos;
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
                    ArrayList<String[]> dupMatchingNames = getValue(DuplicateNames, 0, 1, firstLastName);
                    for (String[] n : dupMatchingNames) {
                        System.out.println(firstLastName + " " + n[1] + " DUPLICATE!"); // currently only outputs one of the duplicate names
                    }

                } else {
                    ArrayList<String[]> x = typoNameSearch(firstLastName);
                    for (String[] i:x){
                        for (String j: i){
                            System.out.println(j);
                        }
                    }
                    System.out.println("Closest name to " + firstLastName + " is " + x.get(0)[0]);

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Parser P = new Parser("nerd club.csv");
//        P.importStudents("students.csv");
//        Graphics gc = new Graphics();
//        gc.main(new String[]{});
    }

}