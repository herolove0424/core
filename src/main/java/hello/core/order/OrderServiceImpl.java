package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //역할? final이 붙은 변수를 가지는 생성자를 만들어줘
public class OrderServiceImpl implements OrderService{

  /**
   * 아래 코드는 OCP위반, 할인 정책을 바꿀때마다 코드를 바꿔줘야해 !
   */
  //private DiscountPolicy discountPolicy = new RateDiscountPolicy();
  //private DiscountPolicy discountPolicy = new FixDiscountPolicy();
  //이 코드로 사용

  private final DiscountPolicy discountPolicy; //아래 SETTER을 위해 final 삭제 (수정자 주입) -> //의존관계 주입 3) 필드 주입을 위해 setter 및 생성자 삭제 후 여기에  @Autowired 를 붙임 -> 단 이렇게 할 경우 임의 자바 테스트 코드가 돌아가지 않아, setter를 또 만들어줘야 하기 때문에, 이렇게 쓰기보단 수정자 주입으로 쓰는게 낫다 ~

  //private MemberRepository memberRepository = new MemoryMemberRepository();
  private final MemberRepository memberRepository; //아래 SETTER을 위해 final 삭제(수정자 주입)

 /* public OrderServiceImpl() {
  }*/

  /**
   * 의존관계 주입 2) 수정자 주입
   * @Autowired  없으면 생성자 호출 시, setter메소드 실행 안됌
   * 또 주입할 대상이 없으며, 오류가 발생하는데, 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)를 붙여주면된다.
   */
 /* @Autowired
  public void setDiscountPolicy(DiscountPolicy discountPolicy) {
    System.out.println("discountPolicy setDiscountPolicy = " + discountPolicy);
    this.discountPolicy = discountPolicy;
  }*/

  //의존관계 주입 2) 수정자 주입
  /*@Autowired
  public void setMemberRepository(MemberRepository memberRepository) {
    System.out.println("memberRepository setDiscountPolicy = " + memberRepository);
    this.memberRepository = memberRepository;
  }*/

  //의존관계 주입 1) 생성자 주입, 생성자가 1개일 경우, @Autowired를 안붙여도 자동으로 붙음
  // setter메소드 +@Autowired 조합(수정자 주입) 사용 시, 아래 생성자 코드 없어도 상관없음. 현재는 없으면 코드 오류가 나기 때문에 나둠

  @Autowired //생성자가 하나인 경우 생략 가능
  //위의 @RequiredArgsConstructor  로 인해 아래 생성자도 생략 가능
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy ) {
    System.out.println("memberRepository 생성자1 = " + memberRepository);
    System.out.println("discountPolicy 생성자1 = " + discountPolicy);
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  //1. 조회 빈이 2개 이상일때 > 파라미터 명으로 매칭
 /* public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy ) {
    System.out.println("memberRepository 생성자 = " + memberRepository);
    System.out.println("discountPolicy 생성자 = " + rateDiscountPolicy);
    this.memberRepository = memberRepository;
    this.discountPolicy = rateDiscountPolicy;
  }*/

  //2. 조회 빈이 2개 이상일때 > @Qualifier를 적용해서 찾기
 /* @Autowired //생성자가 하나인 경우 생략 가능
  public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy ) {
    System.out.println("memberRepository 생성자 = " + memberRepository);
    System.out.println("discountPolicy 생성자 = " + discountPolicy);
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }*/

  //의존관계 주입 4) 일반 메서드 주입
 /* @Autowired
  public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }*/

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);
    return new Order(memberId, itemName, itemPrice, discountPrice );
  }
  
  //test용
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

  //private final MemberRepository memberRepository;
  //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

  //위에 코드는 어떻게 하던 구현체에 의존하게 돼 ! 그래서 어떻게한다? > 아래처럼! > 이후 전체 동작방식 을 구성하는 설정클래슬를 만들자
  // final은 무조건 값이 대입되어야하므로 제거
  //appconfig를 통해, 이제 OrderServiceImpl는 DiscountPolicy에만 의존하지, 이를 구현하는 fix나 ratepolicy와는 무관하다
  /*private DiscountPolicy discountPolicy;

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }*/
}
