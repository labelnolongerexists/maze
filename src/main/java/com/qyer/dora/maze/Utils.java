package com.qyer.dora.maze;

import static com.qyer.dora.maze.Constants.C_ACCESSIBLE;
import static com.qyer.dora.maze.Constants.C_BACKGROUND;
import static com.qyer.dora.maze.Constants.C_BLOCKED;
import static com.qyer.dora.maze.Constants.DEFAULT_BORDER;
import static com.qyer.dora.maze.Constants.R;

import com.qyer.dora.maze.generator.Maze;
import org.apache.commons.collections4.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:58 Package: com.qyer.dora.maze
 */
public class Utils {

  public static final int closedRandom(int fromClosed, int toClosed) {
    if (fromClosed > toClosed) {
      return fromClosed;
    }
    return R.nextInt(toClosed + 1 - fromClosed) + fromClosed;
  }

  public static final void writeImage(BufferedImage image, String format,
                                      OutputStream outputStream) throws IOException {
    ImageIO.write(image, format, outputStream);
  }

  public static final <T> T selectOne(List<T> list) {
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    int idx = R.nextInt(list.size());
    return list.get(idx);
  }

  public static BufferedImage makeImage(Maze maze, int brushSize) {
    int r = maze.getRows(), c = maze.getColumns();
    int width = maze.getColumns() * brushSize, height = maze.getRows() * brushSize;

    final int b = brushSize, bd = DEFAULT_BORDER;
    // 创建BufferedImage对象
    BufferedImage image = new BufferedImage(width + bd * 2, height + bd * 2,
                                            BufferedImage.TYPE_INT_RGB);
    // 获取Graphics2D
    Graphics2D g2d = null;
    try {
      g2d = image.createGraphics();
      // 画图
      g2d.setBackground(C_BACKGROUND);
      g2d.clearRect(0, 0, width + bd * 2, height + bd * 2);

      for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
          if (maze.isBlocked(maze.getContent(i, j))) {
            g2d.setPaint(C_BLOCKED);
            g2d.fillRect(bd + j * b, bd + i * b, b, b);
          } else {
            g2d.setPaint(C_ACCESSIBLE);
            g2d.fillRect(bd + j * b, bd + i * b, b, b);
          }
        }
        g2d.setPaint(C_ACCESSIBLE);
        g2d.fillRect(bd + c * b, bd + i * b, b, b);
      }
      g2d.setPaint(C_ACCESSIBLE);
      g2d.fillRect(height + bd, 0, width, bd);
    } finally {
      //释放对象
      if (g2d != null) {
        g2d.dispose();
      }
    }
    return image;
  }

  public static void writeMaze(Maze maze, String path, int brushSize) throws Exception {
    ImageIO.write(makeImage(maze, brushSize), "png", new File(path + ".png"));
  }

  public static final void printFileContentInClassPath(String filePath) {
    try (BufferedReader br = new BufferedReader(
      new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(filePath)))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean generateBooleanProbability(int percentOfTrue) {
    if (percentOfTrue <= 0) {
      return false;
    }
    if (percentOfTrue >= 100) {
      return true;
    }
    return R.nextInt(100) < percentOfTrue;
  }

  public static void main(String[] args) {
    System.out.println(1);
  }
}
