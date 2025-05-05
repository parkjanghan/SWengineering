package assets;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BoardGraph4 implements BoardGraph {

    private final Map<Integer, Point> nodePositions = new HashMap<>();
    private final List<int[]> edges = new ArrayList<>();
    private final Set<Integer> clickableNodes = new HashSet<>();

    public BoardGraph4() {
        setupBoardGraph(); // ✅ 보드 정보 초기화
    }

    public void setupBoardGraph() {
        int size = 5;    // 외곽 변의 노드 개수
        int gap = 80;    // 노드 간 간격
        int startX = 500, startY = 500;

        int middleX = startX - size * gap / 2;
        int middleY = startY - size * gap / 2;

        nodePositions.put(0, new Point(startX + 100, startY + 100)); // 말 대기 위치 (출발 전) ✅

        int index = 1;

        // ✅ 외곽 사각형 노드 (시계 방향)
        for (int i = 0; i <= size; i++) nodePositions.put(index++, new Point(startX, startY - i * gap));              // 위쪽
        for (int i = 1; i <= size; i++) nodePositions.put(index++, new Point(startX - i * gap, startY - size * gap)); // 오른쪽
        for (int i = 1; i <= size; i++) nodePositions.put(index++, new Point(startX - size * gap, startY - (size - i) * gap)); // 아래쪽
        for (int i = 1; i <= size - 1; i++) nodePositions.put(index++, new Point(startX - (size - i) * gap, startY)); // 왼쪽

        // ✅ 대각선 지름길 노드
        int crossGap = (startX - middleX) / 3;
        int crossCount = 2;

        // ↗ 지름길 (6 → 21 → 22 → 29)
        for (int i = 1; i <= crossCount; i++)
            nodePositions.put(index++, new Point(startX - i * crossGap, startY - size * gap + i * crossGap));

        // ↙ 지름길 (29 → 23 → 24 → 16)
        for (int i = 1; i <= crossCount; i++)
            nodePositions.put(index++, new Point(middleX - i * crossGap, middleX + i * crossGap));

        // ↘ 지름길 (11 → 25 → 26 → 29)
        for (int i = 1; i <= crossCount; i++)
            nodePositions.put(index++, new Point(startX - size * gap + i * crossGap, startY - size * gap + i * crossGap));

        // ↖ 지름길 (29 → 27 → 28 → 1)
        for (int i = 1; i <= crossCount; i++)
            nodePositions.put(index++, new Point(middleX + i * crossGap, middleY + i * crossGap));

        // ✅ 중앙 노드 (29), 결승점 (30)
        nodePositions.put(index++, new Point(middleX, middleY));       // 29
        nodePositions.put(index++, new Point(startX, startY + 50));    // 30
    }

    private void initEdges() {
        // ✅ 외곽 사각형 순차 연결 (1~20)
        for (int i = 1; i < 20; i++) {
            edges.add(new int[]{i, i + 1});
        }
        edges.add(new int[]{20, 1}); // 30: 결승점

        // ✅ 지름길 경로
        edges.add(new int[]{6, 21});
        edges.add(new int[]{21, 22});
        edges.add(new int[]{22, 29});

        edges.add(new int[]{29, 23});
        edges.add(new int[]{23, 24});
        edges.add(new int[]{24, 16});

        edges.add(new int[]{11, 25});
        edges.add(new int[]{25, 26});
        edges.add(new int[]{26, 29});

        edges.add(new int[]{29, 27});
        edges.add(new int[]{27, 28});
        edges.add(new int[]{28, 1});
    }

    @Override
    public Map<Integer, Point> getNodePositions() {
        return nodePositions;
    }

    @Override
    public List<int[]> getEdges() {
        if (edges.isEmpty()) initEdges(); // ✅ 필요 시 lazy 초기화
        return edges;
    }

    @Override
    public Set<Integer> getClickableNodes() {
        return clickableNodes;
    }
}
