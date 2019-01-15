package tech.iooo.coco.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2019-01-15 11:00
 *
 * @author <a href="mailto:yangkizhang@gmail.com?subject=maven-badges-generator">Ivan97</a>
 */
@Controller
public class ExceptionController implements ErrorController {

  @RequestMapping("error")
  public String handleError(HttpServletRequest request) {
    return "error.html";
  }

  @Override
  public String getErrorPath() {
    return "error.html";
  }
}
