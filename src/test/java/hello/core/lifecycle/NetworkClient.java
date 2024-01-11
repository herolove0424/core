package hello.core.lifecycle;

import static java.lang.System.out;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * [스프링, 빈 생명주기 콜백 방법]
 * 1. 인터페이스 InitializingBean, DisposableBean
 * > 이제 implements InitializingBean, DisposableBean 는 너무 스프링 의존적이고, 수정이 안되서 잘 안씀
 * 2. 초기화 , 소멸 메소드 지정
 * > 설정정보(BeanLifeCycleTest 의 LifeCycleConfig 의 @Bean에 initMethod와 destroyMethod 지정
 * > 외부 라이브러리를 초기화 해야하거나, 종료해야하면 이걸 사용
 * 3. 애노테이션 @PostConstruct, @PreDestroy 이용
 * > 스프링에서도 권고
 * >다만 코드를 고칠수 없는 외부 라이브러리에서는 사용못하므로, 필요할 경우 2번 방법 사용
 */
public class NetworkClient /*implements InitializingBean, DisposableBean*/ {

  private String url;

  public NetworkClient() {
    out.println("생성자 호출, url = " + url);

  }

  public void setUrl(String url) {
    this.url = url;
  }

  //서비스 시작시 호출
  public void connect() {
    out.println("connect : " + url);
  }

  public void call(String message) {
    out.println("call: " + url + " message = " + message);
  }

  //서비스 종료시 호출
  public void disconnect(){
    out.println("close : " + url);
  }


  /*
  1. 인터페이스 이용
  //의존관계 주입이 끝나면 호출해주겠다
  @Override
  public void afterPropertiesSet() throws Exception {
    out.println("NetworkClient.afterPropertiesSet");
    connect();
    call("초기화 연결 메시지");
  }


  @Override
  public void destroy() throws Exception {
    out.println("NetworkClient.destroy");
    disconnect();
  }*/

  /**
   * 2. 초기화 , 소멸 메소드 지정 > @Bean(initMethod = "init", destroyMethod = "close")
   * 3. 애노테이션 이용 @PostConstruct @PreDestroy
   */
  @PostConstruct
  public void init(){
    out.println("NetworkClient.init");
    connect();
    call("초기화 연결 메시지");
  }

  //close나 shutdown으로 메소드 이름을 지정하면 @Bean에 종료 메서드 따로 지정안해도 자동으로 호출해줌
  @PreDestroy
  public void close(){
    out.println("NetworkClient.close");
    disconnect();
  }
}
