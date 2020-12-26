package ssamba.ept.sn.BankerApp.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ssamba.ept.sn.BankerApp.model.Agence;
import ssamba.ept.sn.BankerApp.repository.AgenceRepository;
import ssamba.ept.sn.BankerApp.web.exceptions.ResourceNotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Api( description="API pour les opérations CRUD sur les Agences.")
@RestController
@Slf4j
public class AgenceController {
    @Autowired
    private AgenceRepository agenceRepository;


    @ApiOperation(value = "Récupère une agence grâce à son ID à condition que celui-ci existe!")
    @GetMapping(path = "/agence/{id}")
    public Agence getAgence(@PathVariable Integer id) {
        return agenceRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Le agence",id));
    }
    @GetMapping("/agence/all")
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    @PostMapping(path = "/agence/new")
    public ResponseEntity<Agence> addAgence(@Valid @RequestBody Agence agence) {

        Agence agenceAdded =  agenceRepository.save(agence);
        if (agenceAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agenceAdded.getCode())
                .toUri();
        return ResponseEntity.created(location).body(agenceAdded);
    }

    @DeleteMapping("/agence/{id}")
    public ResponseEntity<?> deleteAgence(@PathVariable(value = "id") int id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));

        agenceRepository.delete(agence);
        return ResponseEntity.ok().build();
    }



}