package com.floriani.minhasfinancas.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.floriani.minhasfinancas.exceptions.RegraNegocioException;
import com.floriani.minhasfinancas.model.repository.UsuarioRepository;

/* CONCEITO:
 * Testes na Camada Service = testes unitários
 * Testes na Camada Repository = testes de integração
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Test()
	public void deveValidarEmail() {
		// cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validarEmail("email@email.com");
	}
	
	

	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@email.com"));
	}
	
	
	

}
