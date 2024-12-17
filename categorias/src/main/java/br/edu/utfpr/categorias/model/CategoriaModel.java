package br.edu.utfpr.categorias.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class CategoriaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String disciplina;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String curso;
}
