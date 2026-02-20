//package com.example.comicbe.service.auth;
//
//
//import com.example.comicbe.jpa.entity.Role;
//import com.example.comicbe.jpa.repository.RoleRepository;
//import com.example.comicbe.payload.filter.RoleFilter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//@Service
//@Slf4j
//public class RoleServiceImpl implements RoleService {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public List<RoleComboBox> findCombobox(String filterText) {
//        RoleFilter filter = RoleFilter.builder().roleText(filterText).build();
//        RoleSpec spec = new RoleSpec(filter);
//        List<Role> roles = roleRepository.findAll(spec);
//
//        return roles.stream()
//                .map(role -> RoleComboBox.builder()
//                        .value(role.getName())
//                        .title(role.getName())
//                        .build())
//                .collect(Collectors.toList());
//    }
//}
