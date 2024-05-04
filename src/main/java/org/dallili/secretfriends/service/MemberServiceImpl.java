package org.dallili.secretfriends.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.domain.MemberRole;
import org.dallili.secretfriends.dto.MemberDTO;
import org.dallili.secretfriends.security.JwtUtil;
import org.dallili.secretfriends.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

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

        Member member = findMemberById(memberID);

        MemberDTO.DetailsResponse response = modelMapper.map(member,MemberDTO.DetailsResponse.class);

        return response;
    }

    @Override
    public String login(MemberDTO.LoginRequest requestDTO){
        String email = requestDTO.getEmail();
        String pwd = requestDTO.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->{
            throw new UsernameNotFoundException(email + ": 존재하지 않는 회원입니다.");
        });
        if(!passwordEncoder.matches(pwd,member.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        MemberDTO.MemberInfo info = modelMapper.map(member, MemberDTO.MemberInfo.class);

        String accessToken = jwtUtil.generateToken(info);
        return accessToken;
    }

    @Override
    public Member findMemberById(Long memberID) {
        Member member = memberRepository.findById(memberID).orElseThrow(()->{
            throw new EntityNotFoundException(memberID + ": 존재하지 않는 회원입니다.");
        });
        return member;
    }

    @Override
    public void modifyPassword(Long memberID, MemberDTO.PasswordRequest requestDTO) {
        Member member = findMemberById(memberID);

        String oldPassword = member.getPassword();

        if(!(passwordEncoder.matches(requestDTO.getOldPassword(),oldPassword))){
            throw new IllegalArgumentException("현재 비밀번호가 틀립니다.");
        }

        if(!requestDTO.getNewPassword().equals(requestDTO.getConfirmPassword())){
            throw new IllegalArgumentException("새로 변경할 비밀번호 입력이 서로 동일하지 않습니다.");
        }

        member.changePassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        memberRepository.save(member);
    }

    @Override
    public void modifyMember(Long memberID, MemberDTO.ModifyRequest request) {
        Member member = findMemberById(memberID);
        member.modifyProfile(request.getNickname(),request.getBirthday());
        memberRepository.save(member);
    }

    @Override
    public Boolean findMemberUseFiltering (Long memberID){
        Member member = findMemberById(memberID);
        return member.isUseFiltering();
    }
}
