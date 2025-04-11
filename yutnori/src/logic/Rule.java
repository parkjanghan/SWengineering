package logic;

public class Rule {
  //윷놀이 보드에서 말이 이동할 수 있는 경로를 결정하는 룰(규칙) 테이블을 저장하고,
  //현재 위치(circleId)와 윷 결과(result)를 기반으로 다음에 이동할 수 있는 위치 ID를 반환하는 핵심 클래스야.
  //윷을 던지고 몇 칸 앞으로 가야 할 때, 지름길 진입, 다양한 경로 분기를 처리

  int[][][] nextMoveTable;
  //3차원 배열로 말의 이동 경로를 저장
  //첫 번째: 현재 칸 ID (0 ~ 28)
  //두 번째: 윷 결과 (0: 빽도, 1: 도, ..., 5: 모)
  //세 번째: 분기 여부 (0: 기본 경로, 1: 지름길 또는 특수 경로)

  //예시: nextMoveTable[5][3][1] = 22;
  // 현재 circleId=5에 있을 때, 걸(3)이 나오고 지름길 선택 시 → 22번 칸으로 이동하라는 의미

  public Rule(){
    nextMoveTable = new int[29][6][2]; //보드 칸 수 29개, 결과 수 6개, 2개의 분기 경로

    for(int i = 0; i < 29; i++){
      for(int j = 0; j < 6; j++){
        nextMoveTable[i][j][1] = -1; // 분기 경로를 초기화 (-1은 경로 없음)
      }
    }

    for(int i = 0; i < 15; i++){
      for(int j = 0; j < 6; j++){
        if(i == 0 && j == 0){
          nextMoveTable[0][0][0] = 1; // 빽도는 1칸 뒤로
          continue;
        }
        if(j == 0){
          nextMoveTable[i][j][0] = i - 1; // 빽도: 한 칸 뒤로
        }else {
          nextMoveTable[i][j][0] = i + j; //도~모: 앞으로 j칸 이동
        }
      }
    }
    //가장 단순한 직선 경로를 담당하는 부분
    //일반 경로는 [0] 인덱스에 저장됨

    nextMoveTable[5][1][1] = 25;
    nextMoveTable[5][2][1] = 26;
    nextMoveTable[5][3][1] = 22;
    nextMoveTable[5][4][1] = 27;
    nextMoveTable[5][5][1] = 28;

    nextMoveTable[10][1][1] = 24;
    nextMoveTable[10][2][1] = 23;
    nextMoveTable[10][3][1] = 22;
    nextMoveTable[10][4][1] = 21;
    nextMoveTable[10][5][1] = 20;

    nextMoveTable[15][0][0] = 14;
    nextMoveTable[15][1][0] = 16;
    nextMoveTable[15][2][0] = 17;
    nextMoveTable[15][3][0] = 18;
    nextMoveTable[15][4][0] = 19;
    nextMoveTable[15][5][0] = 0;

    nextMoveTable[16][0][0] = 15;
    nextMoveTable[16][1][0] = 17;
    nextMoveTable[16][2][0] = 18;
    nextMoveTable[16][3][0] = 19;
    nextMoveTable[16][4][0] = 0;
    nextMoveTable[16][5][0] = 0;

    nextMoveTable[17][0][0] = 16;
    nextMoveTable[17][1][0] = 18;
    nextMoveTable[17][2][0] = 19;
    nextMoveTable[17][3][0] = 0;
    nextMoveTable[17][4][0] = 0;
    nextMoveTable[17][5][0] = 0;

    nextMoveTable[18][0][0] = 17;
    nextMoveTable[18][1][0] = 19;
    nextMoveTable[18][2][0] = 0;
    nextMoveTable[18][3][0] = 0;
    nextMoveTable[18][4][0] = 0;
    nextMoveTable[18][5][0] = 0;

    nextMoveTable[19][0][0] = 18;
    nextMoveTable[19][1][0] = 0;
    nextMoveTable[19][2][0] = 0;
    nextMoveTable[19][3][0] = 0;
    nextMoveTable[19][4][0] = 0;
    nextMoveTable[19][5][0] = 0;

    nextMoveTable[20][0][0] = 21;
    nextMoveTable[20][1][0] = 0;
    nextMoveTable[20][2][0] = 0;
    nextMoveTable[20][3][0] = 0;
    nextMoveTable[20][4][0] = 0;
    nextMoveTable[20][5][0] = 0;


    nextMoveTable[21][0][0] = 22;
    nextMoveTable[21][1][0] = 20;
    nextMoveTable[21][2][0] = 0;
    nextMoveTable[21][3][0] = 0;
    nextMoveTable[21][4][0] = 0;
    nextMoveTable[21][5][0] = 0;

    nextMoveTable[22][0][0] = 23;
    nextMoveTable[22][1][0] = 21;
    nextMoveTable[22][2][0] = 20;
    nextMoveTable[22][3][0] = 0;
    nextMoveTable[22][4][0] = 0;
    nextMoveTable[22][5][0] = 0;

    nextMoveTable[22][0][1] = 26;
    nextMoveTable[22][1][1] = 27;
    nextMoveTable[22][2][1] = 28;
    nextMoveTable[22][3][1] = 15;
    nextMoveTable[22][4][1] = 16;
    nextMoveTable[22][5][1] = 17;

    nextMoveTable[23][0][0] = 24;
    nextMoveTable[23][1][0] = 22;
    nextMoveTable[23][2][0] = 21;
    nextMoveTable[23][3][0] = 20;
    nextMoveTable[23][4][0] = 0;
    nextMoveTable[23][5][0] = 0;

    nextMoveTable[24][0][0] = 10;
    nextMoveTable[24][1][0] = 23;
    nextMoveTable[24][2][0] = 22;
    nextMoveTable[24][3][0] = 21;
    nextMoveTable[24][4][0] = 20;
    nextMoveTable[24][5][0] = 0;

    nextMoveTable[25][0][0] = 5;
    nextMoveTable[25][1][0] = 26;
    nextMoveTable[25][2][0] = 22;
    nextMoveTable[25][3][0] = 27;
    nextMoveTable[25][4][0] = 28;
    nextMoveTable[25][5][0] = 15;

    nextMoveTable[26][0][0] = 25;
    nextMoveTable[26][1][0] = 22;
    nextMoveTable[26][2][0] = 27;
    nextMoveTable[26][3][0] = 28;
    nextMoveTable[26][4][0] = 15;
    nextMoveTable[26][5][0] = 16;

    nextMoveTable[27][0][0] = 22;
    nextMoveTable[27][1][0] = 28;
    nextMoveTable[27][2][0] = 15;
    nextMoveTable[27][3][0] = 16;
    nextMoveTable[27][4][0] = 17;
    nextMoveTable[27][5][0] = 18;

    nextMoveTable[28][0][0] = 27;
    nextMoveTable[28][1][0] = 15;
    nextMoveTable[28][2][0] = 16;
    nextMoveTable[28][3][0] = 17;
    nextMoveTable[28][4][0] = 18;
    nextMoveTable[28][5][0] = 19;
  }

  public int[] getNextMoveCircleIds(int circleId, int result){
    try{
      return nextMoveTable[circleId][result];
    } catch (NullPointerException e){
    }

    return null;
  }
  //말이 현재 위치 circleId에 있고, 윷 결과 result (0~5)가 나왔을 때:
  //기본 경로: nextMoveTable[circleId][result][0]
  //분기 경로: nextMoveTable[circleId][result][1]
  //이 둘을 int 배열로 반환
  //예시: getNextMoveCircleIds(5, 3) → [8, 22]
  //5번 칸에서 걸(3) 나왔을 때:
  //  기본 경로는 8번 칸
  //  분기 선택하면 22번 칸 (지름길)
}
