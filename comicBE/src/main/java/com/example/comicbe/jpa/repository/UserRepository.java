package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(@Param("username") String username);
    User findByUsernameAndEnabled(@Param("username") String username, @Param("enabled") boolean enabled);

    User findByEmailOrUsernameAndEnabled(@Param("email") String email, @Param("username") String username, @Param("enabled") boolean enabled);

    User findBySocialIdLoginAndEnabled(@Param("socialIdLogin") String socialIdLogin, @Param("enabled") boolean enabled);

    List<User> findAllByEnabled(@Param("enabled") boolean enabled);

    List<User> findAllByEnabledAndPremiumExpireTimeLessThan(@Param("enabled") boolean enabled, @Param("expireTime") LocalDateTime localDateTime);

    List<User> findAllByEnabledAndPremiumExpireTimeGreaterThanEqual(@Param("enabled") boolean enabled, @Param("expireTime") LocalDateTime localDateTime);

    Optional<User> findByConfirmRegisterToken(String confirmToken);
}
