package com.example.imdb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;


@Service
@Slf4j
public class ActorService {

    public Set<String> fetchAllTitlesBothPlayed(String actor1, String actor2) {

        log.info("{} : fetchAllTitlesBothPlayed() ", LocalDateTime.now());

        Set<String> tiles1 = new HashSet<>();
        Set<String> tiles2 = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                new ClassPathResource("name.basics.tsv.gz").getInputStream()), StandardCharsets.UTF_8))){
            br.lines().skip(1).parallel().forEach(line -> {

                String[] fields = line.split("\t");

                if (actor1.equals(fields[1]))
                    if (Arrays.stream(fields[4].split(",")).
                            anyMatch(role -> role.equals("actor") || role.equals("actress")))
                        tiles1.addAll(Arrays.stream(fields[5].split(",")).collect(Collectors.toSet()));

                if (actor2.equals(fields[1]))
                    if (Arrays.stream(fields[4].split(",")).
                            anyMatch(role -> role.equals("actor") || role.equals("actress")))
                        tiles2.addAll(Arrays.stream(fields[5].split(",")).collect(Collectors.toSet()));

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> titles = tiles1.stream().
                filter(tiles2::contains).
                collect(Collectors.toSet());

        return titles;
    }

}
