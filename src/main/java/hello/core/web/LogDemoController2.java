package hello.core.web;

import hello.core.common.MyLogger;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController2 {


  private final LogDemoService2 logDemoService;
  //private final ObjectProvider<MyLogger> myLoggerObjectProvider;
  private final MyLogger myLogger;

  @RequestMapping("log-demo2")
  @ResponseBody
  public String logDemo(HttpServletRequest request){
    String requestURL = request.getRequestURI().toString();
    //MyLogger myLogger = myLoggerObjectProvider.getObject();
    myLogger.setRequestURL(requestURL);

    myLogger.log("controller test");
    logDemoService.logic("testId");
    return "OK";
  }

}
