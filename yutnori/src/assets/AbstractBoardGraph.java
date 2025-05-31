package assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBoardGraph implements BoardGraph {
    protected final Map<Integer, Point> nodePositions = new HashMap<>();
    final List<int[]> edges = new ArrayList<>();

    public Map<Integer, Point> getNodePositions() {
        return nodePositions;
    }

    public List<int[]> getEdges() {
        if (edges.isEmpty()) initEdges();
        return edges;
    }

    protected abstract void initEdges();
}