package Board;

import java.util.Map;
public class Board4 implements BoardInterface
{

    private Map<Integer, Node> boardShape; //Map<key,Node>
    private Node startNode;   //시작 노드
    private Node endNode;    //끝 노드


    /*TODO
       다음 윷을 저장할 때 바로 이웃한 윷만 저장하는 지 아니면
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
    확인 한번 부탁드립니다..
    
    private Node1 = new Node(1, x, y, false, false, false, false);
    //Node1 = 시작점
    //오른쪽 변
    private Node2 = new Node(2, x, y, false, false, false, false);
    private Node3 = new Node(3, x, y, false, false, false, false);
    private Node4= new Node(4, x, y, false, false, false, false);
    private Node5 = new Node(5, x, y, false, false, false, false);

    //오른쪽 아래 코너
    private Node6 = new Node(6, x, y, false, false, false, false);

    //윗 변
    private Node7 = new Node(7, x, y, false, false, false, false);
    private Node8 = new Node(8, x, y, false, false, false, false);
    private Node9 = new Node(9, x, y, false, false, false, false);
    private Node10 = new Node(10, x, y, false, false, false, false);
    //Node 11 = 오른쪽 위 코너
    private Node11 = new Node(11, x, y, false, false, false, false);

    //왼쪽 변
    private Node12 = new Node(12, x, y, false, false, false, false);
    private Node13 = new Node(13, x, y, false, false, false, false);
    private Node14 = new Node(14, x, y, false, false, false, false);
    private Node15 = new Node(15, x, y, false, false, false, false);

    //Node 16= 왼쪽 위 코너
    private Node16 = new Node(16, x, y, false, false, false, false);

    //아래
    private Node17 = new Node(17, x, y, false, false, false, false);
    private Node18 = new Node(18, x, y, false, false, false, false);
    private Node19 = new Node(19, x, y, false, false, false, false);
    private Node20 = new Node(20, x, y, false, false, false, false);
    //Node1이 시작점/ Node20에서 Node30이랑 이어저야

    //분기점들
    private Node21 = new Node(21, x, y, false, false, false, false);
    private Node22 = new Node(22, x, y, false, false, false, false);
    //
    private Node23 = new Node(23, x, y, false, false, false, false);
    private Node24 = new Node(24, x, y, false, false, false, false);
    //
    private Node25 = new Node(25, x, y, false, false, false, false);
    private Node26 = new Node(26, x, y, false, false, false, false);
    //
    private Node27 = new Node(27, x, y, false, false, false, false);
    private Node28 = new Node(28, x, y, false, false, false, false);
    //
    //Node 29 = Center
    private Node29 = new Node(29, x, y, false, false, false, false);

    private Node30 = new Node(30, x, y, false, false, false, false);
    //마지막에 40번 노드에 들어와야 게임이 끝남
     */

    //TODO 각 코너, 중심점에서 addNext하기

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
        return Map.of();
    }

    @Override
    public void setNodes(Map<Integer, Node> nodes) {
         this.boardShape = nodes;
    }

    @Override
    public void createNode(int key, int x_pos, int y_pos) {
        boolean isStart = (key == 1);
        boolean isEnd = (key == 30);
        boolean isCenter = (key == 29);
        boolean isCorner = (key == 6 || key == 11 || key == 16);

        Node node = new Node(key, x_pos, y_pos, isStart, isEnd, isCenter, isCorner);
        boardShape.put(key, node);

        if (isStart) this.startNode = node;
        if (isEnd) this.endNode = node;
    }

    @Override
    public void createConnection(int from_key, int to_key) {
        Node from = boardShape.get(from_key);
        Node to = boardShape.get(to_key);

        if (from == null || to == null) return;

        /*윷놀이 규칙에 따라서 특정 노드에 있을 때만 특수하게 저장하는 걸로
        코드 넣어보았는데 이상하다싶으면 수정해주시면 감사하겠습니다..
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

        int distance = to_key - from_key;  // 이동한 거리
        if (distance >= 1 && distance <= 5) {
            from.getNextMap().put(distance, to); // 이동한 만큼의 노드 저장
        }

    }
}
