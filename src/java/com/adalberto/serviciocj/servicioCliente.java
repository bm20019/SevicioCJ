package com.adalberto.serviciocj;

import com.adalberto.conectarbd.Conexion.Entidad.cliente;
import com.adalberto.conectarbd.Conexion.Dao.daoCliente;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cliente")
public class servicioCliente extends daoCliente {

    private void getToken(String token) {
        String _token = token.substring(7, token.length());
        System.out.println("Token: " + token);
        String[] datos = _token.split("\\.");
        Base64.Decoder decodificar = Base64.getUrlDecoder();
        String claveJWT = this.getClaveJWT();
        Jwts.parser().setSigningKey(claveJWT).parseClaimsJws(_token);
        System.out.println("Estas autorizado");
        System.out.println("Payload: " + new String(decodificar.decode(datos[1])));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodos(@HeaderParam("Authorization") String token) {
        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            getToken(token);
            return Response.status(Response.Status.OK).entity(this.getAll()).build();
        } catch (Exception ex) {
            System.err.println("E: " + ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Deprecated
    @GET
    @Path("/filtro")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarId(@HeaderParam("Authorization") String token,
            @QueryParam("id") int id) {

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            getToken(token);
            cliente cli = getClienteforId(id);
            return Response.status(Response.Status.OK).entity(cli).build();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarIdPath(@HeaderParam("Authorization") String token,
            @PathParam("id") int id) {

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            getToken(token);
            cliente cli = getClienteforId(id);
            return Response.status(Response.Status.OK).entity(cli).build();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //Metodos de escritura
    //Crear
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarCliente(@HeaderParam("Authorization") String token,
            @FormParam("nom") String nom,
            @FormParam("ape") String ape) {

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            getToken(token);
            cliente cli = new cliente();
            cli.setNombre(nom);
            cli.setApellido(ape);
            if (crearCliente(cli) > 0) {
                return Response.status(Response.Status.CREATED).entity(cli).build();
            } else {
                return Response.status(Response.Status.GONE).build();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    //UPDATE
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarCliente(@HeaderParam("Authorization") String token,
            @PathParam("id") int id,
            @FormParam("nom") String nom,
            @FormParam("ape") String ape) {

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            getToken(token);
            cliente cli = new cliente();
            cli.setNombre(nom);
            cli.setApellido(ape);
            cli.setIdCliente(id);
            if (modificarCliente(id, cli) > 0) {
                return Response.status(Response.Status.OK).entity(cli).build();
            } else {
                return Response.status(Response.Status.GONE).build();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    //DELETE
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerCliente(@HeaderParam("Authorization") String token,
            @PathParam("id") int id) {
        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            getToken(token);
            if (eliminarCliente(id) > 0) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.GONE).build();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
