package br.edu.utfpr.categorias.repository;

import br.edu.utfpr.categorias.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}
