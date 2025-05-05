package assets;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoardGraph5 implements BoardGraph {

    private final Map<Integer, Point> nodePositions = new HashMap<>();
    private final List<int[]> edges = new ArrayList<>();
    private final Set<Integer> clickableNodes = new HashSet<>();

    public BoardGraph5() {
        setupBoardGraph();
    }

    public void setupBoardGraph() {
        
        int size = 6;
        int middleX = 400, middleY = 300;
        double angleStep = 2 * Math.PI / 5;
        double offset = angleStep * 1;

        // 오각형 꼭짓점 위치 계산
        Point[] corners = new Point[5];
        for (int i = 0; i < 5; i++) {
            double angle = Math.PI / 2 + i * angleStep + offset;
            corners[i] = new Point(
                    (int) (middleX + 250 * Math.cos(angle)),
                    (int) (middleY - 250 * Math.sin(angle))
            );
        }

        // 꼭짓점 포함한 외곽 노드 1~25 생성
        int nodeId = 1;
        for (int side = 0; side < 5; side++) {
            Point from = corners[side];
            Point to = corners[(side + 1) % 5];

            int limit = (side == 4) ? size : size - 1;

            for (int j = 0; j < limit; j++) {
                double t = j / (size - 1.0);
                int x = (int) (from.x * (1 - t) + to.x * t);
                int y = (int) (from.y * (1 - t) + to.y * t);
                nodePositions.put(nodeId++, new Point(x, y));
            }
        }

        Point center = new Point(middleX, middleY);
        nodePositions.put(36, center);

        addDiagonalNodes(1, 27, 26);
        addDiagonalNodes(6, 28, 29);
        addDiagonalNodes(11, 30, 31);
        addDiagonalNodes(16, 32, 33);
        addDiagonalNodes(21, 35, 34);

        // 25번과 연결되는 노드 1번
        Point node25 = nodePositions.get(25);
        Point node1 = new Point(node25.x -50, node25.y - 50);
        nodePositions.put(1, node1);

        // ✅ 시작 노드 0번 (명시적으로 먼저 설정)
        nodePositions.put(0, new Point(middleX + 240, middleY)); // 말이 시작하는 자

    }

    private void addDiagonalNodes(int corner, int mid1Id, int mid2Id) {
        Point start = nodePositions.get(corner);
        Point center = nodePositions.get(36);

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

        for (int i =1; i < 25; i++) {
            edges.add(new int[]{i, i + 1});
        }


        edges.add(new int[]{36, 26});
        edges.add(new int[]{26, 27});
        edges.add(new int[]{27, 1});

        edges.add(new int[]{6, 28});
        edges.add(new int[]{28, 29});
        edges.add(new int[]{29, 36});

        edges.add(new int[]{11, 30});
        edges.add(new int[]{30, 31});
        edges.add(new int[]{31, 36});

        edges.add(new int[]{16, 32});
        edges.add(new int[]{32, 33});
        edges.add(new int[]{33, 36});

        edges.add(new int[]{36, 34});
        edges.add(new int[]{34, 35});
        edges.add(new int[]{35, 21});

        edges.add(new int[]{25, 1});



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
