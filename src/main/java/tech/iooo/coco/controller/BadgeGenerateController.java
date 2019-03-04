package tech.iooo.coco.controller;

import static tech.iooo.coco.configuration.Constants.REDIRECT_TO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.iooo.coco.configuration.Constants;
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

  @Autowired
  private MavenRepositoryResolver mavenRepositoryResolver;

  @GetMapping("/")
  public String index() {
    return REDIRECT_TO + "https://github.com/Ivan97/maven-badges-generator";
  }

  @Deprecated
  @GetMapping("/image/{groupId}/{artifactId}")
  public String redirectDependencyToImage(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + ImageGenerator.generate(mavenRepositoryResolver.resolve(groupId, artifactId));
  }

  @Deprecated
  @GetMapping("/public-image/{groupId}/{artifactId}")
  public String redirectPublicDependencyToImage(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + ImageGenerator.generate(mavenRepositoryResolver.resolvePublic(groupId, artifactId), ColorEnum.ORANGE);
  }

  @Deprecated
  @GetMapping("/dependency/classic/{groupId}/{artifactId}")
  public String redirectToClassicRepository(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + "https://search.maven.org/classic/#artifactdetails%7C" + groupId + "%7C"
        + artifactId + "%7C" + mavenRepositoryResolver.resolve(groupId, artifactId) + "%7Cjar";
  }

  @Deprecated
  @GetMapping("/dependency/{groupId}/{artifactId}")
  public String redirectToRepository(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + "https://search.maven.org/artifact/" + groupId + "/"
        + artifactId + "/" + mavenRepositoryResolver.resolve(groupId, artifactId) + "/jar";
  }

  @GetMapping("/maven-central/{groupId}/{artifactId}/badge.svg")
  public String originalServiceBadge(@PathVariable String groupId,
      @PathVariable String artifactId,
      @RequestParam(required = false, defaultValue = "plastic") String style,
      @RequestParam(required = false, defaultValue = "brightgreen") String color) {
    return REDIRECT_TO + ImageGenerator.generate(mavenRepositoryResolver.resolve(groupId, artifactId),
        ColorEnum.valueOf(color.replaceAll("-", "_").toUpperCase()),
        StyleEnum.valueOf(style.replaceAll("-", "_").toUpperCase())
    );
  }

  @GetMapping("/maven-public/{groupId}/{artifactId}/badge.svg")
  public String originalPublicServiceBadge(@PathVariable String groupId,
      @PathVariable String artifactId,
      @RequestParam(required = false, defaultValue = "plastic") String style,
      @RequestParam(required = false, defaultValue = "orange") String color) {
    return REDIRECT_TO + ImageGenerator.generatePublic(mavenRepositoryResolver.resolvePublic(groupId, artifactId),
        ColorEnum.valueOf(color.replaceAll("-", "_").toUpperCase()),
        StyleEnum.valueOf(style.replaceAll("-", "_").toUpperCase())
    );
  }

  @GetMapping("/maven-central/classic/{groupId}/{artifactId}")
  public String originalServiceClassicRepository(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + "https://search.maven.org/classic/#artifactdetails%7C" + groupId + "%7C"
        + artifactId + "%7C" + mavenRepositoryResolver.resolve(groupId, artifactId) + "%7Cjar";
  }

  @GetMapping("/maven-central/{groupId}/{artifactId}")
  public String originalServiceRepository(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + "https://search.maven.org/artifact/" + groupId + "/"
        + artifactId + "/" + mavenRepositoryResolver.resolve(groupId, artifactId) + "/jar";
  }

  @GetMapping("/maven-public/{groupId}/{artifactId}")
  public String publicServiceRepository(@PathVariable String groupId, @PathVariable String artifactId) {
    return REDIRECT_TO + Constants.PUBLIC_REPOSITORY + "/" + groupId.replaceAll("\\.", "/") + "/"
        + artifactId + "/" + mavenRepositoryResolver.resolvePublic(groupId, artifactId);
  }
}
