package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class), basePackages = "hello.core")
public class AutoAppConfig {

  /**
   * 스프링 부트로 돌릴 경우 아래와 같은 오류가 난다 (자동빈과 수동빈 이름이 겹칠경우)
   * The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class],
   * could not be registered. A bean with that name has already been defined in file [E:\2023_Study\core\out\production\classes\hello\core\member\MemoryMemberRepository.class] and overriding is disabled.
   * application.properties에 spring.main.allow-bean-definition-overriding=true 로 하면 오버라이딩 할 수 있게 해줌, false면 오류나
   * @return
   */
  /*@Bean(name = "memoryMemberRepository")
  public MemberRepository memberRepository(){
    return new MemoryMemberRepository();
  }*/



}
