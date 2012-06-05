package pl.edu.agh.iosr.brokers;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class AuthenticationProviderImpl implements AuthenticationProvider {

	private UserDao dao;
	public void setUserDao(UserDao dao){
		this.dao = dao;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication0)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)authentication0;
		Object cred = authentication.getCredentials();
		if (cred == null)
			throw new BadCredentialsException("No credentials");
			
		String pass = cred.toString();
		
		User user = dao.getUser(authentication.getName());
		
		if (user == null || !user.getPassword().equals(pass))
			throw new BadCredentialsException("Bad username or password");
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		
		return new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
