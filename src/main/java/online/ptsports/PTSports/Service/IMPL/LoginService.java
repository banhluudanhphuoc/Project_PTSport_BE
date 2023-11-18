package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.DTO.LoginUserDTO;
import online.ptsports.PTSports.Entity.Role;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User st =
                userRepo.findByEmail(username);

        if (st == null) {
            throw new UsernameNotFoundException("not found");
        }

        List<SimpleGrantedAuthority> list =
                new ArrayList<SimpleGrantedAuthority>();

        for (Role role : st.getRoles()) {
            list.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        // tao user cua security
        // user dang nhap hien tai
        LoginUserDTO currentUser =
                new LoginUserDTO(username, st.getPassword(), list);

        currentUser.setId(st.getUserId());
        currentUser.setName(st.getName());

        //da hinh
        return currentUser;
    }
}