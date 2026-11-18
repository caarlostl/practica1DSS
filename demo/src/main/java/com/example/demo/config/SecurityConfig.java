package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
	          .requestMatchers("/", "/index", "/index.html", "/login",
	                                 "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
			  .requestMatchers("/h2-console", "/h2-console/**").permitAll()
	          .requestMatchers(HttpMethod.GET, "/products").permitAll()
			  .requestMatchers(HttpMethod.GET, "/products/add", "/products/edit/**").hasRole("ADMIN")
			  .requestMatchers(HttpMethod.POST, "/products/save", "/products/delete/**").hasRole("ADMIN")
	          .requestMatchers("/cart", "/cart/**").permitAll()
	          .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
	          .anyRequest().authenticated()
	        )
	        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/products", true).permitAll())
	        .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll())
	        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
			.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {

        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("user"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
