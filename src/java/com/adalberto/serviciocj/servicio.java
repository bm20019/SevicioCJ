package com.adalberto.serviciocj;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("cjapi")
public class servicio extends Application {
    
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> recursos = new HashSet();
        recursos.add(servicioCliente.class);
        recursos.add(loginService.class);
        return recursos;
    }   
}
