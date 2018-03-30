package tech.iooo.coco.domain;

/**
 * Created on 2018/3/30 下午2:13
 *
 * @author Ivan97
 */
public enum StyleEnum {

  PLASTIC("plastic"), FLAT("flat"), FLAT_SQUARE("flat--square"), FOR_THE_BADGE(
      "for--the--badge"), SOCIAL("social");

  private final String style;

  StyleEnum(final String style) {
    this.style = style;
  }

  @Override
  public String toString() {
    return style;
  }

}
