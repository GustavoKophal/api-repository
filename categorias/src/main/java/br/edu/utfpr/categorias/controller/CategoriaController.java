package br.edu.utfpr.categorias.controller;

import br.edu.utfpr.categorias.model.CategoriaModel;
import br.edu.utfpr.categorias.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    
    @GetMapping
    @Operation(
        summary = "Listar todas as categorias",
        description = "Retorna uma lista com todas as categorias cadastradas no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias encontradas"),
        @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada")
    })
    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll();
    }

 
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar categoria por ID",
        description = "Retorna a categoria correspondente ao ID fornecido."
    )
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
    @Operation(
        summary = "Criar nova categoria",
        description = "Cria uma nova categoria com as informações fornecidas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public CategoriaModel createCategoria(@RequestBody CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar categoria",
        description = "Atualiza as informações da categoria com o ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<CategoriaModel> updateCategoria(@PathVariable Long id, @RequestBody CategoriaModel categoriaDetails) {
        Optional<CategoriaModel> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            CategoriaModel categoria = categoriaOptional.get();
            categoria.setDisciplina(categoriaDetails.getDisciplina());
            categoria.setDescricao(categoriaDetails.getDescricao());
            categoria.setCurso(categoriaDetails.getCurso());
            CategoriaModel updatedCategoria = categoriaRepository.save(categoria);
            return ResponseEntity.ok(updatedCategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir categoria",
        description = "Exclui a categoria correspondente ao ID fornecido."
    )
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
