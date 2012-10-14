package gHeat;


import java.util.ArrayList;
import java.util.List;

public class ListSearch {

    private PointLatLng _topLeftBound;
    private PointLatLng _lowerRightBound;
    List<PointLatLng> _list;
    private List<Thread> _threadTracking;
    private Object _threadLocker = new Object();
    private List<PointLatLng> _returnList;

    public class ListInstructions {
        public ListInstructions(int startSearchIndex, int endSearchIndex) {
            this.startSearchIndex = startSearchIndex;
            this.endSearchIndex = endSearchIndex;
        }

        public int startSearchIndex;
        public int endSearchIndex;
    }

    public ListSearch(List<PointLatLng> list, PointLatLng topLeftBound, PointLatLng lowerRightBound) {
        _topLeftBound = topLeftBound;
        _lowerRightBound = lowerRightBound;
        _list = list;
        _threadTracking = new ArrayList<Thread>();
        _returnList = new ArrayList<PointLatLng>();
    }

    public List<PointLatLng> GetMatchingPoints() throws InterruptedException {
        Thread newThread;
        int split = _list.size() / Runtime.getRuntime().availableProcessors();

        //Create a thread for every processor on the computer
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            newThread = new DivideAndConquer(_list, new ListInstructions(i * split, i * split + split), _topLeftBound, _lowerRightBound);
            newThread.run();
            _threadTracking.add(newThread);
        }

        //Wait for each thread to complete searching
        for (Thread thread : _threadTracking) {
            thread.join();
            synchronized (_threadLocker) {
                _returnList.addAll(((DivideAndConquer) thread).getTempList());
            }
        }

        return _returnList;
    }
}
