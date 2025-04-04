package display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MalManage {
    //말의 이미지를 load하고 관리하는 class
    /**
     * 말 이미지 로딩 및 크기 조절 (투명 배경 유지)
     * @param path  이미지 경로 (예: "/img/team_1.png")
     * @param width 원하는 너비
     * @param height 원하는 높이
     * @return 크기 조절된 ImageIcon
     */
    public static ImageIcon loadAndResize(String path, int width, int height) {
        try {
            BufferedImage original = ImageIO.read(MalManage.class.getResource(path));

            // 크기 지정 및 투명도 유지
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(original, 0, 0, width, height, null);
            g2.dispose();

            return new ImageIcon(resized);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("이미지 로딩 실패: " + path);
            return null;
        }
    }
}
