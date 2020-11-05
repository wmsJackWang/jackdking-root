package org.jackdking.security;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.jackdking.security.model.User;
import org.jackdking.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

   private final UserRepository userRepository;

   public UserModelDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   /*
    * 根据 email来认证  或者  根据用户名username来认证
    * (non-Javadoc)
    * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
    */
   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      log.debug("Authenticating user '{}'", login);

      //验证是否是邮件登入:验证登入名称  是否是邮箱格式 ， 是则通过邮箱号来验证
      if (new EmailValidator().isValid(login, null)) {
         return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
            .map(user -> createSpringSecurityUser(login, user))
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
      }

      //如果不是邮箱格式，则使用普通的用户名来登入验证
      String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
      return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)//查询出来的结果进行map操作，即对每个结果进行处理（此处理论上只有一个）
         .map(user -> createSpringSecurityUser(lowercaseLogin, user))
         .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

   }

   private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
      if (!user.isActivated()) {
         throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
         .map(authority -> new SimpleGrantedAuthority(authority.getName()))
         .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
         user.getPassword(),
         grantedAuthorities);
   }
}
