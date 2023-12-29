package hello.core;

import static java.lang.System.out;

import hello.core.member.Grade;
import hello.core.member.Member;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

  public static void main(String[] args) {

    /*AppConfig appConfig = new AppConfig();

    //MemberService memberService = new MemberServiceImpl();
    MemberService memberService = appConfig.memberService();*/
    //-> AppConfig에서 스프링을 사용하는 버전으로 변경

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);

    out.println("member = " + member);
    out.println("findMember = " + findMember);


    //AppConfig appConfig = new AppConfig();
    //MemberService memberService = appConfig.memberService();

    //이렇게 하면 ApplicationContext가 AppConfig.class가 가지고 있는 설정 정보를 가지고 스프링이 스프링빈에 다 관리
    /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

    Member memberA = new Member(1L, "memberA", Grade.VIP);
    memberService.join(memberA);

    Member findMember = memberService.findMember(1L);
    out.println("memberA = " + memberA.getName());
    out.println("findMember = " + findMember.getName());*/
  }

}
