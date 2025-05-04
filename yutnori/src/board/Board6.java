package board;

import play.YutResult;

import java.util.*;

public class Board6 extends BoardAbstract{




    public Board6()
    {

        InitNodes();
        SetConnections();


    }


    protected void InitNodes()
    {
        createNode(1, 500, 500, true, false, false, false);//임시 좌표
        createNode(2, 450, 500, false, false, false, false);
        createNode(3, 400, 500, false, false, false, false);
        createNode(4, 350, 500, false, false, false, false);
        createNode(5, 300, 500, false, false, false, false);
        createNode(6, 250, 500, false, false, false, false);
        createNode(7, 200, 500, false, false, false, false);
        createNode(8, 150, 500, false, false, false, false);
        createNode(9, 100, 500, false, false, false, false);
        createNode(10, 50, 500, false, false, false, false);
        createNode(11, 0, 500, false, false, false, false);
        createNode(12, 50, 450, false, false, false, false);
        createNode(13, 100, 450, false, false, false, false);
        createNode(14, 150, 450, false, false, false, false);
        createNode(15, 200, 450, false, false, false, false);
        createNode(16, 250, 450, false, false, false, false);
        createNode(17, 300, 450, false, false, false, false);
        createNode(18, 350, 450, false, false, false, false);
        createNode(19, 400, 450, false, false, false, false);
        createNode(20, 450, 450, false, false, false, false);
        createNode(21, 500, 450, false, false, false, false);
        createNode(22, 550, 450, false, false, false, false);
        createNode(23, 600, 450, false, false, false, false);
        createNode(24, 650, 450, false, false, false, false);
        createNode(25, 650, 400, false, false, false, false);
        createNode(26, 600, 400, false, false, false, false);
        createNode(27, 550, 400, false, false, false, false);
        createNode(28, 500, 400, false, false, false, false);
        createNode(29, 450, 400, false, false, false, false);
        createNode(30, 400, 400, false, false, false, false);
        createNode(31, 350, 400, false, false, false, false);
        createNode(32, 300, 400, false, false, false, false);
        createNode(33, 250, 400, false, false, false, false);
        createNode(34, 200, 400, false, false, false, false);
        createNode(35, 150, 400, false, false, false, false);
        createNode(36, 100, 400, false, false, false, false);
        createNode(37, 50, 400, false, false, false, false);
        createNode(38, 0, 400, false, false, false, false);
        createNode(39, 0, 350, false, false, false, false);
        createNode(40, 50, 350, false, false, false, false);
        createNode(41, 100, 350, false, false, false, false);
        createNode(42, 150, 350, false, false, false, false);
        createNode(43, 200, 350, false, false, false, false);
        createNode(44, 250, 350, false, false, false, false);

    }

    private void createNode(int key,int x_pos, int y_pos, boolean isStartPoint,
                            boolean isEndPoint, boolean isCenter, boolean isCorner)
    {
        Node node = new Node(key, x_pos, y_pos, isStartPoint,
                isEndPoint, isCenter, isCorner);
        boardShape.put(key, node);
    }


    protected void SetConnections() {

        //1번 노드
        boardShape.get(1).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(30,32))); //0은 빽도
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
        for(int i = 22; i<=25; i++) //22~25번 노드
        {
            setSingleConnection(i);
        }

        for(int i=27; i<=30; i++)
        {
            for(int j =-1; j<6;j ++)
            {

                if(i+j>30)
                {
                    setConnection_one_dest(i,j,44); //끝나면
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
        boardShape.get(6).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(5)));// 빽도
        boardShape.get(6).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(33,7)));
        boardShape.get(6).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(34,8)));
        boardShape.get(6).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(43,9)));
        boardShape.get(6).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(41,10)));
        boardShape.get(6).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(42,11)));

// 11번 노드
        boardShape.get(11).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(10)));//빽도
        boardShape.get(11).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(35,12)));//도
        boardShape.get(11).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(36,13)));//개
        boardShape.get(11).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(43,14)));//걸
        boardShape.get(11).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(41,15)));//윷
        boardShape.get(11).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(42,16)));//모

//16번
        boardShape.get(16).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(15))); //빽도
        boardShape.get(16).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(37,17))); //도
        boardShape.get(16).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(38,18))); //개
        boardShape.get(16).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(43,19)));//걸
        boardShape.get(16).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(41,20)));//윷
        boardShape.get(16).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(43,21)));//모

//21번
        boardShape.get(21).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //빽도
        boardShape.get(21).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(22,39))); //도
        boardShape.get(21).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(23,40))); //개
        boardShape.get(21).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(24,43)));//걸
        boardShape.get(21).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(25,41)));//윷
        boardShape.get(21).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(26,42)));//모

//26번
        boardShape.get(26).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(25,42))); //빽도-25 or 42??
        boardShape.get(26).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(27))); //도
        boardShape.get(26).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(28))); //개
        boardShape.get(26).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(29)));//걸
        boardShape.get(26).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(30)));//윷
        boardShape.get(26).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(44)));//모

// 33 35 37 39 개걸윷모
        int[] nodeNumbers1 = {33, 35, 37, 39};
        for (int node : nodeNumbers1) {
            boardShape.get(node).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(43)));  // 개
            boardShape.get(node).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(41)));  // 걸
            boardShape.get(node).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(42)));  // 윷
            boardShape.get(node).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(26)));  // 모
        }

//34 36 38 40 도개걸윷모
        int[] nodeNumbers2 = {33, 35, 37, 39};
        for (int node : nodeNumbers2) {
            boardShape.get(node).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(43)));  // 도
            boardShape.get(node).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(41)));  // 개
            boardShape.get(node).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(42)));  // 걸
            boardShape.get(node).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(26)));  // 윷
            boardShape.get(node).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(27)));  // 모
        }


// 33번 노드
        boardShape.get(33).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(6)));  // 빽도
        boardShape.get(33).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(34))); // 도

// 34번 노드
        boardShape.get(34).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(33)));  // 빽도

// 35번 노드
        boardShape.get(35).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(11)));  // 빽도
        boardShape.get(35).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(36)));  // 도

// 36번 노드
        boardShape.get(36).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(35)));  // 빽도

// 37번 노드
        boardShape.get(37).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(16)));  // 빽도
        boardShape.get(37).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(38)));  // 도

// 38번 노드
        boardShape.get(38).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(37)));  // 빽도

// 39번 노드
        boardShape.get(39).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(21)));  // 빽도
        boardShape.get(39).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(40)));  // 도

// 40번 노드
        boardShape.get(40).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(39)));  // 빽도

// 41번 노드
        boardShape.get(41).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(43)));  // 빽도
        boardShape.get(41).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(42)));  // 도
        boardShape.get(41).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(26)));  // 개
        boardShape.get(41).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(27)));  // 걸
        boardShape.get(41).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(28)));  // 윷
        boardShape.get(41).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(29)));  // 모

// 42번 노드
        boardShape.get(42).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(41)));  // 빽도
        boardShape.get(42).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(26)));  // 도
        boardShape.get(42).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(27)));  // 개
        boardShape.get(42).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(28)));  // 걸
        boardShape.get(42).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(29)));  // 윷
        boardShape.get(42).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30)));  // 모

// 31번 노드
        boardShape.get(31).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(43)));  // 빽도
        boardShape.get(31).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(32)));  // 도
        boardShape.get(31).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(44)));  // 개
        boardShape.get(31).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(44)));  // 걸
        boardShape.get(31).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(44)));  // 윷
        boardShape.get(31).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(44)));  // 모

// 32번 노드
        boardShape.get(32).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(31)));  // 빽도
        boardShape.get(32).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(32)));  // 도
        boardShape.get(32).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(44)));  // 개
        boardShape.get(32).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(44)));  // 걸
        boardShape.get(32).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(44)));  // 윷
        boardShape.get(32).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(44)));  // 모

// 43번 노드
        boardShape.get(43).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(31))); // 도
        boardShape.get(43).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(32))); // 개
        boardShape.get(43).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(44))); // 걸
        boardShape.get(43).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(44))); // 윷
        boardShape.get(43).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(44))); // 모


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

    @Override
    public void setConnection_one_dest(int from_key, int yut_result, int to_key)
    {
        if (yut_result==-1) {
            yut_result = 0;
        }
        YutResult yutResultEnum = YutResult.values()[yut_result];

        boardShape.get(from_key).addNext_nodes(yutResultEnum, new ArrayList<>(List.of(to_key)));
    }

    @Override
    public int getKey(int key)
    {
        return boardShape.get(key).getKey();
    }


    @Override
    public ArrayList<Integer> getNext_nodes_board(int key, YutResult yut_result) {


        Node node = boardShape.get(key);
        if (node == null) return new ArrayList<>(); // 노드가 존재하지 않을 경우 예외 방지
        return node.getNext_nodes(yut_result);
    }


}
