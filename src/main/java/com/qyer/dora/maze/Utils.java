package com.qyer.dora.maze;

import static com.qyer.dora.maze.Constants.R;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
}
