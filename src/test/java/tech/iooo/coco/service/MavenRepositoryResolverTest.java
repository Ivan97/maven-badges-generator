package tech.iooo.coco.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2019-03-04 14:08
 *
 * @author <a href="mailto:yangkizhang@gmail.com?subject=maven-badges-generator">Ivan97</a>
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MavenRepositoryResolverTest {

  @Autowired
  private MavenRepositoryResolver repositoryResolver;

  @Test
  public void resolve() {
    System.out.println(repositoryResolver.resolve("tech.iooo.boot", "iooo-boot-core"));
  }

  @Test
  public void resolveSnapshot() {
    System.out.println(repositoryResolver.resolvePublic("tech.iooo.boot", "iooo-boot-core"));

  }
}
