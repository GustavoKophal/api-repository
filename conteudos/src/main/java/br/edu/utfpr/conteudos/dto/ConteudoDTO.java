package br.edu.utfpr.conteudos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConteudoDTO {
    private Long id;
    private String titulo;
    private String tipo;
    private String status;
    private String tags;
    private LocalDateTime dataCriacao;
    private Long autorId;
    private Long categoriaId;
}