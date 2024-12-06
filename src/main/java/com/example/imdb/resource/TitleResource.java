package com.example.imdb.resource;


import com.example.imdb.service.ActorService;
import com.example.imdb.service.CrewService;
import com.example.imdb.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/title")
public class TitleResource {

    private final ActorService actorService;
    private final CrewService crewService;
    private final TitleService titleService;

    @GetMapping(path = "/seme-Director-Writer")
    public ResponseEntity<?> fetchTitlesWhichDirectorAndWriterAreSame(){
        return ResponseEntity.ok(crewService.fetchTitlesWhichDirectorAndWriterAreSame());
    }

    @GetMapping(path = "/same-actor-played")
    public ResponseEntity<?> fetchAllTitlesBothPlayed(
            @RequestParam(value = "actor1") String actor1, @RequestParam(value = "actor2") String actor2){
        return ResponseEntity.ok(actorService.fetchAllTitlesBothPlayed(actor1,actor2));
    }

    @GetMapping(path = "/best-in-year")
    public ResponseEntity<?> fetchBestTitlesEachYear(
            @RequestParam(value = "genre") String genre){
        return ResponseEntity.ok(titleService.fetchBestTitlesEachYear(genre));
    }

}
