package org.dallili.secretfriends.notify.repository;

import org.dallili.secretfriends.notify.domain.Notify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyRepository extends JpaRepository<Notify, Long> {

}