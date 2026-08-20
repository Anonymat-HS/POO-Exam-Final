package org.example.pooexamfinal.config;



import org.example.pooexamfinal.model.MovementType;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.sql.SQLException;
import java.util.List;

@Configuration
public class JdbcConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(List.of(
                new MovementTypeToPGobjectConverter(),
                new PGobjectToMovementTypeConverter()
        ));
    }

    @WritingConverter
    static class MovementTypeToPGobjectConverter implements Converter<MovementType, PGobject> {
        @Override
        public PGobject convert(MovementType source) {
            PGobject pgObject = new PGobject();
            pgObject.setType("movement_type");
            try {
                pgObject.setValue(source.name());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return pgObject;
        }
    }

    @ReadingConverter
    static class PGobjectToMovementTypeConverter implements Converter<PGobject, MovementType> {
        @Override
        public MovementType convert(PGobject source) {
            return MovementType.valueOf(source.getValue());
        }
    }
}
