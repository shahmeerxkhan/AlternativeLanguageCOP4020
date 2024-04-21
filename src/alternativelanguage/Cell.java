
package alternativelanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cell {

    private String oem;
    private String model;
    private int launch_announced;
    private String launch_status;
    private String body_dimensions;
    private float body_weight;
    private String body_sim;
    private String display_type;
    private float display_size;
    private String display_resolution;
    private String features_sensors;
    private String platform_os;

    public Cell() {
    }

    public Cell(String oem, String model, int launch_announced, String launch_status, String body_dimensions, float body_weight, String body_sim, String display_type, float display_size, String display_resolution, String features_sensors, String platform_os) {
        this.oem = oem;
        this.model = model;
        this.launch_announced = launch_announced;
        this.launch_status = launch_status;
        this.body_dimensions = body_dimensions;
        this.body_weight = body_weight;
        this.body_sim = body_sim;
        this.display_type = display_type;
        this.display_size = display_size;
        this.display_resolution = display_resolution;
        this.features_sensors = features_sensors;
        this.platform_os = platform_os;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getLaunch_announced() {
        return launch_announced;
    }

    public void setLaunch_announced(int launch_announced) {
        this.launch_announced = launch_announced;
    }

    public String getLaunch_status() {
        return launch_status;
    }

    public void setLaunch_status(String launch_status) {
        this.launch_status = launch_status;
    }

    public String getBody_dimensions() {
        return body_dimensions;
    }

    public void setBody_dimensions(String body_dimensions) {
        this.body_dimensions = body_dimensions;
    }

    public float getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(float body_weight) {
        this.body_weight = body_weight;
    }

    public String getBody_sim() {
        return body_sim;
    }

    public void setBody_sim(String body_sim) {
        this.body_sim = body_sim;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public float getDisplay_size() {
        return display_size;
    }

    public void setDisplay_size(float display_size) {
        this.display_size = display_size;
    }

    public String getDisplay_resolution() {
        return display_resolution;
    }

    public void setDisplay_resolution(String display_resolution) {
        this.display_resolution = display_resolution;
    }

    public String getFeatures_sensors() {
        return features_sensors;
    }

    public void setFeatures_sensors(String features_sensors) {
        this.features_sensors = features_sensors;
    }

    public String getPlatform_os() {
        return platform_os;
    }

    public void setPlatform_os(String platform_os) {
        this.platform_os = platform_os;
    }

    //toString method
    @Override
    public String toString() {
        String value = " |OEM: " + getOem() + " |" + "Model: " + getModel() + " |" + "Launch Announced: " + getLaunch_announced()
                + " |L_status: " + getLaunch_status() + " |B_Dimen: " + getBody_dimensions() + " |B_Weig: " + getBody_weight()
                + " |B_Sim: " + getBody_sim() + " |D_Type: " + getDisplay_type() + " |D_Size: " + getDisplay_size()
                + " |D_resol: " + getDisplay_resolution() + " |Feature: " + getFeatures_sensors() + " |P_OS: " + getPlatform_os();

        return value;
    }
    // Different Year announced 
    public void diffYearAnnounced(Map<Long, Cell> cellMap) {
        Map<String, String> diffYears = new HashMap<>();
        for (Cell cell : cellMap.values()) {
            int announcedYear = cell.getLaunch_announced();
            String status = cell.getLaunch_status();
            if (status != null && status.matches("\\d{4}") && !status.equals(Integer.toString(announcedYear))) {
                String key = cell.getOem() + " " + cell.getModel();
                diffYears.put(key, "Announced " + announcedYear + ", Released: " + status);
            }
        }
            System.out.println("Total Different Years OEMs: "+diffYears.size());
            if (!diffYears.isEmpty()) {
                for(Map.Entry<String, String> entry : diffYears.entrySet())
                {
                    System.out.println("OEM: "+entry.getKey() +" Year: "+ entry.getValue());
                }
            }
    
    }
    
    //Average Weight
    public String calculateAverageWeight(Map<Long, Cell> cellMap) {
        // Calculate average weight for each company
        Map<String, Float> averageWeights = new HashMap<>();
        Map<String, Integer> companyCount = new HashMap<>();

        for (Cell cell : cellMap.values()) {
            String oem = cell.getOem();
            float bodyWeight = cell.getBody_weight();

            // Update total weight and count for the company
            averageWeights.put(oem, averageWeights.getOrDefault(oem, 0f) + bodyWeight);
            companyCount.put(oem, companyCount.getOrDefault(oem, 0) + 1);
        }

        // Calculate average weight for each company
        Map<String, Float> averageWeightsPerCompany = new HashMap<>();
        for (String oem : averageWeights.keySet()) {
            float totalWeight = averageWeights.get(oem);
            int count = companyCount.get(oem);
            float averageWeight = totalWeight / count;
            averageWeightsPerCompany.put(oem, averageWeight);
        }

        // Find the company with the highest average weight
        String companyWithHighestAverageWeight = null;
        float highestAverageWeight = Float.MIN_VALUE;
        for (Map.Entry<String, Float> entry : averageWeightsPerCompany.entrySet()) {
            if (entry.getValue() > highestAverageWeight) {
                highestAverageWeight = entry.getValue();
                companyWithHighestAverageWeight = entry.getKey();
            }
        }
        return companyWithHighestAverageWeight;
    }
    // One Feature Sensor
    public int oneFeatureSensor(Map<Long, Cell> cellMap)
    {
        int numberOfPhones = 0;
        for (Cell cell : cellMap.values()) {
            String features  = cell.getFeatures_sensors();
            if(features!=null && !features.isEmpty())
            {
                String[] sensors = features.split(",");
                if(sensors.length ==1)
                {
                    numberOfPhones++;
                }
            }
        }
        return numberOfPhones;
    }
    //Most Phones Launched Year
    public int mostPhonesLaunchedyear(Map<Long, Cell> cellMap)
    {
        Map<Integer, Integer> launchYearCounts = new HashMap<>();
        for (Cell cell : cellMap.values()) {
            int launchYear = cell.getLaunch_announced();
            if(launchYear > 1999)
            {
                launchYearCounts.put(launchYear,launchYearCounts.getOrDefault(launchYear, 0) + 1);
            }
        }
        int maxCount =0;
        int mostYearLaunches = 0;
        for(Map.Entry<Integer, Integer> entry : launchYearCounts.entrySet())
        {
            int year = entry.getKey();
            int count = entry.getValue();
            if(count>maxCount)
            {
                maxCount = count;
                mostYearLaunches = year;
            }
        }
        return mostYearLaunches;
    }
    // Mean of boday Weight
    public float meanBodyWeight(Map<Long, Cell> cellMap)
    {
        float totalBodyWeight = 0;
        int numberOfPhones = 0;

        for (Cell cell : cellMap.values()) {
            float bodyWeight = cell.getBody_weight();
            if (bodyWeight != 0) { // Exclude 0 or invalid values
                totalBodyWeight += bodyWeight;
                numberOfPhones++;
            }
        }

        // Calculate mean body weight
        float meanBodyWeight = numberOfPhones > 0 ? totalBodyWeight / numberOfPhones : 0;
        return meanBodyWeight;
    }
    //Unique OEMs
    public List<String> uniqueOems(Map<Long, Cell> cellMap)
    {
        List<String> uniqueOems = new ArrayList<>();
        for (Cell cell : cellMap.values()) {
            String oem = cell.getOem();
            if(oem!=null && !uniqueOems.contains(oem))
            {
                uniqueOems.add(oem);
            }
        }
        return uniqueOems;
    }
    //Unique Models
    public List<String> uniqueModels(Map<Long, Cell> cellMap)
    {
        List<String> uniqueModels = new ArrayList<>();
        for (Cell cell : cellMap.values()) {
            String model = cell.getModel();
            if(model!=null && !uniqueModels.contains(model))
            {
                uniqueModels.add(model);
            }
        }
        return uniqueModels;
    }
    //Unique Discontinued
    public int discontinuedModels(Map<Long, Cell> cellMap)
    {
        int count = 0;
        for (Cell cell : cellMap.values()) {
            if(cell.getLaunch_status().equals("Discontinued"))
            {
                count++;
            }
        }
    return count;
    }
    //Unique Cancelled
    public int cancelledModels(Map<Long, Cell> cellMap)
    {
        int count = 0;
        for (Cell cell : cellMap.values()) {
            if(cell.getLaunch_status().equals("Cancelled"))
            {
                count++;
            }
        }
    return count;
    }
}
