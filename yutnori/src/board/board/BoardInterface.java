package board.board;

import play.YutResult;

import java.util.ArrayList;

public interface BoardInterface {



    void setSingleConnection(int i);//(i번째 노드가
    // 한 개의 노드만 연결 가능할 때 도개걸윷모빽도 한번에 연결

    void setConnection_one_dest(int from_key, int yut_result, int to_key);


    int getKey(int key);

    ArrayList<Integer> getNext_nodes_board(int key, YutResult yut_result);
    //board class에서 key노드에서 윷 던지기 결과에 따른 결과 반환



}
