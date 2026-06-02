package com.mylearnings.java.ltim;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Movie {
    private String title;
    private String director;
    private String genre;
    private double rating;
    private int year;

    public Movie(String title, String director, String genre, double rating, int year) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
    }

}

class MainClass {

    public static void main(String[] args) {

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Christopher Nolan", "Sci-Fi", 8.8, 2026));
        movies.add(new Movie("Interstellar", "Christopher Nolan", "Sci-Fi", 8.6, 2023));
        movies.add(new Movie("The Dark Knight", "Christopher Nolan", "Action", 9.0, 2008));
        movies.add(new Movie("Pulp Fiction", "Quentin Tarantino", "Crime", 8.9, 2026));
        movies.add(new Movie("Django Unchained", "Quentin Tarantino", "Western", 8.4, 2021));
        movies.add(new Movie("Parasite", "Bong Joon-ho", "Thriller", 8.6, 2022));
        movies.add(new Movie("The Matrix", "Lana Wachowski", "Sci-Fi", 8.7, 1999));
        movies.add(new Movie("The Grand Budapest Hotel", "Wes Anderson", "Comedy", 8.1, 2014));
        movies.add(new Movie("Whiplash", "Damien Chazelle", "Drama", 8.5, 2014));
        movies.add(new Movie("Everything Everywhere All at Once", "Daniel Kwan", "Sci-Fi", 8.1, 2022));

        /*        1. Group movies by director
                2. Find the highest-rated movie per genre
            3. List all movies released in the last 5 years with rating > 8
        4. Count how many movies each genre has*/

        System.out.println("-------1--------");
        // 1
        System.out.println(movies.stream().collect(Collectors.groupingBy(Movie::getDirector)));
        System.out.println("-------2--------");
        // 2
        System.out.println(movies.stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparingDouble(Movie::getRating)), Optional::get))));

        System.out.println("-------3--------");
        System.out.println(LocalDate.now().getYear());
        // 3
        int end = LocalDate.now().getYear(), start = LocalDate.now().getYear() - 5;
        System.out.println(movies.stream().filter(e -> start <= e.getYear() && end >= e.getYear())
                .filter(e -> e.getRating() >= 8.0d).toList());

        System.out.println("-------4--------");
        // 4
        System.out.println(movies.stream().collect(Collectors.groupingBy(Movie::getGenre, Collectors.counting())));


    }

}






