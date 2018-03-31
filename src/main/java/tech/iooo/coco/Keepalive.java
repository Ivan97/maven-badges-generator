package tech.iooo.coco;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created on 2018/3/31 下午1:04
 *
 * @author Ivan97
 */
public class Keepalive implements ApplicationListener<ApplicationPreparedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(Keepalive.class);

  @Override
  public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
    Vertx vertx = Vertx.vertx();

    WebClientOptions options = new WebClientOptions()
        .setConnectTimeout(2000)
        .setUserAgent("iooo.tech/maven-badge-generator").setKeepAlive(false);
    WebClient webClient = WebClient.create(vertx, options);

    vertx.setPeriodic(25 * 60 * 1000, click ->
        webClient.get(80, "maven-badges.iooo.tech", "/").send(event -> {
          if (event.succeeded()) {
            logger.info("statusCode:{}", event.result().statusCode());
          } else {
            logger.error("", event.cause());
          }
        }));
  }
}
