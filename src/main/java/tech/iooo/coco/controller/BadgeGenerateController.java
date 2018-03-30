package tech.iooo.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.iooo.coco.domain.ColorEnum;
import tech.iooo.coco.domain.StyleEnum;
import tech.iooo.coco.service.ImageGenerator;
import tech.iooo.coco.service.MavenRepositoryResolver;

/**
 * Created on 2018/3/29 下午5:12
 *
 * @author Ivan97
 */
@Controller
public class BadgeGenerateController {

  private static final String REDIRECT_TO = "redirect:";

  @Autowired
  private MavenRepositoryResolver mavenRepositoryResolver;

  @GetMapping("/")
  public String index() {
    return REDIRECT_TO + "https://github.com/Ivan97/maven-badges-generator";
  }

  @GetMapping("/image/{groupId}/{artifactId}")
  public String redirectDependencyToImage(@PathVariable String groupId,
      @PathVariable String artifactId) {
    return REDIRECT_TO + ImageGenerator
        .generate(mavenRepositoryResolver.resolve(groupId, artifactId));
  }


  @GetMapping("/dependency/{groupId}/{artifactId}")
  public String redirectToRepository(@PathVariable String groupId,
      @PathVariable String artifactId) {
    return REDIRECT_TO + "http://search.maven.org/#artifactdetails%7C" + groupId + "%7C"
        + artifactId + "%7C" + mavenRepositoryResolver.resolve(groupId, artifactId) + "%7Cpom";
  }

  @GetMapping("/maven-central/{groupId}/{artifactId}/badge.svg")
  public String originalServiceBadge(@PathVariable String groupId,
      @PathVariable String artifactId,
      @RequestParam(required = false, defaultValue = "plastic") String style,
      @RequestParam(required = false, defaultValue = "brightgreen") String color) {
    return REDIRECT_TO + ImageGenerator
        .generate(mavenRepositoryResolver.resolve(groupId, artifactId),
            ColorEnum.valueOf(color.toUpperCase()), StyleEnum.valueOf(style.toUpperCase()));
  }

  @GetMapping("/maven-central/{groupId}/{artifactId}")
  public String originalServiceRepository(@PathVariable String groupId,
      @PathVariable String artifactId) {
    return REDIRECT_TO + "http://search.maven.org/#artifactdetails%7C" + groupId + "%7C"
        + artifactId + "%7C" + mavenRepositoryResolver.resolve(groupId, artifactId) + "%7Cpom";
  }
}
