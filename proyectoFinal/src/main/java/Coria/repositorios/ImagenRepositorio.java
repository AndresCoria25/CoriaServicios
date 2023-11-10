package Coria.repositorios;

import Coria.entidades.Imagen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {

    @Query("SELECT i FROM Imagen i WHERE i.imagen = :imagen")
    List<Imagen> findByNombre(@Param("imagen") String imagen ); 
    

    
}