package fr.abes.theses2ES.service;



import fr.abes.theses2ES.entities.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IDocumentService {
    Page<Document> findAll(Pageable pageable);
    Document findById(Integer id);
}
