package com.hhrb.maze;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * User: Z J Wu Date: 2019-02-18 Time: 18:07 Package: com.hhrb.maze
 */
public class Utils {

  public static final void writeCanvas(Canvas canvas, String output) throws IOException {
    DOMImplementation domi = GenericDOMImplementation.getDOMImplementation();
    String svgNS = "http://www.w3.org/2000/svg";
    Document document = domi.createDocument(svgNS, "svg", null);
    SVGGraphics2D g = new SVGGraphics2D(document);
    g.setPaint(Color.DARK_GRAY);
    java.awt.Shape shape = new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight());
    g.fill(shape);

    canvas.getRooms().stream().forEach(r -> {
      g.setPaint(Color.LIGHT_GRAY);
      Point leftBottom = r.getLeftBottom();
      g.fill(new Rectangle((int) leftBottom.getX(), (int) leftBottom.getY(), r.getWidth(),
                           r.getHeight()));
    });
    try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(output)))) {
      g.stream(writer);
    }
  }
}
