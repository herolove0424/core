package hello.core.member;

public class MemberServiceImpl implements MemberService{

  //final쓰는 이유? MemberRepository 타입 객체를 다른 객체로 바꾸지 못하도록 final사용
  /**
   * private MemberRepository memberRepository = new MemoryMemberRepository();
   * -> AppConfig에서 memberService를 생성 하므로서, repository를 appConfig에서 넣어줌
   * 그래서 생성자를 생성 !
   */

  private final MemberRepository memberRepository ;


  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /*public MemberServiceImpl() {
    this.memberRepository = memberRepository;
  }

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }*/

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
