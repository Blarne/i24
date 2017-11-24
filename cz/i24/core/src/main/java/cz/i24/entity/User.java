package cz.i24.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Users")
@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId")
    private Role role;

    public User(String name, String email, String password, Role role) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    public User(Long id, String name, String email, String password, Role role) {
        this(name, email, password, role);
        setId(id);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
