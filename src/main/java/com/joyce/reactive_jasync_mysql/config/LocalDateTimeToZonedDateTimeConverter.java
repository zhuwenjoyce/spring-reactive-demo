package com.joyce.reactive_jasync_mysql.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.*;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/11
 */
@Component
public class LocalDateTimeToZonedDateTimeConverter implements Converter<LocalDateTime, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(LocalDateTime localDateTime) {

        Instant instant = localDateTime.toInstant(ZoneOffset.of("8"));
//        Instant instant = null;
        ZonedDateTime t = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return t;

    }
}
