package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

//싱글톤 빈과 프로토타입 빈을 함께 사용할 때 어떻게하면 사용할때마다 항상 새로운 프로토 타입 빈을 생성할 수 있을까?
// 아래는 좋지 않은 방법
public class SingletonWithPrototypeTest2 {


  @Test
  void providerTest(){
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
        ClientBean.class, PrototypeBean.class);
    ClientBean clientBean1 = ac.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);

    ClientBean clientBean2 = ac.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);
  }

  @Scope("singleton")
  static class ClientBean{

    @Autowired
    private  ApplicationContext ac;

    public int logic(){
      PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);

      prototypeBean.addCount();
      int count = prototypeBean.getCount();
      return count;
    }
  }


  @Scope("prototype")
  static class PrototypeBean{

    private int count = 0;

    public void addCount(){
      count++;
    }

    public int getCount() {
      return count;
    }

    @PostConstruct
    public void init(){
      System.out.println("PrototypeBean.init : "+this);
    }

    @PreDestroy
    public void destroy(){
      System.out.println("PrototypeBean.destroy : "+this);
    }
  }

}
