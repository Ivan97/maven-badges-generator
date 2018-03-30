package tech.iooo.coco.domain;

/**
 * Created on 2018/3/30 下午2:18
 *
 * @author Ivan97
 */
public enum ColorEnum {

  BRIGHTGREEN("brightgreen"), GREEN("green"), YELLOWGREEN("yellowgreen"), YELLOW("yellow"), ORANGE(
      "orange"), RED("red"), LIGHTGREY("lightgrey"), BLUE("blue");


  private final String color;

  ColorEnum(final String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return color;
  }
}
