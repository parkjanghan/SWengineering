package board;

import play.YutResult;

import java.util.*;

public class Board4 extends BoardAbstract
{
    public Board4() {
        InitNodes();
        SetConnections();
    }

    protected void InitNodes() {
        //Node(0) ~ Node(-3)까지는 아직 출발하지 않은 말이 대기하는 노드입니다
        createNode(0, 600, 600, true, false, false, false);
        createNode(-1, 600, 600, true, false, false, false);
        createNode(-2, 600, 600, true, false, false, false);
        createNode(-3, 600, 600, true, false, false, false);

        //시작점
        createNode(1, 500, 500, false, false, false, false);  // Start node
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


        createNode(21, 300, 450, false, false, false, false);
        createNode(22, 350, 400, false, false, false, false);


        createNode(23, 350, 300, false, false, false, false);
        createNode(24, 300, 350, false, false, false, false);


        createNode(25, 300, 300, false, false, false, false);
        createNode(26, 350, 350, false, false, false, false);


        createNode(27, 400, 350, false, false, false, false);
        createNode(28, 450, 300, false, false, false, false);


        createNode(29, 375, 375, false, false, true, false);

        createNode(30, 500, 500, false, true, false, false);
    }


    private void createNode(int key,int x_pos, int y_pos, boolean isStartPoint,
                            boolean isEndPoint, boolean isCenter, boolean isCorner)
    {
        Node node = new Node(key, x_pos, y_pos, isStartPoint,
                isEndPoint, isCenter, isCorner);
        boardShape.put(key, node);
    }


    protected void SetConnections() {

        boardShape.get(0).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //0은 빽도
        boardShape.get(0).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(2))); //도
        boardShape.get(0).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(3))); //개
        boardShape.get(0).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(4)));//걸
        boardShape.get(0).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(5)));//윷
        boardShape.get(0).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(6)));//모
        //-1번 노드 = 1번 사용자의 시작 노드
        boardShape.get(-1).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //0은 빽도
        boardShape.get(-1).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(2))); //도
        boardShape.get(-1).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(3))); //개
        boardShape.get(-1).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(4)));//걸
        boardShape.get(-1).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(5)));//윷
        boardShape.get(-1).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(6)));//모
        //-2 번 노드 = 2번 사용자의 시작 노드
        boardShape.get(-2).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //0은 빽도
        boardShape.get(-2).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(2))); //도
        boardShape.get(-2).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(3))); //개
        boardShape.get(-2).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(4)));//걸
        boardShape.get(-2).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(5)));//윷
        boardShape.get(-2).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(6)));//모
        //-3번 노드 = 3번 사용자의 시작 노드
        boardShape.get(-3).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //0은 빽도
        boardShape.get(-3).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(2))); //도
        boardShape.get(-3).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(3))); //개
        boardShape.get(-3).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(4)));//걸
        boardShape.get(-3).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(5)));//윷
        boardShape.get(-3).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(6)));//모


        //1번 노드 = 끝 전 마지막 노드
        boardShape.get(1).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); //0은 빽도
        boardShape.get(1).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(30))); //도
        boardShape.get(1).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(30))); //개
        boardShape.get(1).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(30)));//걸
        boardShape.get(1).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(30)));//윷
        boardShape.get(1).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30)));//모


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
        // 16번 노드: 아래쪽 코너

        for(int i=16; i<=20; i++)
        {
            for(int j =-1; j<6;j ++)
            {

                if(i+j>20)
                {
                    if(i+j==21)
                    {
                        setConnection_one_dest(i,j,1);
                    }
                    else {
                        setConnection_one_dest(i,j,30); //끝나면
                    }

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
        boardShape.get(6).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(5)));  // 빽도
        boardShape.get(6).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(21, 7)));
        boardShape.get(6).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(22, 8)));
        boardShape.get(6).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(29, 9)));
        boardShape.get(6).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(23, 10)));
        boardShape.get(6).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(24, 11))); // 중앙까지

// 11번 노드: 좌측
        boardShape.get(11).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(10))); // 빽도
        boardShape.get(11).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(25, 12))); // 도
        boardShape.get(11).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(26, 13))); // 개
        boardShape.get(11).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(29, 14))); // 걸
        boardShape.get(11).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(27, 15))); // 윷
        boardShape.get(11).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(28, 16))); // 모

// 21번 노드
        boardShape.get(21).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(20))); // 빽도
        boardShape.get(21).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(22))); // 도
        boardShape.get(21).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(29))); // 개
        boardShape.get(21).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(23))); // 걸
        boardShape.get(21).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(24))); // 윷
        boardShape.get(21).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(16))); // 모

// 22번 노드
        boardShape.get(22).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(21))); // 빽도
        boardShape.get(22).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(29))); // 도
        boardShape.get(22).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(23))); // 개
        boardShape.get(22).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(24))); // 걸
        boardShape.get(22).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(16))); // 윷
        boardShape.get(22).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(17))); // 모


// 23번 노드
        boardShape.get(23).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(29))); // 빽도
        boardShape.get(23).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(24))); // 도
        boardShape.get(23).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(16))); // 개
        boardShape.get(23).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(17))); // 걸
        boardShape.get(23).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(18))); // 윷
        boardShape.get(23).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(19))); // 모

// 24번 노드
        boardShape.get(24).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(23))); // 빽도
        boardShape.get(24).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(16))); // 도
        boardShape.get(24).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(17))); // 개
        boardShape.get(24).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(18))); // 걸
        boardShape.get(24).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(19))); // 윷
        boardShape.get(24).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(20))); // 모

// 25번 노드
        boardShape.get(25).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(11))); // 빽도
        boardShape.get(25).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(26))); // 도
        boardShape.get(25).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(29))); // 개
        boardShape.get(25).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(27))); // 걸
        boardShape.get(25).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(28))); // 윷
        boardShape.get(25).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(1))); // 모

// 26번 노드
        boardShape.get(26).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(25))); // 빽도
        boardShape.get(26).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(29))); // 도
        boardShape.get(26).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(27))); // 개
        boardShape.get(26).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(28))); // 걸
        boardShape.get(26).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(1))); // 윷
        boardShape.get(26).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30))); // 모

        // 27번 노드
        boardShape.get(27).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(29))); // 빽도
        boardShape.get(27).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(28))); // 도
        boardShape.get(27).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(1))); // 개
        boardShape.get(27).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(30))); // 걸
        boardShape.get(27).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(30))); // 윷
        boardShape.get(27).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30))); // 모

// 28번 노드
        boardShape.get(28).addNext_nodes(YutResult.BACK_DO, new ArrayList<>(List.of(27))); // 빽도
        boardShape.get(28).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(1))); // 도
        boardShape.get(28).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(30))); // 개
        boardShape.get(28).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(30))); // 걸
        boardShape.get(28).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(30))); // 윷
        boardShape.get(28).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30))); // 모

// 29번 노드
        boardShape.get(29).addNext_nodes(YutResult.DO, new ArrayList<>(List.of(23, 27))); // 도
        boardShape.get(29).addNext_nodes(YutResult.GAE, new ArrayList<>(List.of(28, 24))); // 개
        boardShape.get(29).addNext_nodes(YutResult.GEOL, new ArrayList<>(List.of(16,1))); // 걸
        boardShape.get(29).addNext_nodes(YutResult.YUT, new ArrayList<>(List.of(30))); // 윷
        boardShape.get(29).addNext_nodes(YutResult.MO, new ArrayList<>(List.of(30))); // 모


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