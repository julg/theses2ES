package fr.abes.theses2ES.dao.impl;


import fr.abes.theses2ES.dao.ICompte;
import fr.abes.theses2ES.dao.ICompteSTEP;
import fr.abes.theses2ES.dao.IDocumentDao;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Getter
@Service
public class DaoProvider {
    @Resource
    private IDocumentDao document;

    @Resource
    private ICompte compte;

    @Resource
    private ICompteSTEP compteSTEP;
}
