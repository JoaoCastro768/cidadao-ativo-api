package br.com.fiap.cidadaoativo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Bean public OpenAPI apiDocs() {
        return new OpenAPI().info(new Info().title("Cidadão Ativo — API REST")
                .description("Protótipo CitizenTech para registros privados de problemas urbanos; não é serviço oficial de denúncia.")
                .version("1.0.0"));
    }
}
