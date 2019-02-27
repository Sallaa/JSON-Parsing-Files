import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

class main {

    public static String printJSON(HouseModel house) {

        String jsonString = "";


        JSONObject jsonPositions = new JSONObject();


        for (int i = 0; i < house.positions.size(); i++) {

            jsonPositions.put(house.positions.get(i), new JSONObject(house.cellPicked.get(house.positions.get(i))));
        }
        JSONObject json = new JSONObject();
        json.put(house.name, jsonPositions);

        jsonString = json.toString();


        try (FileWriter file = new FileWriter("json/test.json")) {

            file.write(json.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }


    public static List<Path> ListFilesByStream() throws IOException {

        Path source = Paths.get("resources");

        List<Path> jsonFiles = Files.walk(source).filter(Files::isRegularFile).map(Path::getFileName)
                .sorted()
                .collect(Collectors.toList());

        return jsonFiles;
    }


    public static Houses ParseJSONArray(Houses houses, List<Path> jsonFiles) {

        for (int i = 0; i < jsonFiles.size(); i++) {
            String filename = jsonFiles.get(i).toString();
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("resources/" + filename));

                JSONObject jsonObject = (JSONObject) obj;
                System.out.println("\n New file --- " + filename);

                int comparisonPageNumber;
                int comparisonIdNumber;
                String cellPicked;

                JSONObject jsonObjInfo = (JSONObject) jsonObject.get("info");
                comparisonPageNumber = Integer.parseInt(jsonObjInfo.get("comparison_page").toString());

                comparisonIdNumber = Integer.parseInt(jsonObjInfo.get("comparison_id").toString());

                JSONObject JSONObjSelection = (JSONObject) jsonObject.get("selection");

                cellPicked = JSONObjSelection.get("cell_picked").toString();
                System.out.println(cellPicked);

                houses = SaveJSONToObj(houses, comparisonPageNumber, comparisonIdNumber, cellPicked);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return houses;
    }

    public static Houses calculateCellPickingPercentage(Houses houses) {

        for (int i = 0; i < houses.listHouses.size(); i++) {
            HouseModel house = houses.listHouses.get(i);

            for (int j = 0; j < house.positions.size(); j++) {
                String cellPosition = house.positions.get(j);

                Iterator it = house.cellPicked.get(cellPosition).entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry userCellPicked = (Map.Entry) it.next();
//                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    double percentageForCellPicked = (double) userCellPicked.getValue() / house.numberOfDifferentCellsPicked.get(cellPosition).floatValue() * 100;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    decimalFormat.setRoundingMode(RoundingMode.DOWN);
                    percentageForCellPicked = Double.valueOf(decimalFormat.format(percentageForCellPicked));
                    System.out.println((String) userCellPicked.getKey());
                    System.out.println((double) userCellPicked.getValue());
                    System.out.println(house.numberOfDifferentCellsPicked.get(cellPosition).floatValue());
                    System.out.println(percentageForCellPicked + "%");
                    houses.listHouses.get(i).cellPicked.get(cellPosition).replace((String) userCellPicked.getKey(), percentageForCellPicked);

//                    it.remove();
                }
            }

        }

        return houses;
    }


    public static Houses SaveJSONToObj(Houses houses, int idHouse, int idPosition, String cellPicked) {
        String strIdPosition = houses.listHouses.get(idHouse - 1).positions.get(idPosition - 1);

        System.out.println("House is " + houses.listHouses.get(idHouse - 1).name);
        System.out.println("position is " + strIdPosition);
        if (houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).get(cellPicked) != null) {
            double occurrences = houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).get(cellPicked);
            houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).replace(cellPicked, occurrences + 1);
            System.out.println(occurrences);
            System.out.println("new value is :" + houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).get(cellPicked));
        } else {
            houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).put(cellPicked, 1.0);

        }
        int nbOfTimeCellPicked = houses.listHouses.get(idHouse - 1).numberOfDifferentCellsPicked.get(strIdPosition);
        houses.listHouses.get(idHouse - 1).numberOfDifferentCellsPicked.replace(strIdPosition, nbOfTimeCellPicked + 1);
        System.out.println("idPosition: " + strIdPosition + " ---- " + "nbOfTimeCellPicked: " + (nbOfTimeCellPicked + 1));
        return houses;
    }


    public static void main(String[] args) {

        List<String> positionsH6592 = new ArrayList<>();
        positionsH6592.add("Empty_21_Cell");
        positionsH6592.add("Empty_35_Cell");
        positionsH6592.add("Empty_3_Cell");
        positionsH6592.add("Empty_7_Cell");
        positionsH6592.add("Empty_27_Cell");
        positionsH6592.add("Empty_30_Cell");
        positionsH6592.add("Furniture_5_Cell");
        positionsH6592.add("Empty_109_Cell");
        positionsH6592.add("Empty_30_Cell");
        positionsH6592.add("Furniture_5_Cell");

        HouseModel house6592 = new HouseModel("house6592", positionsH6592);

        for (int i = 0; i < positionsH6592.size(); i++) {
            house6592.cellPicked.put(positionsH6592.get(i), new HashMap<>());
            house6592.numberOfDifferentCellsPicked.put(positionsH6592.get(i), 0);
        }


        List<String> positionsH18753 = new ArrayList<>();
        positionsH18753.add("cell_73_Cell");
        positionsH18753.add("cell_43_Cell");
        positionsH18753.add("furniture_0_Cell");
        positionsH18753.add("cell_39_Cell");
        positionsH18753.add("cell_34_Cell");
        positionsH18753.add("cell_108_Cell");
        positionsH18753.add("cell_44_Cell");
        positionsH18753.add("furniture_12_Cell");
        positionsH18753.add("cell_35_Cell");
        positionsH18753.add("cell_69_Cell");

        HouseModel house18753 = new HouseModel("house18753", positionsH18753);

        for (int i = 0; i < positionsH18753.size(); i++) {
            house18753.cellPicked.put(positionsH18753.get(i), new HashMap<>());
            house18753.numberOfDifferentCellsPicked.put(positionsH18753.get(i), 0);
        }


        List<String> positionsH94 = new ArrayList<>();
        positionsH94.add("Furniture_23_Cell");
        positionsH94.add("Furniture_23_Cell");
        positionsH94.add("Empty_191_Cell");
        positionsH94.add("Empty_45_Cell");
        positionsH94.add("Furniture_3_Cell");
        positionsH94.add("Empty_156_Cell");
        positionsH94.add("Furniture_14_Cell");
        positionsH94.add("Furniture_14_Cell");
        positionsH94.add("Empty_45_Cell");
        positionsH94.add("Empty_174_Cell");

        HouseModel house94 = new HouseModel("house94", positionsH94);

        for (int i = 0; i < positionsH94.size(); i++) {
            house94.cellPicked.put(positionsH94.get(i), new HashMap<>());
            house94.numberOfDifferentCellsPicked.put(positionsH94.get(i), 0);
        }


        List<String> positionsH24 = new ArrayList<>();
        positionsH24.add("Furniture_5_Cell");
        positionsH24.add("Empty_12_Cell");
        positionsH24.add("Empty_36_Cell");
        positionsH24.add("Empty_146_Cell");
        positionsH24.add("Empty_175_Cell");
        positionsH24.add("Empty_195_Cell");
        positionsH24.add("Empty_142_Cell");
        positionsH24.add("Empty_16_Cell");
        positionsH24.add("Empty_56_Cell");
        positionsH24.add("Furniture_25_Cell");

        HouseModel house24 = new HouseModel("house24", positionsH24);

        for (int i = 0; i < positionsH24.size(); i++) {
            house24.cellPicked.put(positionsH24.get(i), new HashMap<>());
            house24.numberOfDifferentCellsPicked.put(positionsH24.get(i), 0);
        }


        // House model 5 is same as 18753

        HouseModel secondHouse18753 = new HouseModel("house18753", positionsH18753);

        for (int i = 0; i < positionsH18753.size(); i++) {
            secondHouse18753.cellPicked.put(positionsH18753.get(i), new HashMap<>());
            secondHouse18753.numberOfDifferentCellsPicked.put(positionsH18753.get(i), 0);
            System.out.println(positionsH18753.get(i));
        }


        Houses houses = new Houses();
        houses.listHouses.add(house6592);
        houses.listHouses.add(house18753);
        houses.listHouses.add(house94);
        houses.listHouses.add(house24);
        houses.listHouses.add(secondHouse18753);


        try {
            List<Path> jsonFiles = main.ListFilesByStream();
            houses = main.ParseJSONArray(houses, jsonFiles);
        } catch (java.io.IOException e) {

        }
        houses = calculateCellPickingPercentage(houses);

        System.out.println(main.printJSON(houses.listHouses.get(0)));


    }
}