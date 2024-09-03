package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/customer/register-customer").permitAll()
                .requestMatchers("api/v1/customer/update-customer").hasAnyAuthority("CUSTOMER")
                .requestMatchers("api/v1/customer/delete-customer").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/customer/get-all-customers").hasAnyAuthority("EMPLOYEE","ADMIN")


                .requestMatchers("api/v1/employee/add-employee").permitAll()
                .requestMatchers("api/v1/employee/get-all-employees").hasAnyAuthority("EMPLOYEE","ADMIN")
                .requestMatchers("api/v1/employee/update-employee").hasAnyAuthority("EMPLOYEE","ADMIN")
                .requestMatchers("api/v1/employee/delete-employee").hasAnyAuthority("EMPLOYEE","ADMIN")

                .requestMatchers("api/v1/auth/getallusers").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/auth/delete").hasAnyAuthority("ADMIN","EMPLOYEE")




                .requestMatchers("api/v1/account/add-account").hasAnyAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/update-account").hasAnyAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/activate-account").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/account/block-account").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/account/delete-account").hasAnyAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/view-account-details").hasAnyAuthority("CUSTOMER","ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/account/get-user-accounts").hasAnyAuthority("CUSTOMER","ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/account/deposit/").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/withdraw").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/transfer").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/account/get-all-account").hasAnyAuthority("EMPLOYEE","ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();


    }
}
