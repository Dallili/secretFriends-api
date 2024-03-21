package org.dallili.secretfriends.repository;

import org.dallili.secretfriends.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
