package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.MemberDTO;
import org.dallili.secretfriends.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/members")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원 데이터를 생성하고 id 반환")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody MemberDTO.SignUpRequest request){
        memberService.singUp(request);
    }

    @Operation(summary = "멤버 조회", description = "회원 데이터 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public MemberDTO.DetailsResponse MemberDetails(Authentication authentication){
        return memberService.findMember(Long.parseLong(authentication.getName()));
    }

    @Operation(summary = "로그인")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberDTO.LoginRequest request){
        String token = memberService.login(request);
        return token;
    }

    @Operation(summary = "비밀번호 변경")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/pw")
    public void passwordModify(@RequestBody @Valid MemberDTO.PasswordRequest request, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        memberService.modifyPassword(memberId, request);
    }

    @Operation(summary = "비속어 필터링 설정 변경")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/useFiltering")
    public void filteringModify(@RequestBody @Valid MemberDTO.FilteringUpdateRequest request, Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        memberService.modifyFiltering(memberId, request);
    }

    @Operation(summary = "회원정보 수정")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void memberModify(@RequestBody @Valid MemberDTO.ModifyRequest request,Authentication authentication){
        Long memberId = Long.parseLong(authentication.getName());
        memberService.modifyMember(memberId, request);
    }

}
