package tech.iooo.coco;

import io.vertx.core.Vertx;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import tech.iooo.coco.configuration.Constants;

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
    //for the free dynos will go to sleep after 30 minutes of inactivity
    vertx.setPeriodic(25 * 60 * 1000, click ->
        logger.info("click:[{}]", LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATETIME_FORMAT))));
  }
}
