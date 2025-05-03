package com.odagadevops.controller;

import com.odagadevops.model.Greeting;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController // Indica que esta classe é um Controller REST, responsável por lidar com requisições HTTP
@RequestMapping("/greetings") // Define o caminho base para todos os endpoints deste controller (ex: /greetings)
public class GreetingController {

    private List<Greeting> greetings = new ArrayList<>(); // Simula um banco de dados em memória para armazenar as saudações
    private static Long nextId = 1L; // Simula o auto-incremento de IDs para novas saudações

    /**
     * GET /greetings
     * @Operation: Documenta a operação no Swagger
     * @ApiResponses: Documenta as possíveis respostas da API
     * Retorna a lista de todas as saudações.
     * @return ResponseEntity com a lista de Greeting e status 200 OK.
     */
    @GetMapping
    @Operation(summary = "Obtém todas as saudações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de saudações encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<Greeting>> getAllGreetings() {
        return ResponseEntity.ok(greetings); // Retorna a lista de saudações com status 200 OK
    }

    /**
     * GET /greetings/{id}
     * @PathVariable: Extrai o valor do ID da URL
     * @Operation: Documenta a operação no Swagger
     * @ApiResponses: Documenta as possíveis respostas da API
     * Retorna uma saudação específica pelo ID.
     * @param id O ID da saudação a ser buscada.
     * @return ResponseEntity com a saudação encontrada e status 200 OK,
     * ou status 404 Not Found se a saudação não existir.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtém uma saudação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saudação encontrada"),
            @ApiResponse(responseCode = "404", description = "Saudação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Greeting> getGreetingById(@PathVariable Long id) {
        Optional<Greeting> greeting = greetings.stream() // Usa streams para buscar a saudação pelo ID
                .filter(g -> g.getId().equals(id))
                .findFirst();
        if (greeting.isPresent()) {
            return ResponseEntity.ok(greeting.get()); // Retorna a saudação com status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna status 404 Not Found se não encontrar
        }
    }

    /**
     * POST /greetings
     * @Valid:  Ativa a validação do objeto Greeting (definida na classe Greeting)
     * @RequestBody: Indica que o objeto Greeting vem no corpo da requisição HTTP
     * @Operation: Documenta a operação no Swagger
     * @ApiResponses: Documenta as possíveis respostas da API
     * Cria uma nova saudação.
     * @param greeting A saudação a ser criada.
     * @return ResponseEntity com a saudação criada e status 201 Created,
     * ou status 400 Bad Request se a validação falhar.
     */
    @PostMapping
    @Operation(summary = "Cria uma nova saudação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saudação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Greeting> createGreeting(@Valid @RequestBody Greeting greeting) {
        greeting.setId(nextId++); // Simula a geração de um ID único
        greetings.add(greeting); // Adiciona a saudação à lista
        return ResponseEntity.status(HttpStatus.CREATED).body(greeting); // Retorna a saudação criada com status 201 Created
    }

    /**
     * PUT /greetings/{id}
     * @PathVariable: Extrai o valor do ID da URL
     * @Valid:  Ativa a validação do objeto updatedGreeting
     * @RequestBody: Indica que o objeto updatedGreeting vem no corpo da requisição HTTP
     * @Operation: Documenta a operação no Swagger
     * @ApiResponses: Documenta as possíveis respostas da API
     * Atualiza uma saudação existente.
     * @param id O ID da saudação a ser atualizada.
     * @param updatedGreeting A saudação com os novos dados.
     * @return ResponseEntity com a saudação atualizada e status 200 OK,
     * ou status 400 Bad Request se a validação falhar,
     * ou status 404 Not Found se a saudação não existir.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma saudação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saudação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Saudação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Greeting> updateGreeting(@PathVariable Long id, @Valid @RequestBody Greeting updatedGreeting) {
        Optional<Greeting> existingGreeting = greetings.stream() // Busca a saudação existente pelo ID
                .filter(g -> g.getId().equals(id))
                .findFirst();
        if (existingGreeting.isPresent()) {
            updatedGreeting.setId(id); // Mantém o ID original
            greetings.removeIf(g -> g.getId().equals(id)); // Remove a saudação antiga
            greetings.add(updatedGreeting); // Adiciona a saudação atualizada
            return ResponseEntity.ok(updatedGreeting); // Retorna a saudação atualizada com status 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna status 404 Not Found se não encontrar
        }
    }

    /**
     * DELETE /greetings/{id}
     * @PathVariable: Extrai o valor do ID da URL
     * @Operation: Documenta a operação no Swagger
     * @ApiResponses: Documenta as possíveis respostas da API
     * Exclui uma saudação pelo ID.
     * @param id O ID da saudação a ser excluída.
     * @return ResponseEntity com status 204 No Content se a exclusão for bem-sucedida,
     * ou status 404 Not Found se a saudação não existir.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma saudação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Saudação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Saudação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        boolean removed = greetings.removeIf(g -> g.getId().equals(id)); // Remove a saudação da lista
        if (removed) {
            return ResponseEntity.noContent().build(); // Retorna status 204 No Content se a exclusão for bem-sucedida
        } else {
            return ResponseEntity.notFound().build(); // Retorna status 404 Not Found se não encontrar
        }
    }
}