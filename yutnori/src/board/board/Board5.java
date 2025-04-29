package board.board;

import play.YutResult;

import java.util.*;

public class Board5 extends BoardAbstract{




    public Board5()
    {

        InitNodes();
        SetConnections();


    }


    protected void InitNodes()
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


    protected void SetConnections() {

        //1번 노드
        boardShape.get(1).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(25,27))); //0은 빽도 - 25 or 27??
        boardShape.get(1).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(2))); //도
        boardShape.get(1).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(3))); //개
        boardShape.get(1).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(4)));//걸
        boardShape.get(1).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(5)));//윷
        boardShape.get(1).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(6)));//모


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
        // 6번 노드
        boardShape.get(6).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(5)));
        boardShape.get(6).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(28, 7)));
        boardShape.get(6).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(29, 8)));
        boardShape.get(6).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(36, 9)));
        boardShape.get(6).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(34, 10)));
        boardShape.get(6).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(35, 11)));

// 11번 노드
        boardShape.get(11).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(10)));
        boardShape.get(11).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(30, 12)));
        boardShape.get(11).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(31, 13)));
        boardShape.get(11).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(36, 14)));
        boardShape.get(11).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(34, 15)));
        boardShape.get(11).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(35, 16)));

// 16번 노드
        boardShape.get(16).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(15)));
        boardShape.get(16).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(32, 17)));
        boardShape.get(16).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(33, 18)));
        boardShape.get(16).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(36, 19)));
        boardShape.get(16).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(34, 20)));
        boardShape.get(16).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(35, 21)));

// 21번 노드
        boardShape.get(21).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20, 35)));
        boardShape.get(21).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(22)));
        boardShape.get(21).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(23)));
        boardShape.get(21).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(24)));
        boardShape.get(21).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(25)));
        boardShape.get(21).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(37)));

// 28번 노드
        boardShape.get(28).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(6)));
        boardShape.get(28).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(29)));
        boardShape.get(28).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(36)));
        boardShape.get(28).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(34)));
        boardShape.get(28).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(35)));
        boardShape.get(28).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(21)));

// 29번 노드
        boardShape.get(29).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(28)));
        boardShape.get(29).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(36)));
        boardShape.get(29).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(34)));
        boardShape.get(29).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(35)));
        boardShape.get(29).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(21)));
        boardShape.get(29).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(22)));

// 30번 노드
        boardShape.get(30).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(11)));
        boardShape.get(30).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(31)));
        boardShape.get(30).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(36)));
        boardShape.get(30).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(34)));
        boardShape.get(30).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(35)));
        boardShape.get(30).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(21)));

// 31번 노드
        boardShape.get(31).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(30)));
        boardShape.get(31).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(36)));
        boardShape.get(31).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(34)));
        boardShape.get(31).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(35)));
        boardShape.get(31).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(21)));
        boardShape.get(31).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(22)));

// 32번 노드
        boardShape.get(32).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(16)));
        boardShape.get(32).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(33)));
        boardShape.get(32).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(36)));
        boardShape.get(32).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(34)));
        boardShape.get(32).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(35)));
        boardShape.get(32).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(21)));

// 33번 노드
        boardShape.get(33).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(32)));
        boardShape.get(33).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(36)));
        boardShape.get(33).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(34)));
        boardShape.get(33).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(35)));
        boardShape.get(33).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(21)));
        boardShape.get(33).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(22)));

// 34번 노드
        boardShape.get(34).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(36)));
        boardShape.get(34).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(35)));
        boardShape.get(34).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(21)));
        boardShape.get(34).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(22)));
        boardShape.get(34).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(23)));
        boardShape.get(34).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(24)));

// 35번 노드
        boardShape.get(35).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(34)));
        boardShape.get(35).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(21)));
        boardShape.get(35).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(22)));
        boardShape.get(35).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(23)));
        boardShape.get(35).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(24)));
        boardShape.get(35).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(25)));

// 26번 노드
        boardShape.get(26).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(36)));
        boardShape.get(26).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(27)));
        boardShape.get(26).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(37)));
        boardShape.get(26).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(37)));
        boardShape.get(26).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(37)));
        boardShape.get(26).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(37)));

// 27번 노드
        boardShape.get(27).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(26)));
        boardShape.get(27).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(37)));
        boardShape.get(27).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(37)));
        boardShape.get(27).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(37)));
        boardShape.get(27).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(37)));
        boardShape.get(27).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(37)));

// 36번 노드
        boardShape.get(36).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(26)));
        boardShape.get(36).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(27)));
        boardShape.get(36).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(37)));
        boardShape.get(36).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(37)));
        boardShape.get(36).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(37)));




    }

    @Override
    public void setSingleConnection(int i) {
        boardShape.get(i).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(i-1))); //-1은 빽도
        boardShape.get(i).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(i+1))); //도
        boardShape.get(i).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(i+2)));//개
        boardShape.get(i).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(i+3)));//걸
        boardShape.get(i).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(i+4)));//윷
        boardShape.get(i).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(i+5)));//모
    }


    public void setConnection_one_dest(int from_key, int yut_result, int to_key)
    {
        if (yut_result==-1) {
            yut_result = 0;
        }
        YutResult yutResultEnum = YutResult.values()[yut_result];
        boardShape.get(from_key).addNext_nodes(yutResultEnum, new ArrayList<>(List.of(to_key)));
    }

    @Override
    public int getKey(int key) {
        return boardShape.get(key).getKey();
    }


    @Override
    public ArrayList<Integer> getNext_nodes_board(int key, YutResult yut_result) {


        Node node = boardShape.get(key);
        if (node == null) return new ArrayList<>(); // 노드가 존재하지 않을 경우 예외 방지
        return node.getNext_nodes(yut_result);
    }


}
