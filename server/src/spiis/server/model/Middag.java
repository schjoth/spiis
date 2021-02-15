package spiis.server.model;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Middag {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    private String title;
    private String date;
    private String time;
    private String description;
    private String place;

    protected Middag() {}

    public Middag(String title, String date, String time, String description, String place) {
        this.title = title;
        this.date=date;
        this.time = time;
        this.description = description;
        this.place = place;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
