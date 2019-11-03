package fr.abes.theses2ES.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import oracle.jdbc.OracleResultSet;
import oracle.xdb.XMLType;

public class TheseRowMapper implements RowMapper<TheseDTO> {

    @Override
    public TheseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        TheseDTO these = new TheseDTO();
        OracleResultSet rsOra = (OracleResultSet) rs;

        these.setIdDoc(rsOra.getInt("iddoc"));
        these.setNnt(rsOra.getString("nnt"));
        these.setDoc(XMLType.createXML(rsOra.getOPAQUE("doc")).getStringVal());

        return these;
    }

}
