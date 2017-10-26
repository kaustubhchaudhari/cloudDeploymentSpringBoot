

/**
 * <KAUSTUBH_CHAUDHARI>, <001218494>, <chaudhari.k@husky.neu.edu>
 * <AKSHAY_NAKHAWA>, <001665873>, <nakhawa.a@husky.neu.edu>
 * <PRACHI_SAXENA>, <001220709>, <saxena.pr@husky.neu.edu>
 * <AKHILA_KUNCHE>, <001251306>, <kunche.a@husky.neu.edu>
 **/

package com.csye6225.demo.auth;

import com.csye6225.demo.model.User;
import com.csye6225.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.persistence.EntityManagerFactory;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasicAuthEntryPoint basicAuthEntryPoint;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicAuthEntryPoint);
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    /*@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
*/
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByEmail(username);
                if (null != user) {
                    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("UserName not found for given email : " + username);
                }
            }
        };
    }



    //NEXT TWO RESPONSE SETTING
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
}
