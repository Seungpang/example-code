package me.seungpang.restdocs.member;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRepository memberRepository;

    @GetMapping("/{id}")
    public MemberResponse getMember(@PathVariable Long id) {
        return new MemberResponse(memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Notfound")));
    }

    @PostMapping
    public void createMember(@RequestBody @Valid MemberSignupRequest dto) {
        memberRepository.save(dto.toEntity());
    }

    @PutMapping("/{id}")
    public void modify(
        @PathVariable Long id,
        @RequestBody @Valid MemberModificationRequest dto
    ) {
        final Member member = memberRepository.findById(id).get();
        member.modify(dto.getName());
        memberRepository.save(member);
    }

    @GetMapping
    public Page<MemberResponse> getMembers(
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable
    ) {
        return memberRepository.findAll(pageable).map(MemberResponse::new);
    }
}
