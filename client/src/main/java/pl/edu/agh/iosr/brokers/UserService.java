package pl.edu.agh.iosr.brokers;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		if (!"user".equals(username)) throw new UsernameNotFoundException(username);
		return new UserDetails(){
			private static final long serialVersionUID = 1L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
			}

			@Override
			public String getPassword() {
				return "password";
			}

			@Override
			public String getUsername() {
				return "user";
			}

			@Override
			public boolean isAccountNonExpired() {
				return true;
			}

			@Override
			public boolean isAccountNonLocked() {
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}

			@Override
			public boolean isEnabled() {
				return true;
			}
		
		};
	}

}
