package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.core.service.StudentService;
import com.example.demo.domain.Produto;
import com.example.demo.domain.Student;

import datadog.trace.api.Trace;

@RestController
public class ProdutoController {

    private final StudentService studentService;

    public ProdutoController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/produtos")
    @Trace(operationName = "hello.request")
    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("Notebook", "Notebook Dell", 3500.00));
        produtos.add(new Produto("Mouse", "Mouse sem fio", 50.00));
        produtos.add(new Produto("Teclado", "Teclado Mecânico", 200.00));
        return produtos;
    }

    // @GetMapping("/produtos/{id}")
    // public Produto buscarProduto(@PathVariable int id) {
    //     List<Produto> produtos = new ArrayList<>();
    //     produtos.add(new Produto("Notebook", "Notebook Dell", 3500.00));
    //     produtos.add(new Produto("Mouse", "Mouse sem fio", 50.00));
    //     produtos.add(new Produto("Teclado", "Teclado Mecânico", 200.00));
    //     return produtos.get(id);
    // }

    @PostMapping("send")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentService.send(student);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    

    @PostMapping("/produtos")
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produto;
    }

    @PutMapping("/produtos/{id}")
    public Produto atualizarProduto(@PathVariable int id, @RequestBody Produto produto) {
        return produto;
    }

    @DeleteMapping("/produtos/{id}")
    public void deletarProduto(@PathVariable int id) {
        System.out.println("Produto deletado com sucesso!");
    }

    @PostMapping(path = "/send/foo/{what}")
	public void sendFoo(@PathVariable String what) {
		//this.template.send("topic1", new Foo1(what));
	}

    //docker exec -it 0266a06e9f35 kafka-topics.sh --create --topic topic1 --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

}
