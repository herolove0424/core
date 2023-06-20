package hello.core.discount;

import static org.junit.jupiter.api.Assertions.*;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {
  
  RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();
  
  //어찌돈겨
  @Test
  @DisplayName("VIP는 10%할인이 적용되야한다.")
  void VIP_O() {
    //G
    Member memberVIP = new Member(1L, "memberVIP", Grade.VIP);
    //W
    int discount = rateDiscountPolicy.discount(memberVIP, 10000);

    //T
    assertEquals(1000,discount);
  }

  @Test
  @DisplayName("VIP가 아니면 할인이 적용되지 않아야한다.")
  void VIP_x() {
    //G
    Member memberNone = new Member(1L, "memberNone", Grade.BASIC);
    //W
    int discount = rateDiscountPolicy.discount(memberNone, 10000);

    //T
    assertEquals(0,discount);
  }

}