package com.qyer.dora.maze;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:58 Package: com.qyer.dora.maze
 */
public class Utils {

  public static final void writeImage(BufferedImage image, String format,
                                      OutputStream outputStream) throws IOException {
    ImageIO.write(image, format, outputStream);
  }
}
