package com.odagadevops.model; // Define o pacote desta classe

import javax.validation.constraints.NotBlank; // Importa anotações de validação
import javax.validation.constraints.Size;    // Importa anotações de validação

public class Greeting {

    private Long id; // Declara um campo para armazenar o ID da saudação

    @NotBlank(message = "Content is required") // Garante que o conteúdo não esteja em branco
    @Size(min = 3, max = 255, message = "Content must be between 3 and 255 characters") // Garante que o conteúdo tenha entre 3 e 255 caracteres
    private String content; // Declara um campo para armazenar o conteúdo da saudação

    /**
     * Construtor padrão (sem argumentos).
     * É necessário para algumas operações do Spring.
     */
    public Greeting() {
    }

    /**
     * Construtor com argumentos para criar um objeto Greeting com ID e conteúdo.
     * @param id O ID da saudação.
     * @param content O conteúdo da saudação.
     */
    public Greeting(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Obtém o ID da saudação.
     * @return O ID da saudação.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da saudação.
     * @param id O novo ID da saudação.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o conteúdo da saudação.
     * @return O conteúdo da saudação.
     */
    public String getContent() {
        return content;
    }

    /**
     * Define o conteúdo da saudação.
     * @param content O novo conteúdo da saudação.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retorna uma representação em String do objeto Greeting.
     * Útil para depuração e logs.
     * @return Uma String representando o objeto Greeting.
     */
    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}