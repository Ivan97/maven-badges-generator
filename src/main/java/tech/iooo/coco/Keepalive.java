package tech.iooo.coco;

import io.vertx.core.Vertx;
import java.io.IOException;
import org.apache.commons.net.telnet.TelnetClient;
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
    vertx.setPeriodic(25 * 60 * 1000, click -> checkStatus());
  }

  /**
   * 检测远程服务器是否可以连接
   */
  private void checkStatus() {
    TelnetClient client = new TelnetClient();
    try {
      client.setDefaultTimeout(3000);
      client.connect("www.iooo.tech", 80);
      logger.info("keepalive");
    } catch (Exception e) {
      logger.warn("remote server: [www.iooo.tech] telnet failed");
    } finally {
      try {
        client.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
