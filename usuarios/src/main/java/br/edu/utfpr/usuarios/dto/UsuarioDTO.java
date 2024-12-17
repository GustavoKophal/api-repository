package br.edu.utfpr.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 10, message = "Senha deve ter entre 6 a 10 caracteres")
    private String senha;

    @Size(max = 50, message = "Curso deve ter no máximo 50 caracteres")
    private String curso;
}

