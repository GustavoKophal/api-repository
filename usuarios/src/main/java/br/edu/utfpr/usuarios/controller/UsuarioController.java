package br.edu.utfpr.usuarios.controller;

import br.edu.utfpr.usuarios.dto.UsuarioDTO;
import br.edu.utfpr.usuarios.model.UsuarioModel;
import br.edu.utfpr.usuarios.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado")
    })
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna o usuário correspondente ao ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable Long id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Validated
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com as informações fornecidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
})
public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult bindingResult) {
    // Verifica erros de validação
    if (bindingResult.hasErrors()) {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    // Conversão de UsuarioDTO para UsuarioModel
    UsuarioModel usuario = new UsuarioModel();
    usuario.setEmail(usuarioDTO.getEmail());
    usuario.setNome(usuarioDTO.getNome());
    usuario.setSenha(usuarioDTO.getSenha());
    usuario.setCurso(usuarioDTO.getCurso());

    // Salvando o usuário no banco
    UsuarioModel savedUsuario = usuarioRepository.save(usuario);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
}


    @PutMapping("/{id}")
    @Validated
    @Operation(summary = "Atualizar usuário", description = "Atualiza as informações do usuário com o ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable Long id,
            @Valid @RequestBody UsuarioModel usuarioDetails) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setNome(usuarioDetails.getNome());
            usuario.setEmail(usuarioDetails.getEmail());
            usuario.setSenha(usuarioDetails.getSenha());
            usuario.setCurso(usuarioDetails.getCurso());
            UsuarioModel updatedUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui o usuário correspondente ao ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
