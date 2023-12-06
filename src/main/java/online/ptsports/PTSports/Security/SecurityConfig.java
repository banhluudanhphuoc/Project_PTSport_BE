    package online.ptsports.PTSports.Security;




    import online.ptsports.PTSports.Security.JWT.JwtTokenFilter;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
    public class SecurityConfig  {

        @Autowired
        UserDetailsService userDetailsService;
        @Autowired
        JwtTokenFilter jwtTokenFilter;


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        // xac thuc
        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Bean
        public AuthenticationManager
        authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }





        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()

//                    .antMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                    .antMatchers("/api/auth/**" , "/api/public/**" ,
                            "/api/admin/**").permitAll()
                    .anyRequest().permitAll()


                    .and().csrf().disable()

                    //bo session vi lam REST API ko can
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().httpBasic()
                    .disable()
                    .formLogin()
                    .disable();

            http.cors().and()
                    .csrf().disable()
                    .authorizeRequests();
            // Apply JWT
            http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }


    }

