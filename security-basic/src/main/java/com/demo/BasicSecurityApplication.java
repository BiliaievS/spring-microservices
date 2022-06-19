package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.sql.ResultSet;

@SpringBootApplication
public class BasicSecurityApplication {

    @Bean
    UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {

        RowMapper<User> userRowMapper = (ResultSet rs, int i) ->
                new User(rs.getString("ACCOUNT_NAME"),
                        rs.getString("PASSWORD"),
//                        rs.getBoolean("ENABLED"),
//                        rs.getBoolean("ENABLED"),
//                        rs.getBoolean("ENABLED"),
//                        rs.getBoolean("ENABLED"),
                        AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));

//        UserDetailsService userDetailsService = username -> jdbcTemplate.queryForObject(
//                "select * from ACCOUNT where ACCOUNT.ACCOUNT_NAME = ?", userRowMapper, username);
//        return userDetailsService;
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("sbiliaiev").password("spring").roles("USER").build());
        manager.createUser(users.username("admin").password("admin").roles("USER", "ADMIN").build());
        return manager;
    }

    public static void main(String[] args) {
        SpringApplication.run(BasicSecurityApplication.class, args);
    }
}
