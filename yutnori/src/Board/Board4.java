package Board;

import java.util.HashMap;
import java.util.Map;
public class Board4 implements BoardInterface
{

    private Map<Integer, Node> boardShape; //Map<key,Node>
    private Node startNode;   //시작 노드
    private Node endNode;    //끝 노드

    public Board4()
    {
        this.boardShape = new HashMap<>();
        BoardInit();
        connectNodes();
    }

    private void BoardInit() {
        createNode(1, 500, 500, true, false, false, false);  // Start node
        createNode(2, 450, 500, false, false, false, false);
        createNode(3, 400, 500, false, false, false, false);
        createNode(4, 350, 500, false, false, false, false);
        createNode(5, 300, 500, false, false, false, false);
        createNode(6, 250, 500, false, false, false, true);  // 오른쪽 위
        createNode(7, 250, 450, false, false, false, false);
        createNode(8, 250, 400, false, false, false, false);
        createNode(9, 250, 350, false, false, false, false);
        createNode(10, 250, 300, false, false, false, false);
        createNode(11, 250, 250, false, false, false, true); // 왼쪽 위
        createNode(12, 300, 250, false, false, false, false);
        createNode(13, 350, 250, false, false, false, false);
        createNode(14, 400, 250, false, false, false, false);
        createNode(15, 450, 250, false, false, false, false);
        createNode(16, 500, 250, false, false, false, true); // 왼쪽 아래
        createNode(17, 500, 300, false, false, false, false);
        createNode(18, 500, 350, false, false, false, false);
        createNode(19, 500, 400, false, false, false, false);
        createNode(20, 500, 450, false, false, false, false);

        // Diagonal path from bottom-right corner
        createNode(21, 300, 450, false, false, false, false);
        createNode(22, 350, 400, false, false, false, false);

        // Diagonal path from top-right corner
        createNode(23, 350, 300, false, false, false, false);
        createNode(24, 300, 350, false, false, false, false);

        // Diagonal path from top-left corner
        createNode(25, 300, 300, false, false, false, false);
        createNode(26, 350, 350, false, false, false, false);

        // Diagonal path to top-left corner
        createNode(27, 400, 350, false, false, false, false);
        createNode(28, 450, 300, false, false, false, false);

        // Center node
        createNode(29, 375, 375, false, false, true, false);

        // End node (same as start node in traditional Yut, but marked as end)
        createNode(30, 500, 500, false, true, false, false);

    }
    /*
    다음 윷의 결과에 따라서 이동 가능한 node들을 저장 할 지?
    ex) 6번 노드에서 next = 7,21번 Node만 저장할 지
        vs 6번노드에서:
            next = if 도 -> 7,21/ if 개 -> 8/ if 걸 ->9 / if 윷 -> 10
         이렇게 저장해야 하는지? 말 움직이는 logic & 이동할 노드 선택 로직에 따라
         달라질 듯 합니다. + 후자로 하면 추후에 테스트 코드 쓰기도 좀 편해 지기는 합니다....
         ex) assertthat(node4.nextnode().isEqualto(3,4)).....

    */
    
    /*윷놀이 규칙 중에 코너나 중심점에 있으면 지름길로 가야하는 규칙이 있어서
    일단 적용해서 createConnection에 넣어 보았습니다..
    */

    /*칸에 해당하는 노드들 생성
    //공지 안내서에는 오른쪽 아래가 출발 지점인 것 같은데 주석 달아놓으신 노드 위치가 맞는지 애매해서
    확인 한번 부탁드립니다 -> 이에 맞게 주석 수정했습니다!0410*/
    



    @Override
    public Node getStartPoint() {
        return this.startNode;
    }

    @Override
    public void setStartPoint(Node startPoint) {
        this.startNode = startPoint;
    }

    @Override
    public Node getEndPoint() {
        return this.endNode;
    }

    @Override
    public void setEndPoint(Node endPoint) {
        this.endNode = endPoint;
    }

    @Override
    public Map<Integer, Node> getNodes() {
        return boardShape;
    }

    @Override
    public void setNodes(Map<Integer, Node> nodes) {
         this.boardShape = nodes;
    }

    @Override
    public void createNode(int key, int x_pos, int y_pos, boolean isStart, boolean isEnd, boolean isCenter,
                           boolean isCorner) {
        /*boolean isStart = (key == 1);
        boolean isEnd = (key == 30);
        boolean isCenter = (key == 29);
        boolean isCorner = (key == 6 || key == 11 || key == 16);
        */


        Node node = new Node(key, x_pos, y_pos, isStart, isEnd, isCenter, isCorner);
        boardShape.put(key, node);

        if (isStart) this.startNode = node;
        if (isEnd) this.endNode = node;
    }

    private void connectNodes() {
        //변에 있는 노드들 + 코너라면 일반적인 움직임
        for(int i =1 ; i <20; i ++)
        {
            createConnection(i, i+1);
            if(i>1)
            {
                connectBackdo(i,i-1);
            }
            else {
                connectBackdo(1,20);
            }
        }
        createConnection(20,1);
        connectBackdo(20,19);

        connectSpecialPath(6, 21);

        // From corner 11 (top right)
        connectSpecialPath(11, 25);

        createConnection(21, 22);
        connectBackdo(21, 6);

        createConnection(22, 29);
        connectBackdo(22, 21);


        createConnection(25, 26);
        connectBackdo(25, 11);

        createConnection(26, 29);
        connectBackdo(26, 25);


        createConnection(29, 27);
        //중앙에서 빽도 처리를 어떻게 해야?
        //일반적으로 안된다고는 하는데 어떻게 처리해야 할지?

        createConnection(27, 28);
        connectBackdo(27, 29);

        createConnection(28, 16);
        connectBackdo(28, 27);  // 빽도 connection


        connectFinish();

    }

    private void connectFinish() {

        for (int i = 16; i <= 20; i++) {
            Node node = boardShape.get(i);

            for (int j = 1; j <= 5; j++) {
                if (i + j > 20) {
                    // This move would go past the starting point
                    node.getNextMap().put(j, endNode);
                }
            }
        }
    }

    private void connectSpecialPath(int from_key, int to_key) {
        Node from = boardShape.get(from_key);
        Node to = boardShape.get(to_key);
        if(from == null || to == null)
        {
            return;
        }
        from.getNextMap().put(1,to);
        //코너에서 빽도는 따로 설정하기로
    }

    private void connectBackdo(int from_key, int to_key) {
        Node from = boardShape.get(from_key);
        Node to = boardShape.get(to_key);

        if(from == null || to == null)
        {
            return;
        }
        from.getNextMap().put(-1, to);
    }

    @Override
    public void createConnection(int from_key, int to_key) {
        Node from = boardShape.get(from_key);
        Node to = boardShape.get(to_key);

        if (from == null || to == null) return;

        /*윷놀이 규칙에 따라서 특정 노드에 있을 때만 특수하게 저장하는 걸로
        코드 넣어보았습니다
        ex) 6번 노드면 21->22->29->23->24 이 방향으로 가야함
         */
        Map<Integer, Integer[]> shortcut = Map.of(
                6, new Integer[]{21, 22, 29, 23, 24},
                11, new Integer[]{25, 26, 29, 27, 28},
                29, new Integer[]{27, 28},
                16, new Integer[]{17, 18, 19, 20}
        );

        if (shortcut.containsKey(from_key)) {
            Integer[] path = shortcut.get(from_key);
            for (int i = 0; i < path.length; i++) {
                if (to_key == path[i]) {
                    from.getNextMap().put(i + 1, to); // i+1->도개걸윷모
                    return;
                }
            }
        }
        
        //TODO 일반적인 경우 수정 필요..
        //22->29 같은 경우의 반영이 아직 안됨
        /*int distance = to_key - from_key;  // 이동한 거리
        if (distance >= 1 && distance <= 5) {
            from.getNextMap().put(distance, to); // 이동한 만큼의 노드 저장
        }*/
        if (from_key >= 1 && from_key <= 20) {
            if (to_key == from_key + 1 || (from_key == 20 && to_key == 1)) {
                from.getNextMap().put(1, to); // 도 (1 step)

                //도 개 걸 윷 모모
                for (int steps = 2; steps <= 5; steps++) {
                    int nextKey = from_key + steps;
                    if (nextKey > 20) nextKey -= 20; // Wrap around the main path
                    Node nextNode = boardShape.get(nextKey);
                    if (nextNode != null) {
                        from.getNextMap().put(steps, nextNode);
                    }
                }
                return;
            }
        }
        // 대각선 경로로
        if ((from_key >= 21 && from_key <= 24) || (from_key >= 25 && from_key <= 28)) {
            if (to_key == from_key + 1 ||
                    (from_key == 24 && to_key == 25) ||
                    (from_key == 28 && to_key == 15)) {
                from.getNextMap().put(1, to); // 도 (1 step)
                return;
            }
        }

        //가운데
        if (from_key == 29) {
            if (to_key == 27 || to_key == 28) {
                from.getNextMap().put(1, to); // 도 (1 step)
                return;
            }
        }

    }
    /*
    @Override
  public void createConnection(int from_key, int to_key) {
    Node from = boardShape.get(from_key);
    Node to = boardShape.get(to_key);

    if (from == null || to == null) return;

    //코너, 중심부 별로 따로 처리

    //오른 쪽위
    if (from_key == 6) {
        if (to_key == 7) {
            from.getNextMap().put(1, to); // 정상 경로 (도)
        }
        else if (to_key == 21) {

            from.getNextMap().put(1, to); // 대각선 (도)
        }
    }
    //왼쪽 위
    else if (from_key == 11) {
        if (to_key == 12) {
            from.getNextMap().put(1, to); // 정상 경로 (도)
        } else if (to_key == 25) {

            from.getNextMap().put(1, to); // 대각선 (도)
        }
    }
    //왼쪽 아래
    else if (from_key == 16) {
        if (to_key == 17) {
            from.getNextMap().put(1, to); // 정상 경로 (도)
        }
    }
    else if (from_key == 29) { // 가운대로
        if (to_key == 27) {
            from.getNextMap().put(1, to); // 가운데에서 출발 (도)
        }
    }
    else {
        // 일반 변의 노드들
        if (from_key >= 1 && from_key <= 20) {
            if (to_key == from_key + 1 || (from_key == 20 && to_key == 1)) {
                from.getNextMap().put(1, to); //  (도)

                //  (개, 걸, 윷, 모)
                for (int steps = 2; steps <= 5; steps++) {
                    int nextKey = from_key + steps;
                    if (nextKey > 20) nextKey = nextKey - 20; //
                    Node nextNode = boardShape.get(nextKey);
                    if (nextNode != null) {
                        from.getNextMap().put(steps, nextNode);
                    }
                }
            }
        }
        // 대각선 연결들
        else if ((from_key >= 21 && from_key <= 24) ||
                 (from_key >= 25 && from_key <= 28)) {
            if (to_key == from_key + 1 ||
                (from_key == 24 && to_key == 25) ||
                (from_key == 28 && to_key == 15)) {
                from.getNextMap().put(1, to); // Move one step (도)


            }
        }
    }
}
     */
}
