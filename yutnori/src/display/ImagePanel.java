package display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
  //이미지를 화면의 비율에 맞게 설정하는 class
  BufferedImage image; //이 패널에 표시할 이미지 데이터를 저장할 변수

  public ImagePanel() {
    setOpaque(false);
  }
  //생성자: 배경을 투명하게 만듦
  //setOpaque(false)는 배경을 칠하지 않도록 하여, 배경이 겹쳐질 경우 아래가 보이게 해줌.

  public void setImage(BufferedImage image) {
    this.image = image;
  }
  //외부에서  이미지를 설정할 수 있게 해주는 setter 매서드

  protected void paintComponent(Graphics g) {
    //JPanel의 paintComponent 메서드를 오버라이드
    //이 메서드는 화면이 다시 그려질 때 자동으로 호출되어서 패널에 그림을 그릴 수 있게 해줌.

    super.paintComponent(g);
    //부모 클래스의 기본 그리기 작업을 먼저 수행(필수!!)

    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    //Graphics2D 객체로 형변환하여 고급 그래픽 설정 사용.
    //INTERPOLATION_BILINEAR는 이미지를 부드럽게 확대/축소하도록 설정.

    if (image != null) { //이미지가 설정되어 있는 경우에만 그림을 그림.
      float wr = (float)getWidth() / (float)image.getWidth();
      float hr = (float)getHeight() / (float)image.getHeight();
      //패널의 크기(getWidth(), getHeight())와 이미지의 원래 크기 간의 비율 계산
      //wr: 너비 비율, hr: 높이 비율

      float r = Math.min(wr, hr);
      //너비 비율과 높이 비율 중 더 작은 값을 사용
      //이미지가 늘어나거나 잘리는 일 없이 비율을 유지한 채 화면 안에 들어가기 때문에.

      int w = (int)(image.getWidth()*r);
      int h = (int)(image.getHeight()*r);
      //비율을 적용해 조절된 이미지의 너비(w)와 높이(h)를 계산

      g.drawImage(image, (getWidth()-w)/2,(getHeight()-h)/2,w,h, this);
      //이미지 그리기
      //(getWidth()-w)/2,(getHeight()-h)/2: 가운데 정렬을 위한 좌표 계산
      //w, h: 계산된 크기로 이미지 스케일링
      //this: 이미지 관찰자
    }
  }
}