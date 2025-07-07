package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.entity.Client;
import com.caioantonio.demo_park_api.exception.CpfUniqueViolationException;
import com.caioantonio.demo_park_api.exception.EntityNotFoundException;
import com.caioantonio.demo_park_api.repository.ClientRepository;
import com.caioantonio.demo_park_api.repository.projection.ClientProjection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public void  delete(Long id) {
        clientRepository.deleteById(id);
    }


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

    @ReadOnlyProperty
    public Client getById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente { ID = %s } não encontrado", id))
        );
        return client;
    }

    @ReadOnlyProperty
    public Page<ClientProjection> getAllClientes(Pageable pageable) {
        return clientRepository.findAllPageable(pageable);
    }

    @ReadOnlyProperty
    public Client getByUserid(Long id) {
        return clientRepository.findByUserId(id);
    }
}
