package hello.core.scope;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

//싱글톤 빈과 프로토타입 빈을 함께 사용할 때 어떻게하면 사용할때마다 항상 새로운 프로토 타입 빈을 생성할 수 있을까?
// objectFactory, objectProvider를 이용해 지정한 프로토 타입빈을 컨테이너에서 대신 찾아주는 DL정도의 기능을 제공
public class SingletonWithPrototypeTest3 {


  @Test
  void singletonClientUsePrototype(){
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
  //@RequiredArgsConstructor
  static class ClientBean{

    //private final PrototypeBean prototypeBean; //생성 시점에 주입
    
    @Autowired
    private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 필드로 주입 받기, ObjectProvider는 필요할때마다 직접 필요한 의존관계를 찾아!

   /* @Autowired
    public clientBean(PrototypeBean prototypeBean) {
      this.prototypeBean = prototypeBean;
    }*/

    public int logic(){
      PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
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
