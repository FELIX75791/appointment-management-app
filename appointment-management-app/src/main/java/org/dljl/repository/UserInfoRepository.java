package org.dljl.repository;

import java.util.Optional;
import org.dljl.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByEmail(String email);

  Optional<UserInfo> findByName(String name);
}
