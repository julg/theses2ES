package fr.abes.theses2ES.dao;


import fr.abes.theses2ES.entities.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IDocumentDao extends PagingAndSortingRepository<Document, Integer> {

/*    @Query("select IDDOC ,DOC ,TEXTE ,CODEETAB ,ENVOISOLR  from " +
            "(select rownum rnum, a from document a where rownum <= 10)" +
            "where rnum > 1;")
    List<Document> getDocumentsPagination(@Param("rowMin") rowMin, @Param("rowMax") rowMax)*/

    @Query("SELECT doc FROM Document doc ORDER BY doc.idDoc")
    Page<Document> findAllDocumentWithPagination(Pageable pageable);
}
