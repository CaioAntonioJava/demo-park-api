package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.entity.Client;
import com.caioantonio.demo_park_api.exception.CpfUniqueViolationException;
import com.caioantonio.demo_park_api.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client create(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CpfUniqueViolationException(String.format("""
                    CPF '%s' não pode ser cadastrado, já existe no sistema
                    """, client.getCpf()));
        }
    }
}
