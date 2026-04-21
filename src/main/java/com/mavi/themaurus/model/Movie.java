package com.mavi.themaurus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private String id;
    private String title;
    private Date releaseYear;
    private String description;
    private String director;
}
