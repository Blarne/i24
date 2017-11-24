package cz.i24.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity(name = "Roles")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String label;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="RolesPrivileges",
            joinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegeId", referencedColumnName = "id")
    )
    private Collection<Privilege> privileges;

    public Role(){}
    public Role(Long id, String label) {
        this.id = id;
        this.label = label;
    }

}
