//package com.xu.controller.service;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service("userDetailsService")
//public class MyUserDetailsService implements UserDetailsService {
////
////    @Autowired
////    private UsersMapper usersMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        QueryWrapper<Users> wrapper = new QueryWrapper<>();
////        wrapper.eq("username",username);
////        Users users = usersMapper.selectOne(wrapper);
////        if(users == null){
////             new UsernameNotFoundException("用户名不存在！");
////        }
//        List<GrantedAuthority> auths =
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");
//        return new User("xu",
//                new BCryptPasswordEncoder().encode("123"),auths);
//    }
//}
