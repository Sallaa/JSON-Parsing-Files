import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

class main {

    public static String printJSON(HouseModel house) {

        String message = "";


        JSONObject jsonPositions = new JSONObject();



        for (int i = 0; i < house.positions.size(); i++) {
//            JSONObject occurences = new JSONObject();
//            occurences =
            jsonPositions.put(house.positions.get(i), new JSONObject(house.cellPicked.get(house.positions.get(i))));
        }
        JSONObject json = new JSONObject();
        json.put(house.name, jsonPositions);

//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//        item.put("information", "test");
//        item.put("id", 3);
//        item.put("name", "course1");
//        array.add(item);
//
//        json.put("course", array);

        message = json.toString();

        return message;
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


    public static Houses SaveJSONToObj(Houses houses, int idHouse, int idPosition, String cellPicked){
        String strIdPosition = houses.listHouses.get(idHouse - 1).positions.get(idPosition - 1);

        System.out.println("House is " + houses.listHouses.get(idHouse - 1).name);
        System.out.println("position is " + strIdPosition);
        if (houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).get(cellPicked) != null){
            float occurences = houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).get(cellPicked);
            houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).replace(cellPicked, occurences++);
            System.out.println(occurences++);
        } else {
            houses.listHouses.get(idHouse - 1).cellPicked.get(strIdPosition).put(cellPicked, 1f);

        }
        houses.listHouses.get(idHouse - 1).numberOfDifferentCellsPicked++;
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

        for (int i = 0; i < positionsH6592.size(); i++)
            house6592.cellPicked.put(positionsH6592.get(i), new HashMap<>());


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

        for (int i = 0; i < positionsH18753.size(); i++)
            house18753.cellPicked.put(positionsH18753.get(i), new HashMap<>());


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

        for (int i = 0; i < positionsH94.size(); i++)
            house94.cellPicked.put(positionsH94.get(i), new HashMap<>());

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

        for (int i = 0; i < positionsH24.size(); i++)
            house24.cellPicked.put(positionsH24.get(i), new HashMap<>());

        // House model 5 is same as 18753

        HouseModel secondHouse18753 = new HouseModel("house18753", positionsH18753);

        for (int i = 0; i < positionsH18753.size(); i++)
            secondHouse18753.cellPicked.put(positionsH18753.get(i), new HashMap<>());

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

        System.out.println(main.printJSON(house94));

    }
}