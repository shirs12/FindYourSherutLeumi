package com.example.findyoursherutleumi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is level user id model
 */
public class LevelUserId {

    @SerializedName("level_id")
    @Expose
    private Integer levelId;
    @SerializedName("level_name")
    @Expose
    private String levelName;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public String toString() {
        return "LevelUserId{" +
                "levelId=" + levelId +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}
