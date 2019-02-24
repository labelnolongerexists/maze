package com.qyer.dora.maze;

import java.util.Random;

/**
 * User: Z J Wu Date: 2019-02-22 Time: 10:31 Package: com.qyer.dora.maze
 */
public class Constants {

  public static final int DEFAULT_MAZE_WIDTH = 200;
  public static final int DEFAULT_MAZE_HEIGHT = 200;

  public static final Random R = new Random();

  public static final byte ACCESSABLE = 0;
  public static final byte BLOCK = 1;

  public static final char ROAD = ' ';
  public static final char G_WALL = "%".charAt(0);
  public static final char H_WALL = "=".charAt(0);
  public static final char V_WALL = "|".charAt(0);

  public static final int BRUSH_THIN = 1;
  public static final int BRUSH_MIDDLE = 10;
  public static final int BRUSH_THICK = 20;

}
