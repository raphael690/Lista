package br.com.raphael.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // configura os getters e setters
@Entity(name = "tb_users")
public class UserModel {

    //criando uma chave primária (chave única que estará sendo inserida na aplicação)
    @Id
    @GeneratedValue(generator = "UUID") /*gerar um id de forma automática */
    private UUID id;

    @Column(unique = true)  /*tornar um valor unico a ser inserido na coluna*/
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt; /*Informa a data de criação */

    // getters: Buscar informação
    // setters : Inserir informação

}
