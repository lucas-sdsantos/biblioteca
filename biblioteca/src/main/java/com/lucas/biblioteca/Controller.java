package com.lucas.biblioteca;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
public class Controller {
    private List<Livro> livros;

    public Controller() {
        livros = new LinkedList<>();
        livros.add(new Livro(100,"Aprendendo Spring-Boot","Huguinho Pato",2020));
        livros.add(new Livro(120,"Aprendendo Java","Zezinho Pato",2015));
        livros.add(new Livro(140,"Aprendendo Outra coisa","Luizinho Pato",2023));
        livros.add(new Livro(140,"Aprendendo Uma coisa nova","Huguinho Pato",2023));
    }

    @GetMapping("")
    @CrossOrigin(origins = "")
    public String mensageDeBemVindo() {
        return "Bem vindo a biblioteca central!";
    }

    @GetMapping("/livros")
    @CrossOrigin(origins = "*")
    public List<Livro> getListaLivros() {
        return livros;
    }

    @GetMapping("/titulos")
    @CrossOrigin(origins = "*")
    public List<String> getTitulos() {
        return livros.stream().map(Livro -> Livro.getTitulo()).toList();
    }

    @GetMapping("/autores")
    @CrossOrigin(origins = "*")
    public List<String> getAutores() {
        return livros.stream().map(Livro -> Livro.getAutor()).toList();
    }

    // Query String
    @GetMapping("/autor")
    @CrossOrigin(origins = "*")
    public List<Livro> getlivrosAutor(@RequestParam(value = "autor") String autor) {
        return livros.stream()
                     .filter(Livro -> Livro.getAutor().equals(autor))
                     .toList();
    }

    // Path Parameter
    @GetMapping("/autor/{autor}")
    @CrossOrigin(origins = "*")
    public List<Livro> getLivrosAutor2(@PathVariable(value = "autor") String autor) {
        return livros.stream()
                     .filter(Livro -> Livro.getAutor().equals(autor))
                     .toList();
    }

    // RequestBody
    // o json enviado deve ter ATRIBUTOS com os MESMOS NOMES
    @PostMapping("/novolivro")
    @CrossOrigin(origins = "*")
    public boolean cadastraLivro(@RequestBody final Livro livro) {
        livros.add(livro);
        return true;
    }

    // ResponseEntity
    // FIXME
    @PostMapping("/livroTitulo/{titulo}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Livro> getLivroTitulo(@PathVariable(value = "titulo") String titulo) {
        Livro resp = livros.stream()
                           .filter(livro -> livro.getTitulo().equals(titulo))
                           .findFirst()
                           .orElse(null);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(resp);
    }

    // Query String
    @GetMapping("/livrosporano")
    @CrossOrigin(origins = "*")
    public List<Livro> getlivrosPorAno(@RequestParam(value = "ano") int ano) {
        return livros.stream()
                     .filter(Livro -> Livro.getAno() == ano)
                     .toList();
    }

    // 
    @GetMapping("/desatualizados/{ano}")
    @CrossOrigin(origins = "*")
    public List<Livro> getLivrosDesatualizados(@PathVariable(value = "ano") int ano) {
        return livros.stream()
                     .filter(l -> l.getAno() < ano)
                     .toList();
    }  
    
}
