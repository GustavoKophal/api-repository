package br.edu.utfpr.categorias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaDTO {

    @NotNull(message = "Disciplina é obrigatório")
    @NotBlank(message = "Disciplina não pode ser vazio")
    private String disciplina;

    @NotNull(message = "Descrição é obrigatório")
    @NotBlank(message = "Descrição não pode ser vazio")
    private String descricao;

    @NotNull(message = "Curso é obrigatório")
    @NotBlank(message = "Curso não pode ser vazio")
    private String curso;
}
