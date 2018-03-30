package tech.iooo.coco.service;

import tech.iooo.coco.domain.ColorEnum;
import tech.iooo.coco.domain.StyleEnum;

/**
 * Created on 2018/3/29 下午5:27
 *
 * @author Ivan97
 */
public class ImageGenerator {

  private static final String URL_TEMPLATE = "https://img.shields.io/badge/maven--central-%s-%s.svg?longCache=true";
  private static final String STYLE_SUFFIX = "&style=";


  public static String generate(String version) {
    return generate(version, ColorEnum.BRIGHTGREEN, StyleEnum.PLASTIC);
  }

  public static String generate(String version, ColorEnum color) {
    return generate(version, color, StyleEnum.PLASTIC);
  }

  public static String generate(String version, StyleEnum style) {
    return generate(version, ColorEnum.BRIGHTGREEN, style);
  }

  public static String generate(String version, ColorEnum color, StyleEnum style) {
    return String.format(URL_TEMPLATE, version, color) + STYLE_SUFFIX + style;
  }
}
