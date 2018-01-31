package br.com.barbeariaclub.ws;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author pedro
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.barbeariaclub.ws.WSAtendimento.class);
        resources.add(br.com.barbeariaclub.ws.WSCliente.class);
    }
    
}
