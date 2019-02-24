package com.qyer.dora.shape;

/**
 * User: Z J Wu Date: 2019/2/24 Time: 22:38 Package: com.qyer.dora.shape
 */
public class RCSegment {

  private final RCPoint from;
  private final RCPoint to;

  public RCSegment(RCPoint from, RCPoint to) {
    this.from = from;
    this.to = to;
  }

  public RCPoint getFrom() {
    return from;
  }

  public RCPoint getTo() {
    return to;
  }

  public int getFromRow() {
    return getFrom().getRow();
  }

  public int getFromColumn() {
    return getFrom().getColumn();
  }

  public int getToRow() {
    return getTo().getRow();
  }

  public int getToColumn() {
    return getTo().getColumn();
  }
}
