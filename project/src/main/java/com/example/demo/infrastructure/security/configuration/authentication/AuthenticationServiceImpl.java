package com.example.demo.infrastructure.security.configuration.authentication;

import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.TipoUsuarioNotFoundException;
import com.example.demo.domain.entity.usuarios.TiposUsuario;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Usuarios.TipoUsuarioRepository;
import com.example.demo.domain.repository.Usuarios.UserRepository;
import com.example.demo.infrastructure.security.configuration.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;
    private final AuthenticationManager authenticationManager;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request, Integer idTipoUsuario) {
        TiposUsuario tiposUsuario = tipoUsuarioRepository.findById(idTipoUsuario)
                .orElseThrow(() -> new TipoUsuarioNotFoundException("Tipo de usuario no encontrado"));
        var user = User.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .apodo(request.getApodo())
                .direccion(request.getDireccion())
                .edad(request.getEdad())
                .celular(request.getCelular())
                .dni(request.getDni())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .tiposUsuario(tiposUsuario)
                .build();

        repository.save(user);
        var jwtToken = projectService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        user = repository.loadTipoUsuario(user.getId());
        var jwtToken = projectService.generateToken(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        TiposUsuario tiposUsuario = user.getTiposUsuario();
        Integer tipoUsuarioId = tiposUsuario.getIdTipoUsuario();

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .IdTipoUsuario(tipoUsuarioId)
                .build();
    }
}