package hello.core.singleton;


import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

  @Test
  void statefulServiceSingleton(){
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
    StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

    //ThreadA: A사용자 10000원 주문
    statefulService1.order("userA", 10000);
    int userAPrice = statefulService1.order2("userA", 10000);

    //ThreadB: B사용자 20000원 주문
    statefulService1.order("userB", 20000);
    int userBPrice = statefulService1.order2("userB", 20000);

    int price = statefulService1.getPrice();
    System.out.println("price = " + price);
    System.out.println("userAPrice = " + userAPrice);

    assertThat(statefulService1.getPrice()).isEqualTo(20000);
  }


  static class TestConfig{

    @Bean
    public StatefulService statefulService(){
      return new StatefulService();
    }
  }
}