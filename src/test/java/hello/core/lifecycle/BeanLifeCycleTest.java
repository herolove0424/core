package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

  @Test
  public void lifeCycleTest(){

    //ApplicationContext는 close를 하지 않으므로, ConfigurableApplicationContext로 바꾸는것
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    NetworkClient client = ac.getBean(NetworkClient.class);
    ac.close();
  }

  @Configuration
  static class LifeCycleConfig{

    //2. 초기화 , 소멸 메소드 지정
    //@Bean(initMethod = "init", destroyMethod = "close")
    //@Bean(initMethod = "init")
    @Bean
    public NetworkClient networkClient(){
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("https://hellp-spring.dev");
      return networkClient;
    }
  }

}
