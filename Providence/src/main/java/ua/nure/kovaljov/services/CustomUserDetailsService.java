package ua.nure.kovaljov.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.nure.kovaljov.database.dao.UserDAO;
import ua.nure.kovaljov.exceptions.AccountException;

public class CustomUserDetailsService implements UserDetailsService {
	private UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(final String email) 
               throws UsernameNotFoundException {

		ua.nure.kovaljov.entity.dbentity.User user = userDao.getUserForAuth(email);
		if (user == null) {
			throw new AccountException("No such user!");
		}
		List<GrantedAuthority> authorities = buildUserAuthority();

		return buildUserForAuthentication(user, authorities);
		

	}

	private User buildUserForAuthentication(ua.nure.kovaljov.entity.dbentity.User user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), 
			user.getPassword(), true, 
                        true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority() {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
			setAuths.add(new SimpleGrantedAuthority("ADMIN"));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
