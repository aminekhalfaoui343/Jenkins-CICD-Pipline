package com.visualpathit.account.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Persistent security role assigned to application users.
 */
@Entity
@Table(name = "role")
public class Role {
    /** Persistent role identifier. */
    private Long id;
    /** Role authority name. */
    private String name;
    /** Users assigned to this role. */
    private Set<User> users;

    /**
     * Returns the persistent role identifier.
     *
     * @return role identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the persistent role identifier.
     *
     * @param newId role identifier
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * Returns the role authority name.
     *
     * @return role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the role authority name.
     *
     * @param newName role name
     */
    public void setName(final String newName) {
        this.name = newName;
    }

    /**
     * Returns the users assigned to this role.
     *
     * @return assigned users
     */
    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "roles",
            cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets the users assigned to this role.
     *
     * @param newUsers assigned users
     */
    public final void setUsers(final Set<User> newUsers) {
        this.users = newUsers;
    }
}
