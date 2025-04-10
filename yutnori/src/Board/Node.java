package Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node
{
    /**
     * 노드, 즉 판의 칸에 해당하는 객체
     * @param id 각 노드(칸의) 번호
     * @param next: 이동 가능한 다음 노드들의 리스트/맵
     * @param pos_x x좌표
     * @param pos_y y좌표
     * @param isStartPoint 시작점인지
     * @param isEndPoint 결승점인지  //시작점과 결증점 중 불필요하면 하나 삭제 해도 됩니다
     * @param isCenter 가운데있는 칸(노드)인지
     * @param isCorner 모서리에 있는가?
     */
    public int id;  //노드 번호
    private int pos_x;  //x좌표
    private int pos_y;   //y좌표

    private final boolean isStartPoint;
    private final boolean isEndPoint;

    private final boolean isCenter; //가운데 있는 점인지?
    private final boolean isCorner; //방향이 갈리는 점인지?


    private Map<Integer, Node> next; //이동 가능한 노드  ...HashMap으로 변경 어떤지?


    public Node(int id, int x, int y, boolean isStartPoint, boolean isEndPoint, boolean isCenter, boolean isCorner) {
        this.id = id;
        this.pos_x = x;
        this.pos_y = y;
        this.isStartPoint = isStartPoint;
        this.isEndPoint = isEndPoint;
        this.isCenter = isCenter;
        this.isCorner = isCorner;
        this.next = new HashMap<>();
    }

    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public boolean isStartPoint() {
        return isStartPoint;
    }

    public boolean isEndPoint() {
        return isEndPoint;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public boolean isCorner() {
        return isCorner;
    }

    //next 쓰기 위한 추가
    public Map<Integer, Node> getNextMap() {
        return next;
    }

    public void setNextMap(Map<Integer, Node> next)
    {
        this.next = next;
    }

    public Node getNextNode(int yut)
    {
        return next.get(yut);
    }






}
