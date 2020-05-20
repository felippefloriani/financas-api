package com.floriani.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.floriani.minhasfinancas.api.dto.UsuarioDTO;
import com.floriani.minhasfinancas.exceptions.ErroAutenticacao;
import com.floriani.minhasfinancas.exceptions.RegraNegocioException;
import com.floriani.minhasfinancas.model.entity.Usuario;
import com.floriani.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	
	private UsuarioService service;
	
	
	//injeção de dependência
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody UsuarioDTO dto) {
		
		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
			
	}

	@PostMapping("/autenticar")
	public ResponseEntity autenticar( @RequestBody UsuarioDTO dto ) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
		

	
	
	

}
