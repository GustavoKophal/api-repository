package br.edu.utfpr.categorias.controller;

import br.edu.utfpr.categorias.model.CategoriaModel;
import br.edu.utfpr.categorias.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    
    @GetMapping
    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll();
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaModel> getCategoriaById(@PathVariable Long id) {
        Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   
    @PostMapping
    public CategoriaModel createCategoria(@RequestBody CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    
    @PutMapping("/{id}")
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
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
