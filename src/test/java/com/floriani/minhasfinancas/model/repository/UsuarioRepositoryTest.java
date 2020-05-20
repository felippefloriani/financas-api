package com.floriani.minhasfinancas.model.repository;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.floriani.minhasfinancas.model.entity.Usuario;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
		
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenário
		
		//acao
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificacao
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void  devePersistirOusuarioNaBaseDeDados() {
		
		Usuario usuario = criarUsuario();
		Usuario usuarioSalvo = repository.save(usuario);
		
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
		
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		
		Usuario usuario = criarUsuario();
		
		entityManager.persist(usuario);
		
		//verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
		
		Assertions.assertThat( result.isPresent() ).isTrue();
		
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		
	
		//verificacao
		Optional<Usuario> result = repository.findByEmail("usuario@gmail.com");
		
		Assertions.assertThat( result.isPresent() ).isFalse();
	
	}
	
	
	public static Usuario criarUsuario() {
		return Usuario
				.builder().nome("usuario")
				.email("usuario@gmail.com")
				.senha("1234")
				.build();
	}
	
	
	
	
	
	
	
	
}
