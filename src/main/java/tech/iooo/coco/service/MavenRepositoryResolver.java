package tech.iooo.coco.service;

import java.util.List;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/3/29 下午8:34
 *
 * @author Ivan97
 */
@Service
public class MavenRepositoryResolver {

  private static RepositorySystem newRepositorySystem() {
    DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
    locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
    locator.addService(TransporterFactory.class, FileTransporterFactory.class);
    locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

    return locator.getService(RepositorySystem.class);
  }

  private static RepositorySystemSession newSession(RepositorySystem system) {
    DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

    LocalRepository localRepo = new LocalRepository("target/local-repo");
    session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

    return session;
  }

  public String resolve(String groupId, String artifactId) {

    RepositorySystem repoSystem = newRepositorySystem();
    RepositorySystemSession session = newSession(repoSystem);

    RemoteRepository central = new RemoteRepository.Builder("central", "default",
        "http://repo1.maven.org/maven2/").build();

    Artifact artifact = new DefaultArtifact(groupId + ":" + artifactId + ":[0,)");

    VersionRangeRequest rangeRequest = new VersionRangeRequest();
    rangeRequest.setArtifact(artifact);
    rangeRequest.addRepository(central);
    VersionRangeResult rangeResult = null;
    try {
      rangeResult = repoSystem.resolveVersionRange(session, rangeRequest);
    } catch (VersionRangeResolutionException e) {
      e.printStackTrace();
    }
    List<Version> versions = rangeResult.getVersions();

    if (versions.isEmpty()) {
      return null;
    } else {
      return versions.stream().max(Comparable::compareTo).get().toString();
    }
  }
}
