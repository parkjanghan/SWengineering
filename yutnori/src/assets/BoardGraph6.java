package assets;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoardGraph6 implements BoardGraph {

    private final Map<Integer, Point> nodePositions = new HashMap<>();
    private final List<int[]> edges = new ArrayList<>();
    private final Set<Integer> clickableNodes = new HashSet<>();

    public BoardGraph6() {
        setupBoardGraph();
    }

    public void setupBoardGraph() {
        
        int size = 6;
        int middleX = 400, middleY = 300;
        int radius = 250;
        double angleStep = 2 * Math.PI / 6;
        double offset = Math.PI / 2;

        // 육각형 꼭짓점 위치 계산
        Point[] corners = new Point[6];
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 2 + i * angleStep + offset; // 반시계 방향 회전
            int x = (int) (middleX + radius * Math.cos(angle));
            int y = (int) (middleY - radius * Math.sin(angle));
            corners[i] = new Point(x, y);
        }

        int nodeId = 1;

        // 꼭짓점 포함한 외곽 노드 1~30 생성
        for (int side = 0; side < 6; side++) {
            Point from = corners[side];
            Point to = corners[(side + 1) % 6];

            int limit = (side == 5) ? size : size - 1;

            for (int j = 0; j < limit; j++) {
                double t = j / (size - 1.0);
                int x = (int) (from.x * (1 - t) + to.x * t);
                int y = (int) (from.y * (1 - t) + to.y * t);
                nodePositions.put(nodeId++, new Point(x, y));
            }
        }


        Point center = new Point(middleX, middleY);
        nodePositions.put(43, center);

        addDiagonalNodes(1, 32, 31);
        addDiagonalNodes(6, 33, 34);
        addDiagonalNodes(11, 35, 36);
        addDiagonalNodes(16, 37, 38);
        addDiagonalNodes(21, 39, 40);
        addDiagonalNodes(26, 42, 41);

        // 30번과 연결되는 노드 1번
        Point node30 = nodePositions.get(30);
        Point node16 = nodePositions.get(16);
//

        // 출발 전 대기 노드

        int startX = node30.x;
        int startY = node30.y;
        Point node0 = new Point(node30.x -60, node30.y - 60);
        nodePositions.put(0, node0);


    }

    private void addDiagonalNodes(int corner, int mid1Id, int mid2Id) {
        Point start = nodePositions.get(corner);
        Point center = nodePositions.get(43);

        double t1 = 0.33;
        double t2 = 0.66;

        int x1 = (int) (start.x * (1 - t1) + center.x * t1);
        int y1 = (int) (start.y * (1 - t1) + center.y * t1);
        nodePositions.put(mid1Id, new Point(x1, y1));

        int x2 = (int) (start.x * (1 - t2) + center.x * t2);
        int y2 = (int) (start.y * (1 - t2) + center.y * t2);
        nodePositions.put(mid2Id, new Point(x2, y2));
    }


    private void initEdges() {

        for (int i = 1; i<30; i++) {
            edges.add(new int[]{i, i + 1});
        }


        edges.add(new int[]{43, 31});
        edges.add(new int[]{31, 32});
        edges.add(new int[]{32, 1});

        edges.add(new int[]{6, 33});
        edges.add(new int[]{33, 34});
        edges.add(new int[]{34, 43});

        edges.add(new int[]{11, 35});
        edges.add(new int[]{35, 36});
        edges.add(new int[]{36, 43});

        edges.add(new int[]{16, 37});
        edges.add(new int[]{37, 38});
        edges.add(new int[]{38, 43});

        edges.add(new int[]{21, 39});
        edges.add(new int[]{39, 40});
        edges.add(new int[]{40, 43});

        edges.add(new int[]{43, 41});
        edges.add(new int[]{41, 42});
        edges.add(new int[]{42, 26});

        edges.add(new int[]{30, 1});



    }

    @Override
    public Map<Integer, Point> getNodePositions() {
        return nodePositions;
    }

    @Override
    public List<int[]> getEdges() {
        if (edges.isEmpty()) initEdges();
        return edges;
    }

    @Override
    public Set<Integer> getClickableNodes() {
        return clickableNodes;
    }
}
