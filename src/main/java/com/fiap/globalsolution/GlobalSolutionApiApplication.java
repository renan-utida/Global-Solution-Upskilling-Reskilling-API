package com.fiap.globalsolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlobalSolutionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalSolutionApiApplication.class, args);

		System.out.println("\n==============================================================================");
		System.out.println("  🌍 GLOBAL SOLUTION API - O FUTURO DO TRABALHO");
		System.out.println("  📚 Plataforma de Upskilling/Reskilling para 2030+");
		System.out.println("  🎯 ODS: 4 (Educação), 8 (Trabalho Decente), 9 (Inovação), 10 (Igualdade)");
		System.out.println("==============================================================================");
		System.out.println("  📊 H2 Console:    http://localhost:8080/h2-console");
		System.out.println("      JDBC URL:     jdbc:h2:mem:globalsolution");
		System.out.println("      Username:     global-solution");
		System.out.println("      Password:     2025");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("  📚 Swagger UI:    http://localhost:8080/swagger-ui.html");
		System.out.println("  👥 Usuários Web:  http://localhost:8080/web/usuarios");
		System.out.println("  📖 Trilhas Web:   http://localhost:8080/web/trilhas");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("  🔐 Login API:     POST /auth/login");
		System.out.println("      Usuários:     admin/admin ou user/user");
		System.out.println("==============================================================================");
		System.out.println("  ✅ Aplicação iniciada com sucesso!");
		System.out.println("==============================================================================\n");
	}

}