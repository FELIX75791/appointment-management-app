package org.dljl.repository;

import java.util.Optional;
import org.dljl.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

  Optional<UserInfo> findByEmail(String email);
}
