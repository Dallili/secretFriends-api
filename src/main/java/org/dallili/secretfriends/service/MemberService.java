package org.dallili.secretfriends.service;

import org.dallili.secretfriends.domain.Member;
import org.dallili.secretfriends.dto.MemberDTO;

public interface MemberService{

    public void singUp(MemberDTO.SignUpRequest requestDTO);
    public MemberDTO.DetailsResponse findMember(Long memberID);
    public String login(MemberDTO.LoginRequest requestDTO);
    public Member findMemberById(Long memberID);
    public void modifyPassword(Long memberID, String password);
    public void modifyMember(Long memberID, MemberDTO.ModifyRequest request);
    public Boolean findMemberUseFiltering (Long memberID);
}
