package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
