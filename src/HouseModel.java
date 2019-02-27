import java.util.HashMap;
import java.util.List;

public class HouseModel {
    String name;
    HashMap<String, Integer> numberOfDifferentCellsPicked = new HashMap<>();
    List<String> positions;
    HashMap<String, HashMap<String, Double>> cellPicked = new HashMap<>();

    public HouseModel(String name, List<String> positions) {
        this.name = name;
        this.positions = positions;
    }
}