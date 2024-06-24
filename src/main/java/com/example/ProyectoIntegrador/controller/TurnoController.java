package com.example.ProyectoIntegrador.controller;

import com.example.ProyectoIntegrador.dto.TurnoDTO;
import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.service.OdontologoService;
import com.example.ProyectoIntegrador.service.PacienteService;
import com.example.ProyectoIntegrador.service.TurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;
    @PostMapping
    public ResponseEntity<TurnoDTO> registrarUnTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> paciente = pacienteService.buscarPaciente(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologo(turno.getOdontologo().getId());
        if (odontologo.isPresent() && paciente.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            throw new BadRequestException("No existe paciente u odontólogo");
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoId(turno.getId());
        Optional<Paciente> paciente = pacienteService.buscarPaciente(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologo(turno.getOdontologo().getId());
        if (turnoBuscado.isPresent() && odontologo.isPresent() && paciente.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoService.actualizarTurno(turnoService.turnoAturnoDTO(turno));
            return ResponseEntity.ok("Turno actualizado con exito");
        } else {
            throw new ResourceNotFoundException("No existe el turno, paciente u odontólogo");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Optional<TurnoDTO> turno = turnoService.buscarTurnoId(id);
        if (turno.isPresent()) {
            return ResponseEntity.ok(turno.get());
        } else {
            throw new ResourceNotFoundException("No existe el id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurnoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turno = turnoService.buscarTurnoId(id);
        if (turno.isPresent()) {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Eliminado con exito");
        } else {
            throw new ResourceNotFoundException("No existe el id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodosTurnos() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }
}