package fr.abes.theses2ES.dao;

import fr.abes.theses2ES.entities.CompteSTEP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompteSTEP extends JpaRepository<CompteSTEP, Integer> {
    CompteSTEP getCompteByIdDoc(Integer idDoc);
}
