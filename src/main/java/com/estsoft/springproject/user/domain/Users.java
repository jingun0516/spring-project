package com.estsoft.springproject.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Users() {}

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    // 인가 - 권한
        return List.of(new SimpleGrantedAuthority("user"),
                (new SimpleGrantedAuthority("ADMIN")
                ));
    }

    // 이하로는 인증

    @Override
    public String getUsername() {
        return email;
    }

    // 해당 계정이 만료가 됐는지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 해당 계정이 잠금처리됐는지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 pw 정보 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
