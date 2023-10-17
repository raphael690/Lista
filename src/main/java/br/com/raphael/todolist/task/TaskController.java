package br.com.raphael.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.raphael.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;

/*
 * Implementar o método de deletar uma tarefa
 * Implementar o método de listar uma tarefa específica
 * Implementar o método de listar as tarefas por status
 * Implementar o método de listar as tarefas por prioridade
 * Implementar a data e tempo de útimo update (updateAt)
 */


@RestController
@RequestMapping("/task")
public class TaskController{

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        // 10/11/2023 - Current
        // 10/10/2023 - startAt
        
        if(currentDate.isBefore(taskModel.getStartAt()) || currentDate.isBefore(taskModel.getEndAt())){
            return ResponseEntity.status(400)
                .body("A data de início / data de término não pode ser maior do que a data atual.");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getStartAt())){
            return ResponseEntity.status(400)
                .body("A data de início dedve ser menor do que a data de término.");
        }
        
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(201).body(task);

    }
    
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    // http://localhost:8080/tasks/5454548-5545454-fsfsfs
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
        var task = this.taskRepository.findById(id).orElse(null);   
        
        if(task == null){
            return ResponseEntity.status(404).body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");
    
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(400)
            .body("O usuário não tem permissão para editar essa tarefa");
    }

        Utils.copyNonNullProperties(taskModel, task);

        var taskUpdate = this.taskRepository.save(task);
        return ResponseEntity.status(201).body(taskUpdate);
    
    }
}

