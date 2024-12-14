package br.edu.utfpr.usuarios.repository;

import br.edu.utfpr.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
