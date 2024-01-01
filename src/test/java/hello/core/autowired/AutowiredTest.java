package hello.core.autowired;

import hello.core.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

public class AutowiredTest {

  @Test
  void AutowiredOption(){
    ApplicationContext ac =  new AnnotationConfigApplicationContext(TestBean.class); //이렇게 하면 컴포넌트 스캔하는 것 처럼 스프링에 등록해줌
  }

  static class TestBean{

    @Autowired(required = false)
    public void setNoBean1(Member noBean1){
      System.out.println("noBean1 = " + noBean1);
    }

    @Autowired
    public void setNoBean2(@Nullable Member noBean2){
      System.out.println("noBean2 = " + noBean2);
    }

    @Autowired(required = false)
    public void setNoBean3(Optional<Member> noBean3){
      System.out.println("noBean3 = " + noBean3);
    }



  }

}
