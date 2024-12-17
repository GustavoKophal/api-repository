package br.edu.utfpr.conteudos.controller;

import br.edu.utfpr.conteudos.dto.ConteudoDTO;
import br.edu.utfpr.conteudos.model.ConteudoModel;
import br.edu.utfpr.conteudos.repository.ConteudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conteudos")
public class ConteudoController {

    @Autowired
    private ConteudoRepository conteudoRepository;

    private final String USER_SERVICE_URL = "http://localhost:8081/usuarios";
    private final String CATEGORY_SERVICE_URL = "http://localhost:8082/categorias";

    @GetMapping
    @Operation(
        summary = "Listar todos os conteúdos",
        description = "Retorna uma lista com todos os conteúdos cadastrados no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdos encontrados"),
        @ApiResponse(responseCode = "404", description = "Nenhum conteúdo encontrado")
    })
    public List<ConteudoDTO> getAllConteudos() {
        return conteudoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar conteúdo por ID",
        description = "Retorna o conteúdo correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdo encontrado"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<ConteudoDTO> getConteudoById(@PathVariable Long id) {
        Optional<ConteudoModel> conteudo = conteudoRepository.findById(id);
        if (conteudo.isPresent()) {
            return ResponseEntity.ok(convertToDTO(conteudo.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Criar novo conteúdo",
        description = "Cria um novo conteúdo com as informações fornecidas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conteúdo criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
public ResponseEntity<?> createConteudo(@Valid @RequestBody ConteudoDTO conteudoDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        // Captura erros de validação
        var errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()
                ));
        return ResponseEntity.badRequest().body(errors);
    }

    // Verificar se o autor e a categoria existem
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> autorResponse = restTemplate.getForEntity(USER_SERVICE_URL + "/" + conteudoDTO.getAutorId(), Object.class);
    if (!autorResponse.getStatusCode().is2xxSuccessful()) {
        return ResponseEntity.badRequest().body("Autor inválido.");
    }

    ResponseEntity<Object> categoriaResponse = restTemplate.getForEntity(CATEGORY_SERVICE_URL + "/" + conteudoDTO.getCategoriaId(), Object.class);
    if (!categoriaResponse.getStatusCode().is2xxSuccessful()) {
        return ResponseEntity.badRequest().body("Categoria inválida.");
    }

    // Criação e salvamento do conteúdo
    ConteudoModel conteudoModel = convertToModel(conteudoDTO);
    conteudoModel.setDataCriacao(LocalDateTime.now());
    ConteudoModel savedConteudo = conteudoRepository.save(conteudoModel);

    return ResponseEntity.status(201).body(convertToDTO(savedConteudo));
}

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar conteúdo",
        description = "Atualiza as informações do conteúdo com o ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdo atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<ConteudoDTO> updateConteudo(@PathVariable Long id, @RequestBody ConteudoDTO conteudoDTO) {
        Optional<ConteudoModel> conteudoOptional = conteudoRepository.findById(id);
        if (conteudoOptional.isPresent()) {
            ConteudoModel conteudoModel = conteudoOptional.get();

            conteudoModel.setTitulo(conteudoDTO.getTitulo());
            conteudoModel.setTipo(conteudoDTO.getTipo());
            conteudoModel.setStatus(conteudoDTO.getStatus());
            conteudoModel.setTags(conteudoDTO.getTags());
            conteudoModel.setAutorId(conteudoDTO.getAutorId());
            conteudoModel.setCategoriaId(conteudoDTO.getCategoriaId());

            ConteudoModel updatedConteudo = conteudoRepository.save(conteudoModel);

            return ResponseEntity.ok(convertToDTO(updatedConteudo));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir conteúdo",
        description = "Exclui o conteúdo correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Conteúdo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<Void> deleteConteudo(@PathVariable Long id) {
        if (conteudoRepository.existsById(id)) {
            conteudoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ConteudoDTO convertToDTO(ConteudoModel conteudoModel) {
        ConteudoDTO conteudoDTO = new ConteudoDTO();
        conteudoDTO.setId(conteudoModel.getId());
        conteudoDTO.setTitulo(conteudoModel.getTitulo());
        conteudoDTO.setTipo(conteudoModel.getTipo());
        conteudoDTO.setStatus(conteudoModel.getStatus());
        conteudoDTO.setTags(conteudoModel.getTags());
        conteudoDTO.setDataCriacao(conteudoModel.getDataCriacao());
        conteudoDTO.setAutorId(conteudoModel.getAutorId());
        conteudoDTO.setCategoriaId(conteudoModel.getCategoriaId());
        return conteudoDTO;
    }

    private ConteudoModel convertToModel(ConteudoDTO conteudoDTO) {
        ConteudoModel conteudoModel = new ConteudoModel();
        conteudoModel.setId(conteudoDTO.getId());
        conteudoModel.setTitulo(conteudoDTO.getTitulo());
        conteudoModel.setTipo(conteudoDTO.getTipo());
        conteudoModel.setStatus(conteudoDTO.getStatus());
        conteudoModel.setTags(conteudoDTO.getTags());
        conteudoModel.setAutorId(conteudoDTO.getAutorId());
        conteudoModel.setCategoriaId(conteudoDTO.getCategoriaId());
        return conteudoModel;
    }
}
