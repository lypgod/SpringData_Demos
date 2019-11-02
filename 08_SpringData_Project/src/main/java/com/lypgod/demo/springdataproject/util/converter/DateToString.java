package com.lypgod.demo.springdataproject.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.time.LocalDateTime;

@WritingConverter
    public class DateToString implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            return source.toString() + 'Z';
        }
    }