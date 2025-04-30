package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.entity.User;
import com.caioantonio.demo_park_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    @ReadOnlyProperty // Marca p/ ser somente leitura para o framework de mapeamento e, portanto, não será persistido.
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
    }

    @ReadOnlyProperty
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User changeUserPassword(Long id, String newPassword) {
        User user = findById(id);
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
