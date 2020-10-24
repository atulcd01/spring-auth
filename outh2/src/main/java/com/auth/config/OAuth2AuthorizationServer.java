package com.auth.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter 
{
	
	
	@Autowired
    private Environment env;

    @Value("classpath:schema.sql")
    private Resource schemaScript;

    @Value("classpath:data.sql")
    private Resource dataScript;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	//@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// This can be used to configure security of your authorization server itself 
		// i.e. which user can generate tokens , changing default realm etc.
		// Sample code below.

		// We're allowing access to the token only for clients with  'ROLE_TRUSTED_CLIENT' authority.
		// There are few more configurations and changing default realm is one of those 
		CorsFilter corsFilter = new CorsFilter(corsConfigurationSource());
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()") //hasAuthority('ROLE_TRUSTED_CLIENT')
			.allowFormAuthenticationForClients()
			.addTokenEndpointAuthenticationFilter(corsFilter);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 // Here you will specify about `ClientDetailsService` i.e.
	    // information about OAuth2 clients & where their info is located - memory , DB , LDAP etc.
	    // Sample code below 
		
		clients.jdbc(dataSource());
		
	}
	
	/*clients
			.inMemory()
			.withClient("clientapp")
			.secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("password", "authorization_code", "refresh_token")
			.authorities("READ_ONLY_CLIENT")// .authorities("ROLE_TRUSTED_CLIENT")
			.scopes("read_profile_info")
			.resourceIds("oauth2-resource")
			.redirectUris("http://localhost:8081/login")
			.accessTokenValiditySeconds(5000)
			.refreshTokenValiditySeconds(50000);
			@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    // Here you will do non-security configs for end points associated with your Authorization Server
	    // and can specify details about authentication manager, token generation etc. Sample code below 
	    endpoints
	        .authenticationManager(this.authenticationManager)
	        .tokenServices(tokenServices())
	        .tokenStore(tokenStore())
	        .accessTokenConverter(accessTokenConverter());
	}
	
	@Bean
	public TokenStore tokenStore() {
	    return new JwtTokenStore(accessTokenConverter());
	} 
	
	
	@Bean
	public TokenStore tokenStore() { 
	    return new JdbcTokenStore(dataSource()); 
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(tokenStore());
	    defaultTokenServices.setSupportRefreshToken(true);
	    return defaultTokenServices;
	}
	
	//@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    converter.setSigningKey("abcd");
	    converter.setSigningKey(PRIVATE_KEY);
	    converter.setVerifierKey(PUBLIC_KEY);
	    
	    return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(tokenStore());
	    defaultTokenServices.setSupportRefreshToken(true);
	    defaultTokenServices.setTokenEnhancer(accessTokenConverter());
	    return defaultTokenServices;
	}
	
	*/
	
	
	
	// JDBC token store configuration

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        //initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }
    
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
    
 
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//source.registerCorsConfiguration("/login", configuration);
		source.registerCorsConfiguration("/oauth/token", configuration);
		source.registerCorsConfiguration("/api/users/me", configuration);
		return source;		
	}
	
	
}
