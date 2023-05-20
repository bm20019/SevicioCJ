package com.adalberto.serviciocj;

import com.adalberto.conectarbd.Conexion.Entidad.usuario;
import com.adalberto.conectarbd.Conexion.Dao.daoUsuario;
import com.adalberto.conectarbd.Conexion.configuracion;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class loginService extends configuracion {
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validar(usuario us) {
        System.out.println(us.toString());
        daoUsuario du = new daoUsuario();
        boolean status = du.validar(us.getUsername(), us.getPassword());
        if (status) {
            String secret = this.getClaveJWT();
            long tiempo = System.currentTimeMillis();
            String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, secret)
                    .setSubject("Mario Beltran")
                    .setIssuedAt(new Date(tiempo))
                    .setExpiration(new Date(tiempo+900000))
                    .claim("email","example@email.com")
                    .compact();
            
            JsonObject json = Json.createObjectBuilder().add("JWT", jwt).build();
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
