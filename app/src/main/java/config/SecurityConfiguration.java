package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().csrf().disable();;
	}

	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
	}

}
/*
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		 UserBuilder users = User.withDefaultPasswordEncoder();
		 UserDetails user = users
		   .username("user")
		   .password("password")
		   .roles("USER")
		   .build();
		 UserDetails admin = users
		   .username("admin")
		   .password("password")
		   .roles("USER","ADMIN")
		   .build();

   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
	   http.authorizeRequests() //
			   .anyRequest().authenticated() //
			   .antMatchers("/persons", "/roles","/")
			   .permitAll()
			   .and().httpBasic() //
			   .and().csrf().disable();

   }

}
*/
