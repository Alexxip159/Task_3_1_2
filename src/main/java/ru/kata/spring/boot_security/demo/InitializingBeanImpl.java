package ru.kata.spring.boot_security.demo;//package ru.kata.spring.boot_security.demo;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.repository.RoleRepository;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class InitializingBeanImpl implements InitializingBean {
//
//    private final UserService userService;
//    private final RoleRepository roleRepository;
//
//    public InitializingBeanImpl(UserService userService, RoleRepository roleRepository) {
//        this.userService = userService;
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    @Transactional
//    public void afterPropertiesSet() throws Exception {
//        User user = userService.findUserByUsername("admin");
//
//        if (user == null) {
//            user = new User();
//            user.setUsername("admin");
//            user.setPassword("admin");
//
//            Set<Role> roles = new HashSet<>();
//
//            Role role1 = roleRepository.findByName("ROLE_ADMIN");
//            if (role1 == null) {
//                role1 = new Role();
//                role1.setName("ROLE_ADMIN");
//            }
//            roles.add(role1);
//
//            Role role2 = roleRepository.findByName("ROLE_USER");
//            if (role2 == null) {
//                role2 = new Role();
//                role2.setName("ROLE_USER");
//            }
//            roles.add(role2);
//            user.setRoles(roles);
//            userService.addUser(user);
//        } else if (user.getRoles() == null || user.getRoles().size() == 0) {
//            Set<Role> roles = new HashSet<>();
//
//            Role role1 = roleRepository.findByName("ROLE_ADMIN");
//            if (role1 == null) {
//                role1 = new Role();
//                role1.setName("ROLE_ADMIN");
//            }
//            roles.add(role1);
//
//            Role role2 = roleRepository.findByName("ROLE_USER");
//            if (role2 == null) {
//                role2 = new Role();
//                role2.setName("ROLE_USER");
//            }
//            roles.add(role2);
//            user.setRoles(roles);
//            userService.addUser(user);
//
//        }
//
////        if (userService.findUserByUsername("user") == null) {
////            User user = new User();
////            user.setUsername("user");
////            user.setPassword("user");
////
////            Set<Role> roles = new HashSet<>();
////
////            Role role = roleRepository.findByName("ROLE_USER");
////            if (role == null) {
////                role = new Role();
////                role.setName("ROLE_USER");
////            }
////
////            roles.add(role);
////            user.setRoles(roles);
////            userService.addUser(user);
////        }
//
//    }
//}
