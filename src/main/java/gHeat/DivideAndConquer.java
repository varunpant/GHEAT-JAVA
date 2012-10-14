package gHeat;

import java.util.ArrayList;
import java.util.List;

public class DivideAndConquer extends Thread {

    private ListSearch.ListInstructions instructions;
    private List<PointLatLng> list;
    private PointLatLng topLeftBound;
    private PointLatLng lowerRightBound;

    public List<PointLatLng> getTempList() {
        return tempList;
    }

    public List<PointLatLng> tempList  ;

    public DivideAndConquer(List<PointLatLng> list, ListSearch.ListInstructions instructions, PointLatLng topLeftBound, PointLatLng lowerRightBound) {
        this.list = list;
        this.instructions = instructions;
        this.topLeftBound = topLeftBound;
        this.lowerRightBound = lowerRightBound;
        tempList = new ArrayList<PointLatLng>();
    }

    public void run() {
        ListSearch.ListInstructions listInstruction = instructions;

        PointLatLng point;

        for (int i = listInstruction.startSearchIndex; i < listInstruction.endSearchIndex; i++) {
            point = list.get(i);
            if (point.getLatitude() <= topLeftBound.getLatitude() &&
                    point.getLongitude() >= topLeftBound.getLatitude() &&
                    point.getLongitude() >= lowerRightBound.getLatitude() &&
                    point.getLongitude() <= lowerRightBound.getLongitude())
                tempList.add(point);
        }
    }
}
