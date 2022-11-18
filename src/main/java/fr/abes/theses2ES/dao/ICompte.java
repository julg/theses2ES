package fr.abes.theses2ES.dao;

import fr.abes.theses2ES.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompte extends JpaRepository<Compte, Integer> {

    Compte getCompteByIdDoc(Integer idDoc);
}
