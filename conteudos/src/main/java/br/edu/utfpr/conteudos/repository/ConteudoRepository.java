package br.edu.utfpr.conteudos.repository;

import br.edu.utfpr.conteudos.model.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {
}
