package com.growCode.growCode.entity;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dashboards")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message="Title is requied")
    private String title;

    private String logo;

    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme", referencedColumnName = "id")
    private Theme theme;

    private String font;

    private ArrayList<String> activities; 

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Dashboard() {
    }

    public Dashboard(long id, @NotBlank(message = "Title is requied") String title, String logo, String cover,
            Theme theme, String font, ArrayList<String> activities, User user) {
        this.id = id;
        this.title = title;
        this.logo = logo;
        this.cover = cover;
        this.theme = theme;
        this.font = font;
        this.activities = activities;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Dashboard [activities=" + activities + ", cover=" + cover + ", font=" + font + ", id=" + id + ", logo="
                + logo + ", theme=" + theme + ", title=" + title + ", user=" + user + "]";
    }

}
