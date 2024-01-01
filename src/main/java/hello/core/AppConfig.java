package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration을 주석 처리하면 CGLIB 기술이 적용되지 않아 싱글톤이 보장안되서,
// ConfigurationSingletonTest 의 configurationTest테스트 실패
@Configuration
public class AppConfig {

  @Bean
  public MemberService memberService(){
    System.out.println("AppConfig.memberService");
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService(){
    System.out.println("AppConfig.orderService");
    return new OrderServiceImpl(memberRepository(), discountPolicy()); //의존관계 주입 중 필드 주입 테스트를 위해 OrderServiceImpl 에서 생성자를 없애면서, 여기 코드 변경
    //return null;
  }
  @Bean
  public DiscountPolicy discountPolicy() {
    //return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }

  //중요 ! static이 붙어있을 경우. 싱글톤을 보장하지 않음

  /**
   * 싱글톤을 보장 받으려면, 프록시로 인해 @Bean이 붙은 메서드를 호출하면 컨테이너에 동일한 빈이 존재하는지 확인하게 됩니다.
   * 그러나 프록시가 적용되지 않는 static 메서드는 컨테이너에 동일한 빈이 존재하는지에 대한 로직이 수행되지 않기 떄문에
   * 싱글톤을 보장하지 않습니다.
   * @return
   */
  @Bean
  public MemberRepository memberRepository() {
    System.out.println("AppConfig.memberRepository");
    return new MemoryMemberRepository();
  }





 /* @Bean
  public MemberService memberService(){
    return new MemberServiceImpl(memberRepository());
  }
  @Bean
  public static MemoryMemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
  @Bean
  public OrderService orderService(){
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }
  @Bean
  public static DiscountPolicy discountPolicy() {
    //return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }*/

}
