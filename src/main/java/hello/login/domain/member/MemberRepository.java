package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepository {

    private static final HashMap<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        
        log.info("save={}", member);
        store.put(member.getId(), member);

        return member;
    }

    public Member findById(Long id) {

        return store.get(id);
    }

    public List<Member> findAll() {

        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId) {

        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();

    }

    public void clearStore() {
        store.clear();
    }

/*
    public Member update(Long id, Member updateParam) {
        Member member = store.get(id);

        member.setName(updateParam.getName());
        member.setLoginId(updateParam.getLoginId());
        member.setPassword(updateParam.getPassword());

    }
*/

}
