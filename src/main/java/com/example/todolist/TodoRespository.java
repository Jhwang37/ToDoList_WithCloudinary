package com.example.todolist;

import org.springframework.data.repository.CrudRepository;

public interface TodoRespository extends CrudRepository<Todo, Long> {
}
