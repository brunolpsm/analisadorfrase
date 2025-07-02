package com.prover.analisadorfrase.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class AnalisadorFraseServico {

	private final ReentrantLock lock = new ReentrantLock();

	public Map<String, Integer> analisar(String frase) {
		lock.lock();
		try {
			Map<String, Integer> contagemPalavras = new HashMap<>();
			for (String palavra : frase.trim().split("\\s+")) {
				String palavraNormalizada = normalizarPalavra(palavra);
				if (!palavraNormalizada.isEmpty()) {
					contagemPalavras.merge(palavraNormalizada, 1, Integer::sum);
				}
			}
			return contagemPalavras;
		} finally {
			lock.unlock();
		}
	}

	private String normalizarPalavra(String palavra) {
		return palavra.toLowerCase().replaceAll("[^\\p{L}]", "");
	}
}
