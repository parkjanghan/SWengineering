package board.board;

import java.util.*;

public class Board5 implements BoardInterface{

    final Map<Integer, Node> boardShape;


    public Board5()
    {
        this.boardShape = new HashMap<>();
        InitNodes();
        SetConnections();


    }


    private void InitNodes()
    {
        createNode(1, 500, 500, true, false, false, false);  // 임시 좌표
        createNode(2, 450, 500, false, false, false, false);
        createNode(3, 400, 500, false, false, false, false);
        createNode(4, 350, 500, false, false, false, false);
        createNode(5, 300, 500, false, false, false, false);
        createNode(6, 250, 500, false, false, false, false);
        createNode(7, 200, 500, false, false, false, false);
        createNode(8, 150, 500, false, false, false, false);
        createNode(9, 100, 500, false, false, false, false);
        createNode(10, 50, 500, false, false, false, false);
        createNode(11, 50, 450, false, false, false, false);
        createNode(12, 100, 450, false, false, false, false);
        createNode(13, 150, 450, false, false, false, false);
        createNode(14, 200, 450, false, false, false, false);
        createNode(15, 250, 450, false, false, false, false);
        createNode(16, 300, 450, false, false, false, false);
        createNode(17, 350, 450, false, false, false, false);
        createNode(18, 400, 450, false, false, false, false);
        createNode(19, 450, 450, false, false, false, false);
        createNode(20, 500, 450, false, false, false, false);
        createNode(21, 550, 450, false, false, false, false);
        createNode(22, 600, 450, false, false, false, false);
        createNode(23, 650, 450, false, false, false, false);
        createNode(24, 650, 400, false, false, false, false);
        createNode(25, 600, 400, false, false, false, false);
        createNode(26, 550, 400, false, false, false, false);
        createNode(27, 500, 400, false, false, false, false);
        createNode(28, 450, 400, false, false, false, false);
        createNode(29, 400, 400, false, false, false, false);
        createNode(30, 350, 400, false, false, false, false);
        createNode(31, 300, 400, false, false, false, false);
        createNode(32, 250, 400, false, false, false, false);
        createNode(33, 200, 400, false, false, false, false);
        createNode(34, 150, 400, false, false, false, false);
        createNode(35, 100, 400, false, false, false, false);
        createNode(36, 50, 400, false, false, false, false);
        createNode(37, 500, 500, false, true, false, false); 

    }

    /*
    private void InitNodes()
    {
        createNode(1, x_pos, y_pos, false, false, false, false);//시작 노드

        createNode(2, x_pos, y_pos, false, false, false, false);
        createNode(3, x_pos, y_pos, false, false, false, false);
        createNode(4, x_pos, y_pos, false, false, false, false);
        createNode(5, x_pos, y_pos, false, false, false, false);

        createNode(6, x_pos, y_pos, false, false, false, false);//코너

        createNode(7, x_pos, y_pos, false, false, false, false);
        createNode(8, x_pos, y_pos, false, false, false, false);
        createNode(9, x_pos, y_pos, false, false, false, false);
        createNode(10, x_pos, y_pos, false, false, false, false);

        createNode(11, x_pos, y_pos, false, false, false, false);//코너

        createNode(12, x_pos, y_pos, false, false, false, false);
        createNode(13, x_pos, y_pos, false, false, false, false);
        createNode(14, x_pos, y_pos, false, false, false, false);
        createNode(15, x_pos, y_pos, false, false, false, false);

        createNode(16, x_pos, y_pos, false, false, false, false);//코너

        createNode(17, x_pos, y_pos, false, false, false, false);
        createNode(18, x_pos, y_pos, false, false, false, false);
        createNode(19, x_pos, y_pos, false, false, false, false);
        createNode(20, x_pos, y_pos, false, false, false, false);

        createNode(21, x_pos, y_pos, false, false, false, false);//코너

        createNode(22, x_pos, y_pos, false, false, false, false);
        createNode(23, x_pos, y_pos, false, false, false, false);
        createNode(24, x_pos, y_pos, false, false, false, false);
        createNode(25, x_pos, y_pos, false, false, false, false);

        createNode(26, x_pos, y_pos, false, false, false, false);
        createNode(27, x_pos, y_pos, false, false, false, false);

        createNode(28, x_pos, y_pos, false, false, false, false);
        createNode(29, x_pos, y_pos, false, false, false, false);

        createNode(30, x_pos, y_pos, false, false, false, false);
        createNode(31, x_pos, y_pos, false, false, false, false);

        createNode(32, x_pos, y_pos, false, false, false, false);
        createNode(33, x_pos, y_pos, false, false, false, false);

        createNode(34, x_pos, y_pos, false, false, false, false);
        createNode(35, x_pos, y_pos, false, false, false, false);

        createNode(36, x_pos, y_pos, false, false, false, false);//중심

        createNode(37, x_pos, y_pos, false, true, false, false);//최종 노드

    }
     */

    private void createNode(int key,int x_pos, int y_pos, boolean isStartPoint,
                            boolean isEndPoint, boolean isCenter, boolean isCorner)
    {
        Node node = new Node(key, x_pos, y_pos, isStartPoint,
                isEndPoint, isCenter, isCorner);
        boardShape.put(key, node);
    }

    private void SetConnections() {

        //1번 노드
        boardShape.get(1).addNext_nodes(0, new ArrayList<>(List.of(25,26))); //0은 빽도
        boardShape.get(1).addNext_nodes(1, new ArrayList<>(List.of(2))); //도
        boardShape.get(1).addNext_nodes(2, new ArrayList<>(List.of(3))); //개
        boardShape.get(1).addNext_nodes(3, new ArrayList<>(List.of(4)));//걸
        boardShape.get(1).addNext_nodes(4, new ArrayList<>(List.of(5)));//윷
        boardShape.get(1).addNext_nodes(5, new ArrayList<>(List.of(6)));//모


        for(int i = 2; i<=5; i++) //2~5번 노드
        {
            setSingleConnection(i);
        }
        for(int i = 7; i<=10; i++) //7~10번 노드
        {
            setSingleConnection(i);
        }
        for(int i = 12; i<=15; i++) //12~15번 노드
        {
            setSingleConnection(i);
        }
        for(int i = 17; i<=20; i++) //17~20번 노드
        {
            setSingleConnection(i);
        }

        for(int i=22; i<=25; i++)
        {
            for(int j =-1; j<6;j ++)
            {

                if(i+j>25)
                {
                    setConnection_one_dest(i,j,37); //끝나면
                }
                else if(j==0){
                    setConnection_one_dest(i,j,i-1); //빽도
                }
                else {
                    setConnection_one_dest(i,j,i+j); //일반적인 경우
                }

            }
        }

        // 6번 노드
        boardShape.get(6).addNext_nodes(0, new ArrayList<>(List.of(5)));  // 빽도
        boardShape.get(6).addNext_nodes(1, new ArrayList<>(List.of(28,7)));
        boardShape.get(6).addNext_nodes(2, new ArrayList<>(List.of(29,8)));
        boardShape.get(6).addNext_nodes(3, new ArrayList<>(List.of(36,9)));
        boardShape.get(6).addNext_nodes(4, new ArrayList<>(List.of(34,10)));
        boardShape.get(6).addNext_nodes(5, new ArrayList<>(List.of(35,11))); // 중앙까지

        // 11번 노드
        boardShape.get(11).addNext_nodes(0, new ArrayList<>(List.of(10)));//빽도
        boardShape.get(11).addNext_nodes(1, new ArrayList<>(List.of(30,12)));//도
        boardShape.get(11).addNext_nodes(2, new ArrayList<>(List.of(31,13)));//개
        boardShape.get(11).addNext_nodes(3, new ArrayList<>(List.of(36,14)));//걸
        boardShape.get(11).addNext_nodes(4, new ArrayList<>(List.of(34,15)));//윷
        boardShape.get(11).addNext_nodes(5, new ArrayList<>(List.of(35,16)));//모

        //16번
        boardShape.get(16).addNext_nodes(0, new ArrayList<>(List.of(15))); //빽도
        boardShape.get(16).addNext_nodes(1, new ArrayList<>(List.of(32,17))); //도
        boardShape.get(16).addNext_nodes(2, new ArrayList<>(List.of(33,18))); //개
        boardShape.get(16).addNext_nodes(3, new ArrayList<>(List.of(36,19)));//걸
        boardShape.get(16).addNext_nodes(4, new ArrayList<>(List.of(34,20)));//윷
        boardShape.get(16).addNext_nodes(5, new ArrayList<>(List.of(35,21)));//모

        //21번
        boardShape.get(21).addNext_nodes(0, new ArrayList<>(List.of(20,35))); //빽도-20 or 35??
        boardShape.get(21).addNext_nodes(1, new ArrayList<>(List.of(22))); //도
        boardShape.get(21).addNext_nodes(2, new ArrayList<>(List.of(23))); //개
        boardShape.get(21).addNext_nodes(3, new ArrayList<>(List.of(24)));//걸
        boardShape.get(21).addNext_nodes(4, new ArrayList<>(List.of(25)));//윷
        boardShape.get(21).addNext_nodes(5, new ArrayList<>(List.of(37)));//모

        //28번
        boardShape.get(28).addNext_nodes(0, new ArrayList<>(List.of(6))); //빽도
        boardShape.get(28).addNext_nodes(1, new ArrayList<>(List.of(29))); //도
        boardShape.get(28).addNext_nodes(2, new ArrayList<>(List.of(36))); //개
        boardShape.get(28).addNext_nodes(3, new ArrayList<>(List.of(34)));//걸
        boardShape.get(28).addNext_nodes(4, new ArrayList<>(List.of(35)));//윷
        boardShape.get(28).addNext_nodes(5, new ArrayList<>(List.of(21)));//모
        //29번
        boardShape.get(29).addNext_nodes(0, new ArrayList<>(List.of(28))); //빽도
        boardShape.get(29).addNext_nodes(1, new ArrayList<>(List.of(36))); //도
        boardShape.get(29).addNext_nodes(2, new ArrayList<>(List.of(34))); //개
        boardShape.get(29).addNext_nodes(3, new ArrayList<>(List.of(35)));//걸
        boardShape.get(29).addNext_nodes(4, new ArrayList<>(List.of(21)));//윷
        boardShape.get(29).addNext_nodes(5, new ArrayList<>(List.of(22)));//모

        //30번
        boardShape.get(30).addNext_nodes(0, new ArrayList<>(List.of(11))); //빽도
        boardShape.get(30).addNext_nodes(1, new ArrayList<>(List.of(31))); //도
        boardShape.get(30).addNext_nodes(2, new ArrayList<>(List.of(36))); //개
        boardShape.get(30).addNext_nodes(3, new ArrayList<>(List.of(34)));//걸
        boardShape.get(30).addNext_nodes(4, new ArrayList<>(List.of(35)));//윷
        boardShape.get(30).addNext_nodes(5, new ArrayList<>(List.of(21)));//모
        //31번
        boardShape.get(31).addNext_nodes(0, new ArrayList<>(List.of(30))); //빽도
        boardShape.get(31).addNext_nodes(1, new ArrayList<>(List.of(36))); //도
        boardShape.get(31).addNext_nodes(2, new ArrayList<>(List.of(34))); //개
        boardShape.get(31).addNext_nodes(3, new ArrayList<>(List.of(35)));//걸
        boardShape.get(31).addNext_nodes(4, new ArrayList<>(List.of(21)));//윷
        boardShape.get(31).addNext_nodes(5, new ArrayList<>(List.of(22)));//모

        //32번
        boardShape.get(32).addNext_nodes(0, new ArrayList<>(List.of(16))); //빽도
        boardShape.get(32).addNext_nodes(1, new ArrayList<>(List.of(33))); //도
        boardShape.get(32).addNext_nodes(2, new ArrayList<>(List.of(36))); //개
        boardShape.get(32).addNext_nodes(3, new ArrayList<>(List.of(34)));//걸
        boardShape.get(32).addNext_nodes(4, new ArrayList<>(List.of(35)));//윷
        boardShape.get(32).addNext_nodes(5, new ArrayList<>(List.of(21)));//모
        //33번
        boardShape.get(33).addNext_nodes(0, new ArrayList<>(List.of(32))); //빽도
        boardShape.get(33).addNext_nodes(1, new ArrayList<>(List.of(36))); //도
        boardShape.get(33).addNext_nodes(2, new ArrayList<>(List.of(34))); //개
        boardShape.get(33).addNext_nodes(3, new ArrayList<>(List.of(35)));//걸
        boardShape.get(33).addNext_nodes(4, new ArrayList<>(List.of(21)));//윷
        boardShape.get(33).addNext_nodes(5, new ArrayList<>(List.of(22)));//모

        //34번
        boardShape.get(34).addNext_nodes(0, new ArrayList<>(List.of(36))); //빽도
        boardShape.get(34).addNext_nodes(1, new ArrayList<>(List.of(35))); //도
        boardShape.get(34).addNext_nodes(2, new ArrayList<>(List.of(21))); //개
        boardShape.get(34).addNext_nodes(3, new ArrayList<>(List.of(22)));//걸
        boardShape.get(34).addNext_nodes(4, new ArrayList<>(List.of(23)));//윷
        boardShape.get(34).addNext_nodes(5, new ArrayList<>(List.of(24)));//모
        //35번
        boardShape.get(35).addNext_nodes(0, new ArrayList<>(List.of(34))); //빽도
        boardShape.get(35).addNext_nodes(1, new ArrayList<>(List.of(21))); //도
        boardShape.get(35).addNext_nodes(2, new ArrayList<>(List.of(22))); //개
        boardShape.get(35).addNext_nodes(3, new ArrayList<>(List.of(23)));//걸
        boardShape.get(35).addNext_nodes(4, new ArrayList<>(List.of(24)));//윷
        boardShape.get(35).addNext_nodes(5, new ArrayList<>(List.of(25)));//모

        //26번
        boardShape.get(26).addNext_nodes(0, new ArrayList<>(List.of(36))); //빽도
        boardShape.get(26).addNext_nodes(1, new ArrayList<>(List.of(27))); //도
        boardShape.get(26).addNext_nodes(2, new ArrayList<>(List.of(37))); //개
        boardShape.get(26).addNext_nodes(3, new ArrayList<>(List.of(37)));//걸
        boardShape.get(26).addNext_nodes(4, new ArrayList<>(List.of(37)));//윷
        boardShape.get(26).addNext_nodes(5, new ArrayList<>(List.of(37)));//모
        //27번
        boardShape.get(27).addNext_nodes(0, new ArrayList<>(List.of(26))); //빽도
        boardShape.get(27).addNext_nodes(1, new ArrayList<>(List.of(37))); //도
        boardShape.get(27).addNext_nodes(2, new ArrayList<>(List.of(37))); //개
        boardShape.get(27).addNext_nodes(3, new ArrayList<>(List.of(37)));//걸
        boardShape.get(27).addNext_nodes(4, new ArrayList<>(List.of(37)));//윷
        boardShape.get(27).addNext_nodes(5, new ArrayList<>(List.of(37)));//모

        //36번
        boardShape.get(36).addNext_nodes(1, new ArrayList<>(List.of(26))); //도
        boardShape.get(36).addNext_nodes(2, new ArrayList<>(List.of(27))); //개
        boardShape.get(36).addNext_nodes(3, new ArrayList<>(List.of(37)));//걸
        boardShape.get(36).addNext_nodes(4, new ArrayList<>(List.of(37)));//윷
        boardShape.get(36).addNext_nodes(5, new ArrayList<>(List.of(37)));//모



    }

    @Override
    public void setSingleConnection(int i) {
        boardShape.get(i).addNext_nodes(0, new ArrayList<>(List.of(i-1))); //-1은 빽도
        boardShape.get(i).addNext_nodes(1, new ArrayList<>(List.of(i+1))); //도
        boardShape.get(i).addNext_nodes(2, new ArrayList<>(List.of(i+2)));//개
        boardShape.get(i).addNext_nodes(3, new ArrayList<>(List.of(i+3)));//걸
        boardShape.get(i).addNext_nodes(4, new ArrayList<>(List.of(i+4)));//윷
        boardShape.get(i).addNext_nodes(5, new ArrayList<>(List.of(i+5)));//모
    }

    @Override
    public void setConnection_one_dest(int from_key, int yut_result, int to_key)
    {
        boardShape.get(from_key).addNext_nodes(yut_result, new ArrayList<>(List.of(to_key)));
    }

    @Override
    public int getKey(int key) {
        return boardShape.get(key).getKey();
    }



    @Override
    public void setNext_nodes(int key, int next_node) {

    }



    @Override
    public ArrayList<Integer> getNext_nodes_board(int key, int yut_result) {


        Node node = boardShape.get(key);
        if (node == null) return new ArrayList<>(); // 노드가 존재하지 않을 경우 예외 방지
        return node.getNext_nodes(yut_result);
    }

    @Override
    public void addNext_nodes(int key, int next_node) {

    }

    @Override
    public void removeNext_nodes(int key, int next_node) {

    }


}
