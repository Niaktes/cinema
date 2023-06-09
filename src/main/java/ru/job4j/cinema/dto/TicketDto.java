package ru.job4j.cinema.dto;

public class TicketDto {

    private int id;
    private String sessionStartTime;
    private String hallName;
    private String filmName;
    private int rowNumber;
    private int placeNumber;
    private int userId;

    public TicketDto() {
    }

    public TicketDto(int id, String sessionStartTime, String hallName, String filmName, int rowNumber,
                     int placeNumber, int userId) {
        this.id = id;
        this.sessionStartTime = sessionStartTime;
        this.hallName = hallName;
        this.filmName = filmName;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(String sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketDto ticketDto = (TicketDto) o;
        return id == ticketDto.id
                && rowNumber == ticketDto.rowNumber
                && placeNumber == ticketDto.placeNumber
                && userId == ticketDto.userId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 44 * result + rowNumber;
        result = 44 * result + placeNumber;
        result = 44 * result + userId;
        return result;
    }

}