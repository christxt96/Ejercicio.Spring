package com.ejercicio.EggNews.servicios;

import com.ejercicio.EggNews.entidades.Administrador;
import com.ejercicio.EggNews.entidades.Noticia;
import com.ejercicio.EggNews.entidades.Periodista;
import com.ejercicio.EggNews.entidades.Usuario;
import com.ejercicio.EggNews.entidades.imagen;
import com.ejercicio.EggNews.enumeracion.Rol;
import com.ejercicio.EggNews.repositorios.AdminRepositorio;
import com.ejercicio.EggNews.repositorios.NoticiaRepositorio;
import com.ejercicio.EggNews.repositorios.PeriodistaRepositorio;
import com.ejercicio.EggNews.repositorios.UsuarioRepositorio;
import com.ejercicio.excepciones.Miexcepcion;

import io.micrometer.core.ipc.http.HttpSender.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenService imagenService;
@Autowired
public NoticiaRepositorio nr;
    @Autowired
    private AdminRepositorio adminRepositorio;
    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    @Transactional
    public void register(String nombre, String email, String password, String password2, MultipartFile archivo)
            throws Miexcepcion {
        validar(nombre, email, password, password2);

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRol(Rol.USER);
        imagen img = imagenService.guardar(archivo);
        user.setImagen(img);
        usuarioRepositorio.save(user);

    }

    @Transactional
    public void actualizar(String nombre, String idUsuario, String email, String password, String password2,
            MultipartFile archivo) throws Miexcepcion {
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        Usuario user = respuesta.get();
        if (respuesta.isPresent()) {
            if (user instanceof Periodista) {
                Periodista period = (Periodista) user;
                period.setNombre(nombre);
                period.setEmail(email);
                period.setPassword(new BCryptPasswordEncoder().encode(password));
                String idimagen = null;
                if (user.getImagen() != null) {
                    idimagen = user.getImagen().getId();
                }
                imagen img = imagenService.actualizar(archivo, idimagen);
                period.setImagen(img);
                periodistaRepositorio.save(period);

            } else if (user instanceof Administrador) {
                Administrador period = (Administrador) user;
                period.setNombre(nombre);
                period.setEmail(email);
                period.setPassword(new BCryptPasswordEncoder().encode(password));
                String idimagen = null;
                if (user.getImagen() != null) {
                    idimagen = user.getImagen().getId();
                }
                imagen img = imagenService.actualizar(archivo, idimagen);
                period.setImagen(img);
                adminRepositorio.save(period);

            } else {

                user.setNombre(nombre);
                user.setEmail(email);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                String idimagen = null;
                if (user.getImagen() != null) {
                    idimagen = user.getImagen().getId();
                }
                imagen img = imagenService.actualizar(archivo, idimagen);
                user.setImagen(img);
                usuarioRepositorio.save(user);

            }

        }

    }

    /**
     * @param id
     * @param rol
     */
    // @Transactional
    // public void cambiarRoles(String id, String rol) {

    // Optional<Usuario> repuesta = usuarioRepositorio.findById(id);
    // if (repuesta.isPresent()) {
    // Usuario user = repuesta.get();
    // switch (rol.toUpperCase()) {
    // case "USER":
    // user.setRol(Rol.USER);
    // user.setActivo(false);
    // break;
    // case "ADMIN":
    // Administrador admin = (Administrador) repuesta.get();
    // admin.setRol(Rol.ADMIN);
    // admin.setFechaDeAlta(new Date());
    // usuarioRepositorio.save(admin);
    // break;
    // case "PERIODISTA":
    // Periodista p = (Periodista) repuesta.get();
    // p.setRol(Rol.PERIODISTA);
    // p.setFechaDeAlta(new Date());
    // p.setActivo(true);

    // p.setSueldo(0);

    // usuarioRepositorio.save(p);

    // break;
    // }

    // }

    // }

    @Transactional
    public void cambiarRoles(String id, String rol) {
        Optional<Usuario> repuesta = usuarioRepositorio.findById(id);
        if (repuesta.isPresent()) {
            Usuario user = repuesta.get();

            // si el el objeto es Usuario y el rol que  
            if ("ADMIN".equals(rol.toUpperCase()) && user instanceof Usuario) {
                // Administrador admin = (Administrador) user;
                // admin.setRol(Rol.ADMIN);
                // admin.setFechaDeAlta(new Date());
                Administrador admin = new Administrador();
                admin.setId(user.getId());
                admin.setNombre(user.getNombre());
                admin.setEmail(user.getEmail());
                admin.setPassword(user.getPassword());
                admin.setActivo(true);
                admin.setRol(Rol.ADMIN);
                admin.setFechaDeAlta(new Date());
                admin.setImagen(user.getImagen());
                List<Periodista> periodistas = listadePeriodistas();
                admin.setListDePeriodistas(periodistas);
                usuarioRepositorio.delete(user);
                usuarioRepositorio.save(admin);
            }

            // Verificar si es un Periodista
            else if ("PERIODISTA".equals(rol.toUpperCase()) && user instanceof Usuario) {
                // Periodista p = (Periodista) user;
                // p.setRol(Rol.PERIODISTA);
                // p.setFechaDeAlta(new Date());
                // p.setActivo(true);
                // p.setSueldo(0);
                // usuarioRepositorio.delete(user);
                // periodistaRepositorio.save(p);

                Periodista periodista = new Periodista();
                periodista.setId(user.getId());
                periodista.setNombre(user.getNombre());
                periodista.setEmail(user.getEmail());
                periodista.setPassword(user.getPassword());
                periodista.setActivo(true);
                periodista.setRol(Rol.PERIODISTA);
                periodista.setFechaDeAlta(new Date());
                periodista.setSueldo(0);
                periodista.setImagen(user.getImagen());
                usuarioRepositorio.delete(user);
                periodistaRepositorio.save(periodista);

            }
            // Si no es ni Administrador ni Periodista, asumir USER
            else if (user instanceof Periodista) {
                Optional<Periodista> period = periodistaRepositorio.findById(id);
                Usuario us = new Usuario();
                us.setId(user.getId());
                us.setNombre(user.getNombre());
                us.setEmail(user.getEmail());
                us.setPassword(user.getPassword());
                us.setImagen(user.getImagen());
                us.setActivo(false);
                us.setRol(Rol.USER);
                periodistaRepositorio.delete(period.get());
                usuarioRepositorio.save(us);

            } else if (user instanceof Administrador) {
                Optional<Administrador> period = adminRepositorio.findById(id);
                Usuario us = new Usuario();
                us.setId(user.getId());
                us.setNombre(user.getNombre());
                us.setEmail(user.getEmail());
                us.setPassword(user.getPassword());
                us.setImagen(user.getImagen());
                us.setActivo(false);
                us.setRol(Rol.USER);

                adminRepositorio.delete(period.get());
                usuarioRepositorio.save(us);
            }
        }
    }

    public List<Usuario> listDeUsuarios() {

        List<Usuario> Usuarios = usuarioRepositorio.listDeUsuarios();

        return Usuarios;

    }

    public void validar(String nombre, String email, String password, String password2) throws Miexcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new Miexcepcion("el nombre de usuario no puede esta vacio o nulo");
        }
        if (email.isEmpty() || email == null) {
            throw new Miexcepcion(" El email no puede esta vacio o nulo");
        }
        if (password.isEmpty() || password.length() < 6) {
            throw new Miexcepcion("la contraseña no puede estar vacio o se menor de 6 caracteres");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepositorio.BuscarPorEmail(email);

        if (user != null) {
            List<GrantedAuthority> permissions = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+user.getRol().toString());

            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession();

            session.setAttribute("usuario", user);
            return new User(user.getEmail(), user.getPassword(), permissions);
        }

        return null;

    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }
    public List<Periodista> listadePeriodistas() {
    List<Periodista> periodistas= periodistaRepositorio.findAll();
    return periodistas;
    }

public List<Noticia> listaDeNoticias(String id) {
return nr.NoticiasDePeriodista(id);

}


}
