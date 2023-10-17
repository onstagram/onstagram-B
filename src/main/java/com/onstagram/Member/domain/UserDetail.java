//package com.onstagram.Member.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter@Setter
//@AllArgsConstructor
//public class UserDetail implements UserDetails {
//
//    private String email;
//    private String password;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // 권한 목록을 생성하고 반환
//        Set<GrantedAuthority> authorities = new HashSet<>();
//
//        // 여기서 "ROLE_USER"는 사용자의 권한을 나타냅니다.
//        // 애플리케이션에 따라 권한 이름이 다를 수 있습니다.
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        // 필요에 따라 다른 권한을 추가할 수 있습니다.
//        // authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
