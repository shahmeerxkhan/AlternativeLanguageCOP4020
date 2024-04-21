
package alternativelanguage;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Main {

    public static void main(String[] args) {
        Map<Long, Cell> cellMap = new HashMap<>(); 
        
        try (FileReader reader = new FileReader("cells.csv"); CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : parser) {
                long lineNumber = csvRecord.getRecordNumber();
                if (lineNumber == 1) {
                    continue; // Skip header row
                }
                Cell cell = new Cell();
                cell.setOem(CheckForBlank(csvRecord.get(0)));
                cell.setModel(CheckForBlank(csvRecord.get(1)));
                cell.setLaunch_announced(processLaunchAnnounced(csvRecord.get(2)));
                cell.setLaunch_status(processlaunchStatus(csvRecord.get(3)));
                cell.setBody_dimensions(CheckForBlank(csvRecord.get(4)));
                cell.setBody_weight(processBodyWeight(csvRecord.get(5)));
                cell.setBody_sim(processBodySim(csvRecord.get(6)));
                cell.setDisplay_type(CheckForBlank(csvRecord.get(7)));
                cell.setDisplay_size(processDisplaySize(csvRecord.get(8)));
                cell.setDisplay_resolution(CheckForBlank(csvRecord.get(9)));
                cell.setFeatures_sensors(processFeaturesSensors(csvRecord.get(10)));
                cell.setPlatform_os(processPlatformOs(csvRecord.get(11)));

                cellMap.put(lineNumber, cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Printing all the columns
//        for (Map.Entry<Long, Cell> entry : cellMap.entrySet()) {
//            Cell cell = entry.getValue();
//            System.out.println(entry.getKey() +": "+ cell);
//        }
        
      Cell cell = new Cell();
      //  Print the Company with highest average weight
       System.out.println("Company with highest average weight: " + cell.calculateAverageWeight(cellMap));
       // Print phones that were announced in one year and released in another
         cell.diffYearAnnounced(cellMap);
     //   Print total phones with one features
       System.out.println("Phones With one Feature: "+cell.oneFeatureSensor(cellMap));
       // Print mostPhonesLaunchedyear
      System.out.println("Most Phones Launched in Year: "+cell.mostPhonesLaunchedyear(cellMap));


          //print mean of boday weight
//         System.out.println("Mean of Body Weigth: "+cell.meanBodyWeight(cellMap));
//        //Print unique OEMs
//        for (String s : cell.uniqueOems(cellMap)) {
//            System.out.println("OEM: "+s);
//        }
//        //Print unique Models
//        for (String s : cell.uniqueModels(cellMap)) {
//            System.out.println("Models: "+s);
//        }
//        //print count of discontinued models
//        System.out.println("Discontinued Models: "+cell.discontinuedModels(cellMap));
//        //print count of cancelled models
//        System.out.println("Cancelled Models: "+cell.cancelledModels(cellMap));
        
    }
    // Proceed with data cleaning and transformation
    public static String CheckForBlank(String value) {
        String result = "".equals(value) || "-".equals(value) ? null : value;
        return result;
    }

    // Data processing methods
    public static Integer processLaunchAnnounced(String value) {
        try {
            // Extracting year using regex
            Pattern pattern = Pattern.compile("\\d{4}");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }

        } catch (NumberFormatException | IllegalStateException ignored) {
            ignored.printStackTrace();
        }
        return 0;
    }

    private static String processlaunchStatus(String value) {
        try {

            Pattern pattern = Pattern.compile("\\d{4}");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return matcher.group();
            }

            if ("Discontinued".equals(value) || "Cancelled".equals(value)) {
                return value;
            }

        } catch (NumberFormatException | IllegalStateException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    public static Float processBodyWeight(String value) {
        try {
            // Extracting weight using regex
            Pattern pattern = Pattern.compile("(\\d+)\\s*g");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return Float.parseFloat(matcher.group(1));
            }
        } catch (NumberFormatException | IllegalStateException ignored) {
            ignored.printStackTrace();
        }
        return 0f;
    }

    private static String processBodySim(String value) {
        String result = "No".equalsIgnoreCase(value) || "Yes".equalsIgnoreCase(value) ? null : value;
        return result;

    }

    public static Float processDisplaySize(String value) {
        try {
            // Extracting size in inches using regex
            Pattern pattern = Pattern.compile("(\\d+\\.?\\d*)\\s*inches");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return Float.parseFloat(matcher.group(1));
            } else if (value.matches("\\d+") || value.matches("([+-]?[0-9]*\\.[0-9]*)")) {
                return 0f;
            }

        } catch (NumberFormatException | IllegalStateException ignored) {
            ignored.printStackTrace();
        }
        return 0f;
    }

    private static String processFeaturesSensors(String value) {

        if (value.matches("\\d+") || value.matches("([+-]?[0-9]*\\.[0-9]*)")) {
            return null;
        } else {
            String newValue = CheckForBlank(value);
            return newValue;
        }

    }

    private static String processPlatformOs(String value) {
        if (value.matches("\\d+") || value.matches("([+-]?[0-9]*\\.[0-9]*)")) {
            return null;
        }
        int commaIndex = value.indexOf(",");
        String result = commaIndex != -1 ? value.substring(0, commaIndex) : value;
        String newValue = CheckForBlank(result);
        return newValue;
    }

    
}
