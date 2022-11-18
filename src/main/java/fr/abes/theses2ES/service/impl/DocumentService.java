package fr.abes.theses2ES.service.impl;


import fr.abes.theses2ES.dao.impl.DaoProvider;
import fr.abes.theses2ES.entities.Document;
import fr.abes.theses2ES.service.IDocumentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentService implements IDocumentService {

    @Autowired
    @Getter
    private DaoProvider dao;

    @Override
    public Page<Document> findAll(Pageable pageable) {
        return getDao().getDocument().findAll(pageable);
    }

    @Override
    public Document findById(Integer id) {
        return getDao().getDocument().findById(id).orElse(null);
    }
}
