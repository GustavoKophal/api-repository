package br.edu.utfpr.conteudos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConteudoDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "O tipo é obrigatório.")
    private String tipo;

    @NotBlank(message = "O status é obrigatório.")
    private String status;

    private String tags;

    private LocalDateTime dataCriacao;

    @NotNull(message = "O ID do autor é obrigatório.")
    private Long autorId;

    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long categoriaId;
}