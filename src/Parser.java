import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    public static void main(String[] args) {

        String csvFile = "nerd club.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.print("Country [code= " + country[6] +" "+ country[8] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}