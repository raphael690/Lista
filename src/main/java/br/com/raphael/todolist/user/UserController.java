package br.com.raphael.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

//import io.micrometer.core.ipc.http.HttpSender.Response;

/**
 * Modificadores (Tipos)
 * public -  Qualquer um pode acessar esta classe
 * private - Tem uma restrinção maior, somente alguns atributos são permitidos
 * protected - Somente tem acesso quem está na mesma estrutura
 */
@RestController
@RequestMapping("/users")
public class UserController {
   
    @Autowired
    private IUserRepository userRepository;     
   
    @PostMapping("/")
     public ResponseEntity create(@RequestBody UserModel userModel){
         var user = this.userRepository.findByUsername(userModel.getUsername());

         if(user != null){
            //Mensagem de erro
            //Status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuário já existe");
         }

         var passwordHashred = BCrypt.withDefaults()
                 .hashToString(12, userModel.getPassword().toCharArray());

            userModel.setPassword(passwordHashred);

         var userCreated = this.userRepository.save(userModel);
         return ResponseEntity.status(HttpStatus.OK).body(userCreated);
     }

}

/**
     * String (texto)
     * Integer (int) numeros inteiros
     * Double (double) numeros 0.0000
     * Float (float) numero decimal 0.000
     * char (caractere) 'A', 'B'
     * Date (data) 2021-08-10
     * void - sem retorno 9vazio
     */

    