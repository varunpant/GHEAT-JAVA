package gheat.datasources;


import gheat.DataPoint;
import gheat.PointLatLng;
import gheat.Projections;

public interface HeatMapDataSource {

    PointLatLng[] GetList(DataPoint tlb, DataPoint lrb, int zoom, Projections _projection);
}
