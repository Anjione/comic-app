//package com.example.comicbe.service.auth;
//
//import com.ielts.server.constant.SysConfigCode;
//import com.ielts.server.dto.device.UserDeviceDTO;
//import com.ielts.server.dto.sys.SysConfigDTO;
//import com.ielts.server.jpa.entity.UserDevice;
//import com.ielts.server.jpa.repository.UserDeviceRepository;
//import com.ielts.server.service.SysConfigService;
//import com.ielts.server.service.UserDeviceService;
//import com.ielts.server.utils.AppException;
//import com.ielts.server.utils.ErrorCode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service("UserDeviceServiceImpl")
//@Slf4j
//public class UserDeviceServiceImpl implements UserDeviceService {
//
//    @Autowired
//    private SysConfigService sysConfigService;
//
//    @Autowired
//    private UserDeviceRepository userDeviceRepository;
//
//    @Override
//    @Transactional
//    public void handleLogin(UserDeviceDTO dto) {
//        List<UserDevice> userDevices = userDeviceRepository.findByUserIdAndActiveTrue(dto.getUserId());
//        SysConfigDTO sysConfigDTO = sysConfigService.findByCode(SysConfigCode.LIMIT_DEVICE);
//
//        if (sysConfigDTO != null && StringUtils.hasText(sysConfigDTO.getValue()) && (userDevices.size() >= Integer.parseInt(sysConfigDTO.getValue())
//                    && userDevices.stream().noneMatch(item -> StringUtils.hasText(dto.getDeviceId()) && item.getDeviceId().equals(dto.getDeviceId())))) {
//            throw new AppException(ErrorCode.DEVICE_LIMIT);
//        }
//
//        UserDevice device = userDeviceRepository.findByUserIdAndDeviceId(dto.getUserId(), dto.getDeviceId())
//                .orElse(new UserDevice());
//        device.setUserId(dto.getUserId());
//        device.setDeviceId(dto.getDeviceId());
//        device.setLoginTime(LocalDateTime.now());
//        device.setExpireTime(dto.getExpireTime() != null ? dto.getExpireTime() : device.getExpireTime());
//        device.setActive(true);
//
//        userDeviceRepository.saveAndFlush(device);
//    }
//
//    @Override
//    @Transactional
//    public void handleLogout(Long userId, String deviceId) {
//        userDeviceRepository.findByUserIdAndDeviceId(userId, deviceId)
//                .ifPresent(device -> {
//                    device.setActive(false);
//                    userDeviceRepository.saveAndFlush(device);
//                });
//    }
//
//    @Override
//    @Transactional
//    public void updateActiveExpire() {
//        List<UserDevice> userDevices = userDeviceRepository.findByExpireTimeLessThanEqual(LocalDateTime.now());
//        userDevices = userDevices.stream().peek(item -> item.setActive(Boolean.FALSE)).collect(Collectors.toList());
//        userDeviceRepository.saveAllAndFlush(userDevices);
//    }
//}
