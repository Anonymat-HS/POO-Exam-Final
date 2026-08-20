package org.example.pooexamfinal.config;

import org.example.pooexamfinal.model.MovementType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.util.List;

@Configuration
public class JdbcConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(List.of(
                new MovementTypeToStringConverter(),
                new StringToMovementTypeConverter()
        ));
    }

    @WritingConverter
    static class MovementTypeToStringConverter implements Converter<MovementType, String> {
        @Override
        public String convert(MovementType source) {
            return source.name();
        }
    }

    @ReadingConverter
    static class StringToMovementTypeConverter implements Converter<String, MovementType> {
        @Override
        public MovementType convert(String source) {
            return MovementType.valueOf(source);
        }
    }
}
