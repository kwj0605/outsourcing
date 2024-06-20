package com.sparta.outsourcing.security;

import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + userId));

        return new UserDetailsImpl(user);
    }
}
