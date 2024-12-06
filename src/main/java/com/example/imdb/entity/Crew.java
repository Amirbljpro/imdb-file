package com.example.imdb.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crew {

    private String tconst;

    private List<String> directors ;

    private List<String> writers ;

}
