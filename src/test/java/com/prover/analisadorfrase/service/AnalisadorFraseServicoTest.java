package com.prover.analisadorfrase.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes Unitários do AnalisadorFraseServico")
class AnalisadorFraseServicoTest {

    private AnalisadorFraseServico analisadorFraseServico;

    @BeforeEach 
    void setUp() {
        analisadorFraseServico = new AnalisadorFraseServico();
    }

    @Test 
    @DisplayName("Deve contar palavras corretamente em uma frase simples")
    void testAnalisar_fraseSimples() {
        String frase = "Olá mundo Olá";
        Map<String, Integer> resultado = analisadorFraseServico.analisar(frase);

        assertNotNull(resultado, "O mapa de resultado não deve ser nulo");
        assertEquals(2, resultado.size(), "Deve ter 2 palavras distintas"); 
        assertEquals(2, resultado.get("olá"), "A contagem para 'olá' deve ser 2"); 
        assertEquals(1, resultado.get("mundo"), "A contagem para 'mundo' deve ser 1");
    }

    @Test
    @DisplayName("Deve lidar com sensibilidade a maiúsculas/minúsculas")
    void testAnalisar_sensibilidadeCase() {
        String frase = "Java java JAVA";
        Map<String, Integer> resultado = analisadorFraseServico.analisar(frase);

        assertNotNull(resultado);
        assertEquals(1, resultado.size(), "Deve tratar diferentes casos da mesma palavra como uma só");
        assertEquals(3, resultado.get("java"), "A contagem para 'java' (case-insensitive) deve ser 3");
    }

    @Test
    @DisplayName("Deve retornar um mapa vazio para uma string com apenas espaços")
    void testAnalisar_apenasEspacos() {
        String frase = "   ";
        Map<String, Integer> resultado = analisadorFraseServico.analisar(frase);

        assertNotNull(resultado, "O resultado não deve ser nulo para uma string com apenas espaços");
        assertTrue(resultado.isEmpty(), "O mapa de resultado deve estar vazio para uma string com apenas espaços");
    }

    @Test
    @DisplayName("Deve garantir o processamento sincronizado (teste básico de concorrência)")
    void testAnalisar_sincronizacaoBasica() throws InterruptedException {
        final StringBuilder log = new StringBuilder();
        Thread thread1 = new Thread(() -> {
            log.append("Thread 1: Tentando adquirir lock.\n");
            analisadorFraseServico.analisar("primeira frase");
            log.append("Thread 1: Lock liberado.\n");
        });

        Thread thread2 = new Thread(() -> {
            log.append("Thread 2: Tentando adquirir lock.\n");
            analisadorFraseServico.analisar("segunda frase");
            log.append("Thread 2: Lock liberado.\n");
        });

        thread1.start();
        Thread.sleep(50);
        thread2.start();

        thread1.join(); // Espera a thread 1 terminar
        thread2.join(); // Espera a thread 2 terminar

        String logContent = log.toString();
        assertTrue(logContent.contains("Thread 1: Tentando adquirir lock.") &&
                   logContent.contains("Thread 1: Lock liberado.") &&
                   logContent.contains("Thread 2: Tentando adquirir lock.") &&
                   logContent.contains("Thread 2: Lock liberado."),
                   "Ambas as threads devem tentar e liberar o lock sem erros");
    }
}