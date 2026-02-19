//package com.example.comicbe.jpa.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>, JpaSpecificationExecutor<UserDevice> {
//
//    List<UserDevice> findByUserIdAndActiveTrue(Long userId);
//
//    Optional<UserDevice> findByUserIdAndDeviceId(Long userId, String deviceId);
//
//    List<UserDevice> findByExpireTimeLessThanEqual(LocalDateTime expireTime);
//}
