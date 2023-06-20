package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{

  private final MemberRepository memberRepository;
  //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

  //위에 코드는 어떻게 하던 구현체에 의존하게 돼 ! 그래서 어떻게한다? > 아래처럼! > 이후 전체 동작방식 을 구성하는 설정클래슬를 만들자
  // final은 무조건 값이 대입되어야하므로 제거
  //appconfig를 통해, 이제 OrderServiceImpl는 DiscountPolicy에만 의존하지, 이를 구현하는 fix나 ratepolicy와는 무관하다
  private DiscountPolicy discountPolicy;

  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
