package br.edu.utfpr.conteudos.repository;

import br.edu.utfpr.conteudos.model.ConteudoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<ConteudoModel, Long> {
}
