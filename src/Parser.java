import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    Map<String, String> NamesToNumber = new HashMap<>();
    ArrayList<String[]> DuplicateNames = new ArrayList<>();

    public Parser() {
        String csvFile = "nerd club.csv";
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

    public void importNames() {
        String csvNames = "";
        String line = "";
        String cvsSplitBy = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] Name = line.split(cvsSplitBy);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parser P = new Parser();
        P.


                System.out.println("DONE");

    }

}