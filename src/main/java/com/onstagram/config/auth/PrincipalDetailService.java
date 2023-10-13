package com.onstagram.config.auth;

import com.onstagram.user.entity.UserEntity;
import com.onstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PrincipalDetailService implements UserDetailsService {
private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByUsername(username);
//
//        if(userEntity == null) {
//            return null;
//        }else {
//            return new PrincipalDetails(userEntity);
//        }    }
        return null;
    }
}
