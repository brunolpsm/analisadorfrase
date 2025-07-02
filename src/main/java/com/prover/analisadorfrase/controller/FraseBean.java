package com.prover.analisadorfrase.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.prover.analisadorfrase.service.AnalisadorFraseServico;

@Named
@RequestScoped
public class FraseBean {

	private String frase;
	private int quantidadePalavrasDistintas;
	private Map<String, Integer> contagemPalavras;

	@Inject
	private AnalisadorFraseServico analisadorFraseServico;

	public void analisarFrase() {
		if (frase == null || frase.trim().isEmpty()) {
			contagemPalavras = new HashMap<>();
			quantidadePalavrasDistintas = 0;
			return;
		}
		contagemPalavras = analisadorFraseServico.analisar(frase);
		quantidadePalavrasDistintas = contagemPalavras.size();
	}

	public void limpar() {
		this.frase = null;
		this.contagemPalavras = new HashMap<>();
		this.quantidadePalavrasDistintas = 0;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public int getQuantidadePalavrasDistintas() {
		return quantidadePalavrasDistintas;
	}

	public Map<String, Integer> getContagemPalavras() {
		return contagemPalavras;
	}
}