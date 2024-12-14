package br.edu.utfpr.usuarios.service;

import br.edu.utfpr.usuarios.dto.UsuarioDTO;
import br.edu.utfpr.usuarios.model.Usuario;
import br.edu.utfpr.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setEmail(usuario.getEmail());
            dto.setNome(usuario.getNome());
            dto.setCurso(usuario.getCurso());
            return dto;
        }).collect(Collectors.toList());
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existente.setNome(usuario.getNome());
        existente.setCurso(usuario.getCurso());
        existente.setSenha(usuario.getSenha());
        return usuarioRepository.save(existente);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}