import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HouseModel {
    String name;
    int numberOfDifferentCellsPicked = 0;
    List<String> positions = new ArrayList<>();
    HashMap<String, HashMap<String,Float>> cellPicked = new HashMap<>();

    public HouseModel(String name, List<String> positions) {
        this.name = name;
        this.positions = positions;
    }
}