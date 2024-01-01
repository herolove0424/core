package hello.core.member;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class MemoryMemberRepository implements MemberRepository {
  
  //map은 동시성 이슈가 발생할 수 있으므로 이런 경우 ConcurrentHashMap 이용
  private static ConcurrentHashMap<Long, Member> store = new ConcurrentHashMap<>();

  @Override
  public void save(Member member) {
    store.put(member.getId(), member);
  }

  @Override
  public Member findById(Long memberId) {
    return store.get(memberId);
  }
}
