package tech.iooo.coco;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * Created on 2018/3/31 下午1:04
 *
 * @author Ivan97
 */
public class Keepalive implements ApplicationListener<ApplicationPreparedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(Keepalive.class);

  private WebClient client;

  @Override
  public void onApplicationEvent(@NonNull ApplicationPreparedEvent applicationPreparedEvent) {
    Vertx vertx = Vertx.vertx();
    this.client = WebClient.create(vertx);
    vertx.setPeriodic(25 * 60 * 1000, click -> checkStatus());
  }

  /**
   * keepalive
   */
  private void checkStatus() {
    client
        .get(443, "coco.iooo.tech", "/")
        .ssl(true)
        .send(ar -> {
          if (ar.succeeded()) {
            HttpResponse<Buffer> response = ar.result();
            if (logger.isTraceEnabled()) {
              logger.trace("Received response with status code {}", response.statusCode());
            }
          } else {
            logger.error("Something went wrong {}", ar.cause().getMessage());
          }
        });
  }
}
