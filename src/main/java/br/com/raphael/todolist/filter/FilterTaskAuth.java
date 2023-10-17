package br.com.raphael.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
//import com.fasterxml.jackson.databind.JsonSerializable.Base;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.raphael.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();
                System.out.println("PATH" + servletPath);
                if(servletPath.startsWith("/tasks/")) {//rotas autorizada sem problema nenhum
                   
                     // Pegar a autenticação (usuário e senha)
                var authorization = request.getHeader("Authorization");
               
                var authEncoded = authorization.substring("Basic".length()).trim(); 
                /*substring é para pegar a partir do 6º caractere 
                 * Basic e Trin tira os espaços em branco
                 */

                byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                /*decode é para decodificar o que foi codificado( no caso, o usuário e senha) */

                var authString = new String(authDecode);
                /*split é para separar o usuário e senha (no caso, o usuário é o primeiro item 
                 *e a senha é o segundo item) 
                 */
              
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];                
                
                // Validar usuário
                    var user = this.userRepository.findByUsername(username);
                    if(user == null) {
                    response.sendError(401, "Usuário sem autorização");
                    }else{
                // Validar senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (passwordVerify.verified){ 
                // Segue Viagem        
                        request.setAttribute("IdUser", user.getId());                       
                        filterChain.doFilter(request, response);
                    }else{
                        response.sendError(401);
                    }
                }

            }else {
                filterChain.doFilter(request, response);
            }
        }  

    }
