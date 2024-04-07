package org.dallili.secretfriends.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dallili.secretfriends.dto.MemberDTO;
import org.dallili.secretfriends.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원 데이터를 생성하고 id 반환")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void signUp(@Valid @RequestBody MemberDTO.SignUpRequest request){
        memberService.singUp(request);
    }

    @Operation(summary = "멤버 조회", description = "회원 데이터 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{memberID}")
    public MemberDTO.DetailsResponse MemberDetails(@PathVariable Long memberID){
        return memberService.findMember(memberID);
    }

}
