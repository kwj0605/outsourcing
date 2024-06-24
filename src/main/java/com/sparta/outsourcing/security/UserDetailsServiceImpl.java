package com.sparta.outsourcing.security;

import com.sparta.outsourcing.entity.User;
import com.sparta.outsourcing.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;


    @Transactional
    @Override
    public UserDetailsImpl loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + userId));

        return new UserDetailsImpl(user);
    }
}
