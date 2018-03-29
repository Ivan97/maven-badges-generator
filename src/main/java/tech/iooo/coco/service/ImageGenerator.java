package tech.iooo.coco.service;

/**
 * Created on 2018/3/29 下午5:27
 *
 * @author Ivan97
 */
public class ImageGenerator {

  private static final String URL_TEMPLATE = "https://img.shields.io/badge/maven--central-%s-brightgreen.svg";

  public static String generate(String version) {
    return String.format(URL_TEMPLATE, version);
  }
}
