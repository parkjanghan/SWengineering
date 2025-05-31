package assets;

import java.util.List;
import java.util.Map;

public interface BoardGraph {
    void setupBoardGraph();
    Map<Integer, Point> getNodePositions();
    List<int[]> getEdges();
}
