package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "Имя может содержать только буквы и дефисы")
    private String name;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "Фамилия может содержать только буквы и дефисы")
    private String lastName;

    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 1, message = "Возраст должен быть положительным")
    private int age;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 4, message = "Пароль должен быть не менее 4 символов")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Transient
    public Set<Long> getRoleIds() {
        if (this.roles == null) {
            return Collections.emptySet();
        }
        return this.roles.stream()
                .map(role -> role.getId())
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
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
}