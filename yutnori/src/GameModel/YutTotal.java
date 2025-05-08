package GameModel;

import play.YutResult;

import java.util.Random;

public class YutTotal {
    // 윷을 던져서 결과를 반환하는 메서드

    public YutResult rollYut()
    {
        Random random = new Random();
        double probability = random.nextDouble();
        if(probability <=0.0625)  //빽도
        {
            return YutResult.BACK_DO;
        }
        else if(probability <= 0.25)  //도
        {
            return YutResult.DO;
        }
        else if( probability <= (0.375 + 0.25)) //개
        {
            return YutResult.GAE;
        }
        else if(probability <= (0.375 + 0.25 + 0.25)) //걸
        {
            return YutResult.GEOL;
        }
        else if(probability <= (0.375 + 0.25 + 0.25 + 0.0625)) //윷
        {
            return YutResult.YUT;
        }
        else //모
        {
            return YutResult.MO;
        }
    }


}
