package org.dallili.secretfriends.security;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("해당하는 회원이 없습니다."));

        return new CustomUserDetails(member);
    }
}
