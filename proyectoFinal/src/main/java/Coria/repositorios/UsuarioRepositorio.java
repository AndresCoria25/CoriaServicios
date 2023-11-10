package Coria.repositorios;


import Coria.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

  
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email); 
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Optional<Usuario> findByEmail(String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    public Optional<Usuario> findByNombre(String nombre);
}
