package tech.iooo.coco.service;

import tech.iooo.coco.configuration.Constants;
import tech.iooo.coco.domain.ColorEnum;
import tech.iooo.coco.domain.StyleEnum;

/**
 * Created on 2018/3/29 下午5:27
 *
 * @author Ivan97
 */
public class ImageGenerator {

  private static final String URL_TEMPLATE = "https://img.shields.io/badge/maven--%s-%s-%s.svg?longCache=true";
  private static final String STYLE_SUFFIX = "&style=";


  public static String generate(String version) {
    return generate(Constants.CENTRAL, version, ColorEnum.BRIGHTGREEN, StyleEnum.FLAT);
  }

  public static String generate(String version, ColorEnum color) {
    return generate(Constants.CENTRAL, version, color, StyleEnum.FLAT);
  }

  public static String generate(String version, StyleEnum style) {
    return generate(Constants.CENTRAL, version, ColorEnum.BRIGHTGREEN, style);
  }

  public static String generatePublic(String version) {
    return generate(Constants.PUBLIC, version, ColorEnum.BRIGHTGREEN, StyleEnum.FLAT);
  }

  public static String generatePublic(String version, ColorEnum color) {
    return generate(Constants.PUBLIC, version, color, StyleEnum.FLAT);
  }

  public static String generatePublic(String version, StyleEnum style) {
    return generate(Constants.PUBLIC, version, ColorEnum.BRIGHTGREEN, style);
  }

  public static String generate(String type, String version, StyleEnum style) {
    return generate(type, version, ColorEnum.BRIGHTGREEN, style);
  }

  public static String generate(String version, ColorEnum color, StyleEnum style) {
    return generate(Constants.CENTRAL, version, color, style);
  }

  public static String generatePublic(String version, ColorEnum color, StyleEnum style) {
    return generate(Constants.PUBLIC, version, color, style);
  }

  public static String generate(String type, String version, ColorEnum color, StyleEnum style) {
    return String.format(URL_TEMPLATE, type, version.replaceAll("-", "--"), color) + STYLE_SUFFIX + style;
  }
}
