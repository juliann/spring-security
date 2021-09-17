package com.nadarzy.springsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Created by Julian Nadarzy on 17/09/2021 */
@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public MyUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> byUserName = userRepository.findByUserName(s);
    byUserName.orElseThrow(() -> new UsernameNotFoundException("not found" + s));
    return byUserName.map(MyUserDetails::new).get();
  }
}
