package com.example.imdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Loader implements CommandLineRunner {

    @Autowired
    private TitleService titleService;
    @Autowired
    private CrewService crewService;
    @Autowired
    private ActorService actorService;

    @Override
    public void run(String... args) {

        crewService.fetchTitlesWhichDirectorAndWriterAreSame();

//        actorService.fetchAllTitlesBothPlayed("Humphrey Bogart","Marlon Brando");

//        titleService.fetchBestTitlesEachYear("Short");

    }

}
