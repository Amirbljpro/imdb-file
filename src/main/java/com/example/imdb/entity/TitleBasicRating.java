package com.example.imdb.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TitleBasicRating {

    private String tconst;

    private String primaryTitle;

    private float averageRating ;

    private int numVotes ;

}
