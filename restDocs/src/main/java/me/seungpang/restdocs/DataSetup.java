package me.seungpang.restdocs;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import me.seungpang.restdocs.member.Member;
import me.seungpang.restdocs.member.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSetup implements ApplicationRunner {

    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final List<Member> members = new ArrayList<>();

        members.add(new Member("test@gmail.com", "test"));
        members.add(new Member("test1@gmail.com", "test1"));
        members.add(new Member("test2@gmail.com", "test2"));
        members.add(new Member("test3@gmail.com", "test3"));

        memberRepository.saveAll(members);
    }
}
