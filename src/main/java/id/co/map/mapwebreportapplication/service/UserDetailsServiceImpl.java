package id.co.map.mapwebreportapplication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import id.co.map.mapwebreportapplication.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    private final UserService usrServ;
    private final RoleService roleServ;

    public UserDetailsServiceImpl(UserService usrServ, RoleService roleServ) {
        this.usrServ = usrServ;
        this.roleServ = roleServ;
    }

    public UsernamePasswordAuthenticationToken loadUserByUname(String userName) throws UsernameNotFoundException {
        User appUser = usrServ.findUserAccount(userName);

        if(appUser == null){
            throw new UsernameNotFoundException("User "+userName+" does not have Role in System !! ");
        }

        List<String> roleNames = roleServ.getRoleNames(userName);
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        if(roleNames != null){
            for(String role:roleNames){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                grantList.add(grantedAuthority);
                logger.info("Role "+role);
            }
        }

        UserDetails userDet = (UserDetails) new org.springframework.security.core.userdetails.User(appUser.getUserName(), "Password01", grantList);
        UsernamePasswordAuthenticationToken userDetails = new UsernamePasswordAuthenticationToken(userDet, "", grantList);

        return userDetails;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
