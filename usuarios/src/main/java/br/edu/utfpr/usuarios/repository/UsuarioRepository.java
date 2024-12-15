package br.edu.utfpr.usuarios.repository;

import br.edu.utfpr.usuarios.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
