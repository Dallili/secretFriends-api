package org.dallili.secretfriends.service;

import lombok.RequiredArgsConstructor;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.domain.MemberRole;
import org.dallili.secretfriends.dto.MemberDTO;
import org.dallili.secretfriends.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void singUp(MemberDTO.SignUpRequest requestDTO){

        if (memberRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        Member member = modelMapper.map(requestDTO,Member.class);
        member.changePassword(passwordEncoder.encode(requestDTO.getPassword()));
        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Override
    public MemberDTO.DetailsResponse findMember(Long memberID) {

        Member member = memberRepository.findById(memberID).orElseThrow(()->{
            throw new IllegalStateException(memberID + ": 존재하지 않는 회원입니다.");
        });

        MemberDTO.DetailsResponse response = modelMapper.map(member,MemberDTO.DetailsResponse.class);

        return response;
    }
}
