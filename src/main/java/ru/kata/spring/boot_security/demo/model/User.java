package ru.kata.spring.boot_security.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@EqualsAndHashCode
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The field \"username\" cannot be empty")
    @Size(min = 4, max = 20, message = "Field \"username\" should be between 4 and 20 characters")
    @Column(name = "username", unique = true)
    private String username = "";
    @NotEmpty(message = "The field \"password\" cannot be empty")
    @Size(min = 4, max = 200, message = "Field \"password\" should be between 4 and 20 characters")
    @Column(name = "password")
    private String password = "";
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles;
    @Column(name = "firstname")
    private String firstName = "";
    @Column(name = "lastname")
    private String lastName = "";
    @Min(value = 0, message = "The age cannot be less than 0")
    @Max(value = 127, message = "The age cannot be more than 127")
    @Column(name = "age")
    private Byte age = 0;
    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email = "";
    @Transient
//    @NotEmpty
    private String rolesStr;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public String getPassword() {
        return password;
    }
}