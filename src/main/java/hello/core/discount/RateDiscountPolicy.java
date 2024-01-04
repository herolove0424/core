package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy") //문자이기 때문에 컴파일 단계에서 오류 못잡음
//@Primary
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

  int discountPercent = 10;


  @Override
  public int discount(Member member, int price) {
    if(member.getGrade() == Grade.VIP){
      return price * discountPercent / 100;
    }else {
      return 0;
    }
  }
}
