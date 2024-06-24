package com.example.ProyectoIntegrador.service;

import com.example.ProyectoIntegrador.dto.TurnoDTO;
import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.entity.Paciente;
import com.example.ProyectoIntegrador.entity.Turno;
import com.example.ProyectoIntegrador.repository.OdontologoRepository;
import com.example.ProyectoIntegrador.repository.PacienteRepository;
import com.example.ProyectoIntegrador.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;


    public TurnoDTO guardarTurno(Turno turno){
        return turnoAturnoDTO(turnoRepository.save(turno));
    }
    public List<TurnoDTO> listarTodos(){
        List<Turno> listaTurnos= turnoRepository.findAll();
        List<TurnoDTO> listaDTO= new ArrayList<>();
        for (Turno turno : listaTurnos) {
            listaDTO.add(turnoAturnoDTO(turno));
        }
        return listaDTO;
    }
    public Optional<TurnoDTO> buscarTurnoId(Long id){
        Optional<Turno> turnoBuscado= turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            return Optional.of(turnoAturnoDTO(turnoBuscado.get()));
        }
        return Optional.empty();
    }
    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
    public void actualizarTurno(TurnoDTO turnoDTO){
        turnoRepository.save(turnodtoAturno(turnoDTO));
    }

    public TurnoDTO turnoAturnoDTO(Turno turno){
        TurnoDTO turnoDTO= new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setFecha(turno.getFechaHora());
        turnoDTO.setPacienteId(turno.getPaciente().getId());
        turnoDTO.setOdontologoId(turno.getOdontologo().getId());
        turnoDTO.setPaciente(turno.getPaciente());
        turnoDTO.setOdontologo(turno.getOdontologo());
        return turnoDTO;
    }
    public Turno turnodtoAturno(TurnoDTO turnoDTO){
        Turno turno= new Turno();
        Odontologo odontologo= odontologoRepository.findById(turnoDTO.getOdontologoId()).get();
        Paciente paciente= pacienteRepository.findById(turnoDTO.getPacienteId()).get();
        turno.setId(turnoDTO.getId());
        turno.setFechaHora(turnoDTO.getFecha());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        return turno;
    }
}