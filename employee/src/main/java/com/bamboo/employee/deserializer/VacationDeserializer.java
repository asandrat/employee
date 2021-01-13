package com.bamboo.employee.deserializer;

import com.bamboo.employee.model.Vacation;
import com.bamboo.employee.model.VacationStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VacationDeserializer extends StdDeserializer<Vacation> {

    public VacationDeserializer() {
        this(null);
    }

    public VacationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Vacation deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String id = node.get("uniqueId").asText();
        LocalDate localDateFrom =  LocalDate.parse(node.get("dateFrom").asText(), formatter);
        LocalDate localDateTo =  LocalDate.parse(node.get("dateTo").asText(), formatter);
        long duration = node.get("duration").asLong();
        VacationStatus vacationStatus = VacationStatus.valueOf(
                node.get("vacationStatus").asText()
        );

        return new Vacation(id, localDateFrom, localDateTo, duration, vacationStatus);
    }
}
