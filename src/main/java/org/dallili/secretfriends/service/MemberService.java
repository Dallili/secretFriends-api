package org.dallili.secretfriends.service;

import org.dallili.secretfriends.dto.MemberDTO;

public interface MemberService{

    public void singUp(MemberDTO.SignUpRequest requestDTO);
    public MemberDTO.DetailsResponse findMember(Long memberID);
}
