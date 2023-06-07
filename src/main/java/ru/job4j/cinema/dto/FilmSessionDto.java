package ru.job4j.cinema.dto;

import java.util.Objects;

public class FilmSessionDto {

    private int id;
    private int filmId;
    private int hallId;
    private String startTime;
    private String endTime;
    private int price;

    public FilmSessionDto() {
    }

    public FilmSessionDto(int id, int filmId, int hallId, String startTime, String endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) o;
        return id == that.id
                && filmId == that.filmId
                && hallId == that.hallId
                && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 35 * result + filmId;
        result = 35 * result + hallId;
        result = 35 * result + startTime.hashCode();
        return result;
    }

}