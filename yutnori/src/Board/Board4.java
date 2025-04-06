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

    /*칸에 해당하는 노드들 생성
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

    }

    @Override
    public void createNode(int key, int x_pos, int y_pos) {

    }

    @Override
    public void createConnection(int from_key, int to_key) {

    }
}
