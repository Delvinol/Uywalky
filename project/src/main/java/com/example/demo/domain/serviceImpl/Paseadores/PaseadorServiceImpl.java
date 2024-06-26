package com.example.demo.domain.serviceImpl.Paseadores;

import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.PaseadorExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.CategoriaNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.UserNotFoundException;
import com.example.demo.application.service.PaseadoresServices.PaseadorService;

import com.example.demo.domain.entity.paseadores.Categorias;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Paseadores.CategoriaRepository;
import com.example.demo.domain.repository.Paseadores.PaseadorRepository;
import com.example.demo.domain.repository.Usuarios.UserRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaseadorServiceImpl implements PaseadorService {


    private final UserRepository userRepository;
    private final PaseadorRepository paseadorRepository;
    private final CategoriaRepository categoriaRepository;

    // Servicio para registrar Paseadores
    @Override
    public PaseadoresDTO registrarPaseador(PaseadoresDTO paseadoresDTO) {
        User user = userRepository.findById(paseadoresDTO.getIdUsuario())
                .orElseThrow(() -> new UserNotFoundException("Id de usuario no encontrado"));

        // Lógica para verificar si el usuario ya tiene un paseador asignado
        if (paseadorRepository.existsByUser(user)) {
            throw new PaseadorExistenteException("Este usuario ya tiene una cuenta como paseador");
        }

        Categorias categorias = categoriaRepository.findById(paseadoresDTO.getIdCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("Id de la categoría no encontrado"));

        // Implementar validaciones necesarias después
        Paseadores paseadores = Paseadores.builder()
                .calificacion(paseadoresDTO.getCalificacion())
                .descripcion(paseadoresDTO.getDescripcion())
                .experiencia(paseadoresDTO.getExperiencia())
                .ubicacion(paseadoresDTO.getUbicacion())
                .tarifa(paseadoresDTO.getTarifa())
                .saldo(paseadoresDTO.getSaldo())
                .disponibilidad(paseadoresDTO.getDisponibilidad())
                .categorias(categorias)
                .user(user)
                .build();

        // Guardando al paseador en la base de datos usando el repositorio
        paseadores = paseadorRepository.save(paseadores);
        // Retornando nada.
        return null;
    }


    // Servicio para editar paseadores
    @Override
    public PaseadoresDTO editarPaseador(Integer id_paseador, PaseadoresDTO paseadoresDTO) {
        Paseadores paseadorExistente = paseadorRepository.findById(id_paseador)
                .orElseThrow(() -> new PaseadorNotFoundException("Paseador no encontrado"));

        User user = paseadorExistente.getUser();
        if (paseadoresDTO.getIdUsuario() != null) {
            user = userRepository.findById(paseadoresDTO.getIdUsuario())
                    .orElseThrow(() -> new UserNotFoundException("Id de usuario no encontrado"));
        }
        Categorias categorias = paseadorExistente.getCategorias();
        if (paseadoresDTO.getIdCategoria() != null) {
            categorias = categoriaRepository.findById(paseadoresDTO.getIdCategoria())
                    .orElseThrow(() -> new CategoriaNotFoundException("Id de la categoría no encontrado"));
        }

        paseadorExistente.setCalificacion(paseadoresDTO.getCalificacion());
        paseadorExistente.setDescripcion(paseadoresDTO.getDescripcion());
        paseadorExistente.setExperiencia(paseadoresDTO.getExperiencia());
        paseadorExistente.setUbicacion(paseadoresDTO.getUbicacion());
        paseadorExistente.setTarifa(paseadoresDTO.getTarifa());
        paseadorExistente.setSaldo(paseadoresDTO.getSaldo());
        paseadorExistente.setDisponibilidad(paseadoresDTO.getDisponibilidad());
        paseadorExistente.setCategorias(categorias);

        paseadorExistente = paseadorRepository.save(paseadorExistente);
        // Retornando nada
        return null;
    }

}

