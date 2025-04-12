package Telemedcine.cwa.telemedcine.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Telemedcine.cwa.telemedcine.model.Disponibilite;
import Telemedcine.cwa.telemedcine.repositories.DisponibiliteRepository;
@Service
public class DisponibiliteService {
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    public List<Disponibilite> getDisponibilitesMedecin(Long medecinId) {
        return disponibiliteRepository.findByMedecinId(medecinId);
    }

    public Disponibilite ajouterDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }
}

