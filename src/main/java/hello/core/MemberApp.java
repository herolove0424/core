package hello.core;

import static java.lang.System.out;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

  public static void main(String[] args) {
    MemberService memberService = new MemberServiceImpl();
    Member memberA = new Member(1L, "memberA", Grade.VIP);
    memberService.join(memberA);

    Member findMember = memberService.findMember(1L);
    out.println("memberA = " + memberA.getName());
    out.println("findMember = " + findMember.getName());
  }

}