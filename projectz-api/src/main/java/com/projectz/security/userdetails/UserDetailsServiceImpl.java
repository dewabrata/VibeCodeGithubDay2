package com.projectz.security.userdetails;

import com.projectz.entity.MstUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Menggunakan query dengan JOIN FETCH untuk eager loading
        MstUser user = entityManager.createQuery(
                "SELECT u FROM MstUser u JOIN FETCH u.akses WHERE u.username = :username", 
                MstUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String roleName = user.getAkses().getNama();

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName))
        );
    }
}
