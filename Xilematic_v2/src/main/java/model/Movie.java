package model;

import java.time.LocalDate;

import java.time.LocalDate;

public class Movie {

    private int id;
    private String movieName;
    private String trailer;
    private String image;
    private String description;
    private LocalDate releaseDate = LocalDate.now();
    private int rate;
    private boolean hot;
    private boolean status;
    private String actor;
    private String director;

    public Movie() {
    }

    public Movie(int id, String movieName, String trailer, String image, String description, LocalDate releaseDate, int rate, boolean hot, boolean status, String actor, String director) {
        this.id = id;
        this.movieName = movieName;
        this.trailer = trailer;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.hot = hot;
        this.status = status;
        this.actor = actor;
        this.director = director;
    }

    public Movie(int id, String movieName, String trailer, String image, String description, String releaseDate, int rate, boolean hot, boolean status, String actor, String director) {
        this.id = id;
        this.movieName = movieName;
        this.trailer = trailer;
        this.image = image;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.rate = rate;
        this.hot = hot;
        this.status = status;
        this.actor = actor;
        this.director = director;
    }

    public Movie(String movieName, String trailer, String image, String description, String releaseDate, int rate, boolean hot, boolean status, String actor, String director) {
        this.movieName = movieName;
        this.trailer = trailer;
        this.image = image;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.rate = rate;
        this.hot = hot;
        this.status = status;
        this.actor = actor;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", movieName=" + movieName + ", trailer=" + trailer + ", image=" + image + ", description=" + description + ", releaseDate=" + releaseDate + ", rate=" + rate + ", hot=" + hot + ", status=" + status + ", actor=" + actor + ", director=" + director + '}';
    }

}
