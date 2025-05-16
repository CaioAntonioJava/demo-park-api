package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.entity.User;
import com.caioantonio.demo_park_api.exception.EntityNotFoundException;
import com.caioantonio.demo_park_api.exception.PasswordInvalidException;
import com.caioantonio.demo_park_api.exception.UsernameUniqueViolationException;
import com.caioantonio.demo_park_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User newUser) {
        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new UsernameUniqueViolationException(String.format("""
                    Username {%s} já cadastrado
                    """, newUser.getUsername()));
        }

    }

    @ReadOnlyProperty // Marca p/ ser somente leitura para o framework de mapeamento e, portanto, não será persistido.
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("""
                        Usuário { ID = %s } não encontrado.
                        """, id))
        );
    }

    @ReadOnlyProperty
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User changeUserPassword(Long id, String newPassword, String confirmPassword, String currentPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("Nova senha e confirmação de senha não conferem");
        }

        User user = findById(id);

        if (!user.getPassword().equals(currentPassword)) {
            throw new PasswordInvalidException("Senha atual não confere");
        }

        user.setPassword(newPassword);

        return userRepository.save(user);
    }


    @Transactional
    public void deleteById(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception exception) {
            throw new RuntimeException("Não é possível deletar pois há entidades relacionadas ao usuário");
        }
    }
}
