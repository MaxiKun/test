package com.mx.marvel.integration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author MBL
 */

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**Método para configurar las políticas de seguridad HTTP para la aplicación.
	 *
	 * @param http Objeto HttpSecurity que permite configurar la seguridad web basada en Spring Security. 
	 * @throws Exception Si ocurre un error durante la configuración de las políticas de seguridad. 
	 *
	 * Este método se invoca automáticamente cuando se inicia la aplicación.
	 * @autor MBL
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/**/access/**").permitAll()
			.antMatchers(HttpMethod.GET, "/**").permitAll()
			.anyRequest().authenticated();
	}
}
