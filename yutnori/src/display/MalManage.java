package display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MalManage {
  //말의 이미지를 load하고 관리하는 class
  private String [] name = { "team1", "team2", "team3", "team4"};
  //말 이미지 파일들의 이름을 담은 배열
  BufferedImage[] pieceList; //각 말의 이미지를 저장할 배열
  BufferedImage image = null; //이미지를 임시 저장할 변수(루프 안에서 사용됨)

  public MalManage() {
    try {
      pieceList = new BufferedImage[4];
      pieceList = bufferedImage(name);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  //객체가 생성되면 자동으로 이미지 4개를 로듷하여 pieceList에 저장함
  //bufferedImage(name) 메서드를 호출하여 이미지들을 읽어옴.
  //예외가 발생하면 스택 트레이스를 출력

  private BufferedImage[] bufferedImage(String[] name) throws Exception {
    //이미지 파일 이름을 받아서 이미지 배열을 만들어 변환하는 private 메서드

    BufferedImage[] list = new BufferedImage[4];
    //반환한 이미지 배열 생성

    String path = MalManage.class.getResource("").getPath();
    //현재 클래스(MalManage.class)의 경로를 가져옴
    //이 경로를 기반으로 이미지 파일 경로를 만들기 위해 사용

    for (int i=0; i<name.length; i++) { //각 팀의 이미지 파일을 하나씩 반복해서 읽기 위한 반복문
      try {
        File file = new File(path + name[i] + ".png");
        //파일 경로를 조합해서 File 객체 생성
        image = ImageIO.read(file);
        //이미지 파일을 읽어서 BufferedImage로 로드
        list[i] = image;
        //읽은 이미지를 배열에 저장
      }
      catch(IOException e){
        System.out.println("Error: "+e);
      }
      //파일 읽기 도중 오류가 발생하면 오류 메세지를 출력
    }

    return list; //반복문이 끝나고 모든 이미지가 저장된 배열을 반환
  }

}
