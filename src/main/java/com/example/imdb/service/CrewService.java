package com.example.imdb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;


@Service
@Slf4j
public class CrewService {

    public Set<String> fetchTitlesWhichDirectorAndWriterAreSame() {

        log.info("{} : fetchTitlesWhichDirectorAndWriterAreSame() ", LocalDateTime.now());

        Set<String> titles = ConcurrentHashMap.newKeySet();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                new ClassPathResource("title.crew.tsv.gz").getInputStream()), StandardCharsets.UTF_8))){
            br.lines().skip(1).parallel().forEach(line -> {

                String[] fields = line.split("\t");

                Set<String> writers = Arrays.stream(fields[2].split(","))
                        .filter(writer -> !"\\N".equals(writer))
                        .collect(Collectors.toSet());

                Arrays.stream(fields[1].split(","))
                        .filter(director -> !"\\N".equals(director))
                        .filter(writers::contains)
                        .findFirst()
                        .ifPresent(d -> {
                            titles.add(fields[0]);
                });

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return titles;
    }

}
