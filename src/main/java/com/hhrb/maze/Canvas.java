package com.hhrb.maze;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * User: Z J Wu Date: 2019-02-18 Time: 15:16 Package: com.hhrb.maze
 */
public class Canvas {

  private final Random random = new Random();

  private static final String HEAD = "<svg xmlns='http://www.w3.org/2000/svg'>";
  private static final String TAIL = "<svg xmlns='http://www.w3.org/2000/svg'>";

  private int width;
  private int height;

  private int minRoomWidth;
  private int minRoomHeight;
  private int maxRoomWidth;
  private int maxRoomHeight;

  private List<Rect> rooms;

  public Canvas(int width, int height) {
    this.width = width;
    this.height = height;
    this.minRoomWidth = 30;
    this.minRoomHeight = 30;
    this.maxRoomWidth = 100;
    this.maxRoomHeight = 100;
    this.rooms = Lists.newArrayList();
  }

  public Rect randomRoom() {
    int width = random.nextInt(maxRoomWidth - minRoomWidth) + minRoomWidth;
    int height = random.nextInt(maxRoomHeight - minRoomHeight) + minRoomHeight;
    int x = random.nextInt(this.width - width), y = random.nextInt(this.height - height);
    return new Rect(x, y, width, height);
  }

  public Rect randomNonOverlapRoom() {
    for (int i = 0; i < 100000; i++) {
      Rect room = randomRoom();
      boolean overlap = false;
      nextRound:
      for (Rect r : rooms) {
        if (Rect.hasOverlap(r, room)) {
          overlap = true;
          break nextRound;
        }
      }
      if (!overlap) {
        return room;
      }
    }
    return null;
  }

  public int getWidth() {
    return width;
  }

  public List<Rect> getRooms() {
    return rooms;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    return sb.toString();
  }

  public static void main(String[] args) throws IOException {

    Canvas canvas = new Canvas(1280, 720);
    for (int i = 0; i < 200; i++) {
      Rect room = canvas.randomNonOverlapRoom();
      if (room != null) {
        canvas.getRooms().add(room);
      }
    }
    System.out.println(canvas.getRooms().size());
    Utils.writeCanvas(canvas, "/Users/WuZijing/tmp_data/svg/canvas.svg");
  }
}
