package assets;

import java.util.*;
import java.util.List;

public class BoardGraph5 implements BoardGraph {

    private final Map<Integer, MyPoint> nodePositions = new HashMap<>();
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
        MyPoint[] corners = new MyPoint[5];
        for (int i = 0; i < 5; i++) {
            double angle = Math.PI / 2 + i * angleStep + offset;
            corners[i] = new MyPoint(
                    (int) (middleX + 250 * Math.cos(angle)),
                    (int) (middleY - 250 * Math.sin(angle))
            );
        }

        // 꼭짓점 포함한 외곽 노드 1~25 생성
        int nodeId = 1;
        for (int side = 0; side < 5; side++) {
            MyPoint from = corners[side];
            MyPoint to = corners[(side + 1) % 5];

            int limit = (side == 4) ? size : size - 1;

            for (int j = 0; j < limit; j++) {
                double t = j / (size - 1.0);
                int x = (int) (from.x * (1 - t) + to.x * t);
                int y = (int) (from.y * (1 - t) + to.y * t);
                nodePositions.put(nodeId++, new MyPoint(x, y));
            }
        }

        MyPoint center = new MyPoint(middleX, middleY);
        nodePositions.put(36, center);
        nodePositions.put(0, new MyPoint(800, 150)); // 0번 사용자 말 대기 위치 (출발 전) ✅
        nodePositions.put(-1, new MyPoint(800, 200));
        nodePositions.put(-2, new MyPoint(800, 250));
        nodePositions.put(-3, new MyPoint(800, 300));
        addDiagonalNodes(1, 27, 26);
        addDiagonalNodes(6, 28, 29);
        addDiagonalNodes(11, 30, 31);
        addDiagonalNodes(16, 32, 33);
        addDiagonalNodes(21, 35, 34);

        // 25번과 연결되는 노드 1번
        MyPoint node25 = nodePositions.get(25);
        MyPoint node16 = nodePositions.get(16);
        MyPoint node1 = new MyPoint(node25.x -50, node16.y );
        nodePositions.put(1, node1);

        nodePositions.put(37, new MyPoint(node1.x - 50, node1.y + 50)); // 37번 노드 추가

        // 출발 전 대기 노드
        //nodePositions.put(0, new Point(middleX + 240, middleY));
    }

    private void addDiagonalNodes(int corner, int mid1Id, int mid2Id) {
        MyPoint start = nodePositions.get(corner);
        MyPoint center = nodePositions.get(36);

        double t1 = 0.33;
        double t2 = 0.66;

        int x1 = (int) (start.x * (1 - t1) + center.x * t1);
        int y1 = (int) (start.y * (1 - t1) + center.y * t1);
        nodePositions.put(mid1Id, new MyPoint(x1, y1));

        int x2 = (int) (start.x * (1 - t2) + center.x * t2);
        int y2 = (int) (start.y * (1 - t2) + center.y * t2);
        nodePositions.put(mid2Id, new MyPoint(x2, y2));
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
    public Map<Integer, MyPoint> getNodePositions() {
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