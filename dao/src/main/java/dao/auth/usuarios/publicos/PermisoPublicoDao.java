package dao.auth.usuarios.publicos;

import org.springframework.data.jpa.repository.JpaRepository;

import model.auth.usuarios.publicos.PermisoPublico;

public interface PermisoPublicoDao extends JpaRepository<PermisoPublico, Long> {

}
