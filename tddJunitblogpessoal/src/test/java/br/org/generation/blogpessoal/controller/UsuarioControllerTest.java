package br.org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
    
	/**
	 * Antes de criar o teste, não esqueça de criar os métodos
	 * Construtores na Model Usuario e Cadastrar um
	 * usuário e senha no Banco de Dados 
	 */

	
	/*
	 * Cria um objeto do tipo TestRestTemplate para criar uma
	 * requisição http 
	 * */
    
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private Usuario usuario;
	private Usuario usuarioUpdate;

	
	@BeforeAll
	public void start() {
		usuario = new Usuario(0, "Administrador", "admin", "admin123", "admin@email.com.br", true);
       // usuario = new Usuario(0, "João da Silva", "joaos", "13465278", "joao@email.com.br", true);
	
	   /**O Usuario com id 5 somente será alterado se ele existir
		 * Verifique no Banco de Dados o Id correto
		 * */

	   usuarioUpdate = new Usuario(5L, "João da Silva Souza", "admin", "admin123", "admin@email.com.br", true);
	}

	@Test
    @DisplayName("✔ Cadastrar Usuário!")
	public void deveRealizarPostUsuario() {

		
		/*
		 * Cria um objeto do tipo HttpEntity para enviar como terceiro
		 * parâmentro do método exchange. (Enviando um objeto Usuario via body)
		 * 
		 * */
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

	}
	

	@Test
    @DisplayName("👍 Listar todos os Usuários!")
	public void deveMostrarTodosUsuarios() {
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("admin", "admin123").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
    @DisplayName("😳 Alterar Usuário!")
	public void deveRealizarPutUsuario() {

		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("admin", "admin123").exchange("/usuarios/alterar", HttpMethod.PUT, request, Usuario.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
	
}
