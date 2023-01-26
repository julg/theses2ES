package fr.abes.theses2ES.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import oracle.jdbc.OracleResultSet;
import oracle.xdb.XMLType;
@Slf4j
public class TheseRowMapper implements RowMapper<TheseDTO> {

    @Override
    public TheseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        try {
            TheseDTO these = new TheseDTO();
            OracleResultSet rsOra = (OracleResultSet) rs;

            these.setIdDoc(rsOra.getInt("iddoc"));
            log.info("dans TheseRowMapper, l'iddoc est : " + these.getIdDoc());
            these.setNnt(rsOra.getString("nnt"));
            these.setIdSujet(rsOra.getString("numsujet"));
            these.setDoc(XMLType.createXML(rsOra.getOPAQUE("doc")).getStringVal());

            return these;

        }
        catch (NullPointerException e) {
            log.error("dans TheseRowMapper : " + e.toString());
            return null;
        }
    }

}
