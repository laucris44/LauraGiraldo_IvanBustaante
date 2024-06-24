package com.example.ProyectoIntegrador.controller;

import com.example.ProyectoIntegrador.exception.BadRequestException;
import com.example.ProyectoIntegrador.exception.ResourceNotFoundException;
import com.example.ProyectoIntegrador.entity.Odontologo;
import com.example.ProyectoIntegrador.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorMatricula(odontologo.getMatricula());
        if(odontologoBuscado.isPresent()){
            throw new BadRequestException("La matricula ya existe, no se puede crear el odontologo");
        }
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(odontologo.getId());
        if (odontologoBuscado.isPresent()){
            odontologoService.actualizar(odontologo);
            return ResponseEntity.ok("Odontólogo actualizado con exito");
        } else {
            throw new ResourceNotFoundException("No existe el odontólogo");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologo(id);
        if (odontologo.isPresent()) {
            return ResponseEntity.ok(odontologo.get());
        } else{
            throw new ResourceNotFoundException("No existe el odontologo con el id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologoPorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = odontologoService.buscarOdontologo(id);
        if (odontologo.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado con exito");
        } else {
            throw new ResourceNotFoundException("No existe el id : " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodosOdontologos() {
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);

        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            throw new ResourceNotFoundException("no se encontro el odontologo ");
        }
    }
}
