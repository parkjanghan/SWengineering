package assets;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BoardGraph {
    /*
    BoardGraph의 인터페이스 : BoardGraph4, BoardGraph5, BoardGraph6이 인터페이스 구현
    */

    //그래프의 노드와 엣지 설정
    // 각 노드의 위치를 설정하는 메소드
    void setupBoardGraph();

    Map<Integer, Point> getNodePositions();
    List<int[]> getEdges();
    Set<Integer> getClickableNodes();

    Set<Integer> clickableNodes = new HashSet<>();
    
}
