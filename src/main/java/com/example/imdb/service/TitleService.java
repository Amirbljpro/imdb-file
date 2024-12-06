package com.example.imdb.service;


import com.example.imdb.entity.TitleBasicRating;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
public class TitleService {

    public Object fetchBestTitlesEachYear(String genre) {

        log.info("{} : fetchBestTitlesEachYear() ", LocalDateTime.now());

        Map<String, List<TitleBasicRating>> titleBasicRatingMap = new ConcurrentHashMap<>();

        findTitlesOnEachYear(titleBasicRatingMap, genre);

        setNumberOfVotesAndRatingForEachTitle(titleBasicRatingMap);

        holdHigherVote(titleBasicRatingMap);

        return titleBasicRatingMap;
    }

    private void findTitlesOnEachYear(Map<String, List<TitleBasicRating>> titleBasicRatingMap, String genre) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                new ClassPathResource("title.basics.tsv.gz").getInputStream()), StandardCharsets.UTF_8))) {
            br.lines().skip(1).parallel().forEach(line -> {

                String[] fields = line.split("\t");

                if (Arrays.asList(fields[8].split(",")).contains(genre))
                    if (titleBasicRatingMap.get(fields[5]) != null) {
                        titleBasicRatingMap.get(fields[5]).add(TitleBasicRating.builder().tconst(fields[0]).primaryTitle(fields[2]).build());

                    } else {
                        ArrayList<TitleBasicRating> arrayList = new ArrayList<>();
                        arrayList.add(TitleBasicRating.builder().tconst(fields[0]).primaryTitle(fields[2]).build());
                        titleBasicRatingMap.put(fields[5], arrayList);
                    }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setNumberOfVotesAndRatingForEachTitle(Map<String, List<TitleBasicRating>> titleBasicRatingMap) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                new ClassPathResource("title.ratings.tsv.gz").getInputStream()), StandardCharsets.UTF_8))) {
            br.lines().skip(1).parallel().forEach(line -> {

                String[] fields = line.split("\t");

                titleBasicRatingMap.forEach((key, value) -> value.stream().
                        filter(titleBasicRating -> titleBasicRating.getTconst().equals(fields[0])).
                        findFirst().
                        ifPresent(titleBasicRating -> {
                            titleBasicRating.setAverageRating(Float.parseFloat(fields[1]));
                            titleBasicRating.setNumVotes(Integer.parseInt(fields[2]));
                        }));

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void holdHigherVote(Map<String, List<TitleBasicRating>> titleBasicRatingMap) {
        titleBasicRatingMap.entrySet().forEach(entry ->
                entry.getValue().stream().max(Comparator.comparingInt(TitleBasicRating::getNumVotes)).
                        ifPresent(titleBasicRating -> entry.setValue(List.of(titleBasicRating))));
    }

}
