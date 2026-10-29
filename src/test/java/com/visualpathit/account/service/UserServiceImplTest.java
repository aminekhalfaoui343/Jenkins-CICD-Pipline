package com.visualpathit.account.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.visualpathit.account.model.Role;
import com.visualpathit.account.model.User;
import com.visualpathit.account.repository.RoleRepository;
import com.visualpathit.account.repository.UserRepository;

public class UserServiceImplTest {
    @Test
    public void encodesPasswordAndSavesCurrentRoles() {
        UserRepository users = mock(UserRepository.class);
        RoleRepository roles = mock(RoleRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        User user = new User();
        user.setPassword("raw-password");
        List<Role> assignedRoles = List.of(new Role());
        when(roles.findAll()).thenReturn(assignedRoles);
        when(encoder.encode("raw-password")).thenReturn("encoded-password");

        new UserServiceImpl(users, roles, encoder).save(user);

        assertEquals("encoded-password", user.getPassword());
        assertEquals(assignedRoles, user.getRoles().stream().toList());
        verify(users).save(user);
    }

    @Test
    public void savesWithoutEncodingBlankPassword() {
        UserRepository users = mock(UserRepository.class);
        RoleRepository roles = mock(RoleRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        User user = new User();
        user.setPassword(" ");
        when(roles.findAll()).thenReturn(List.of());

        new UserServiceImpl(users, roles, encoder).save(user);

        assertEquals(" ", user.getPassword());
        verify(encoder, never()).encode(" ");
        verify(users).save(user);
    }

    @Test
    public void delegatesUserLookupsToRepositories() {
        UserRepository users = mock(UserRepository.class);
        RoleRepository roles = mock(RoleRepository.class);
        BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
        User user = new User();
        when(users.findByUsername("sample")).thenReturn(user);
        when(users.findAll()).thenReturn(List.of(user));
        when(users.findById(7L)).thenReturn(Optional.of(user));

        UserServiceImpl service = new UserServiceImpl(users, roles, encoder);

        assertSame(user, service.findByUsername("sample"));
        assertSame(user, service.getList().get(0));
        assertSame(user, service.findById(7L));
        when(users.findById(8L)).thenReturn(Optional.empty());
        assertNull(service.findById(8L));
    }
}
