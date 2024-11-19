package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Cliente;
import java.util.List;

public interface IClienteDao {
    void guardar(Cliente cliente);
    Cliente buscarPorId(Long id);
    void actualizar(Cliente cliente);
    void eliminar(Long id);
    List<Cliente> obtenerTodos();
}
