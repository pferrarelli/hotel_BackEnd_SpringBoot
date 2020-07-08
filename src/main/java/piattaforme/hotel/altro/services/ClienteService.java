package piattaforme.hotel.altro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import piattaforme.hotel.altro.entities.Cliente;
import piattaforme.hotel.altro.repositories.ClienteRepository;
import piattaforme.hotel.altro.support.exceptions.ClienteAlredyExistsException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void addCliente(Cliente cliente) throws ClienteAlredyExistsException {
        if(cliente.getEmail()!= null && clienteRepository.existsByEmail(cliente.getEmail())){
            throw new ClienteAlredyExistsException();
        }
        clienteRepository.save(cliente);
    }

    public boolean clienteExists(String email){
        return clienteRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Cliente> getAllClienti(){
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente getClienteByEmail(String email){
        return clienteRepository.findByEmail(email);
    }

}
