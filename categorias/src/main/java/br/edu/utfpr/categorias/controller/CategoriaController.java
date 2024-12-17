package br.edu.utfpr.categorias.controller;

import br.edu.utfpr.categorias.dto.CategoriaDTO;
import br.edu.utfpr.categorias.model.CategoriaModel;
import br.edu.utfpr.categorias.repository.CategoriaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada")
    })
    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna a categoria correspondente ao ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaModel> getCategoriaById(@PathVariable Long id) {
        Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria com as informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<?> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO, BindingResult bindingResult) {
        // Verifica erros de validação
        if (bindingResult.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors); // Retorna erro 400 com as mensagens
        }
    
        // Criação do modelo
        CategoriaModel categoria = new CategoriaModel();
        categoria.setDisciplina(categoriaDTO.getDisciplina());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setCurso(categoriaDTO.getCurso());
    
        // Salva no banco
        CategoriaModel savedCategoria = categoriaRepository.save(categoria);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoria);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza as informações da categoria com o ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<?> updateCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO, BindingResult result) {
    if (result.hasErrors()) {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    Optional<CategoriaModel> categoriaOptional = categoriaRepository.findById(id);
    if (categoriaOptional.isPresent()) {
        CategoriaModel categoria = categoriaOptional.get();
        categoria.setDisciplina(categoriaDTO.getDisciplina());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setCurso(categoriaDTO.getCurso());
        CategoriaModel updatedCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.ok(updatedCategoria);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria", description = "Exclui a categoria correspondente ao ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
