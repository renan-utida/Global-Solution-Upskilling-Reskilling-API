package com.fiap.globalsolution;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Suite de Testes Geral - Executa TODOS os testes do projeto
 *
 * Como executar:
 * mvn test -Dtest=SuiteDeTestesGeral
 */
@Suite
@SelectPackages({
        "com.fiap.globalsolution.model",
        "com.fiap.globalsolution.dto",
        "com.fiap.globalsolution.service",
        "com.fiap.globalsolution.controller",
        "com.fiap.globalsolution.repository",
        "com.fiap.globalsolution.security",
        "com.fiap.globalsolution.exception"
})
public class SuiteDeTestesGeral {
    // Nenhum código necessário aqui
}