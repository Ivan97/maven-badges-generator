package tech.iooo.coco.service;

import java.util.Objects;
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
import org.eclipse.aether.repository.RepositoryPolicy;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.springframework.stereotype.Service;
import tech.iooo.coco.configuration.Constants;

/**
 * Created on 2018/3/29 下午8:34
 *
 * @author Ivan97
 */
@Service
public class MavenRepositoryResolver {

  private RepositorySystem repositorySystem;
  private RepositorySystemSession repositorySystemSession;

  private RepositorySystemSession newSession(RepositorySystem system) {
    if (Objects.isNull(repositorySystemSession)) {
      DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
      LocalRepository localRepo = new LocalRepository("target/local-repo");
      session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
      repositorySystemSession = session;
    }

    return repositorySystemSession;
  }

  private RepositorySystem newRepositorySystem() {
    if (Objects.isNull(repositorySystem)) {
      DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
      locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
      locator.addService(TransporterFactory.class, FileTransporterFactory.class);
      locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
      repositorySystem = locator.getService(RepositorySystem.class);
    }
    return repositorySystem;
  }

  public String resolve(String groupId, String artifactId) {
    return resolve(groupId, artifactId, Constants.CENTRAL);
  }

  public String resolvePublic(String groupId, String artifactId) {
    return resolve(groupId, artifactId, Constants.PUBLIC);
  }

  private String resolve(String groupId, String artifactId, String id) {

    RepositorySystem repoSystem = newRepositorySystem();
    RepositorySystemSession session = newSession(repoSystem);

    RemoteRepository remoteRepository;
    if (Objects.equals(id, Constants.CENTRAL)) {
      remoteRepository = new RemoteRepository.Builder(Constants.CENTRAL, "default", Constants.CENTRAL_REPOSITORY)
          .setReleasePolicy(new RepositoryPolicy(true, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN))
          .setSnapshotPolicy(new RepositoryPolicy(false, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN))
          .build();
    } else {
      remoteRepository = new RemoteRepository.Builder(Constants.PUBLIC, "default", Constants.PUBLIC_REPOSITORY)
          .setReleasePolicy(new RepositoryPolicy(true, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN))
          .setSnapshotPolicy(new RepositoryPolicy(false, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN))
          .build();
    }

    Artifact artifact = new DefaultArtifact(groupId + ":" + artifactId + ":[0,)");

    VersionRangeRequest rangeRequest = new VersionRangeRequest();
    rangeRequest.setArtifact(artifact);
    rangeRequest.addRepository(remoteRepository);
    VersionRangeResult rangeResult = null;
    try {
      rangeResult = repoSystem.resolveVersionRange(session, rangeRequest);
    } catch (VersionRangeResolutionException e) {
      e.printStackTrace();
    }

    if (Objects.nonNull(rangeResult) && Objects.nonNull(rangeResult.getVersions()) && rangeResult.getVersions().isEmpty()) {
      return null;
    } else {
      return rangeResult.getVersions().stream().max(Comparable::compareTo).get().toString();
    }
  }
}
