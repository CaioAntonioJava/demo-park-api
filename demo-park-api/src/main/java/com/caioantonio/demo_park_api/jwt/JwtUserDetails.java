package com.caioantonio.demo_park_api.jwt;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


// CLASE P/ ARMAZENAR AS INFORMAÇÕES DO USUÁRIO LOGADO

public class JwtUserDetails extends User {
    private com.caioantonio.demo_park_api.entity.User user;

    public JwtUserDetails(com.caioantonio.demo_park_api.entity.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }
    
    public Long getId() {
        return user.getId();
    }

    public String getRole() {
        return this.user.getRole().name();
    }
}