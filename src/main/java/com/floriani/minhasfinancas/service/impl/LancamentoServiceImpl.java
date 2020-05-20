package com.floriani.minhasfinancas.service.impl;



import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.floriani.minhasfinancas.exceptions.RegraNegocioException;
import com.floriani.minhasfinancas.model.entity.Lancamento;
import com.floriani.minhasfinancas.model.enums.StatusLancamento;
import com.floriani.minhasfinancas.model.repository.LancamentoRepository;
import com.floriani.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl  implements LancamentoService{

	
	private LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		
		Objects.requireNonNull(lancamento.getId());
		
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		
		Objects.requireNonNull(lancamento.getId());
		
		repository.delete(lancamento);
		
	}

	@Override //Example é uma biblioteca do framework Spring
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of( lancamentoFiltro, 
				ExampleMatcher.matching()
					.withIgnoreCase()
					.withStringMatcher(StringMatcher.CONTAINING) );
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}

	@Override
	public void validar(Lancamento lancamento) {
		
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição válida.");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um Mês válido.");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Informe um Ano válido.");
		}
		
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um Usuário.");
		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1 ) {
			throw new RegraNegocioException("Informe um Valor válido.");
		}
		
		if(lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um tipo de Lançamento.");
		}
		
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal obterSaldoPorUsuario(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}