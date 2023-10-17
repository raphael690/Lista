package br.com.raphael.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.hibernate.mapping.List;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID>{
     List<TaskModel>findByIdUser(UUID idUser);
            
    
}
