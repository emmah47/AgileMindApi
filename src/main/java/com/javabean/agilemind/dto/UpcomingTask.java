package com.javabean.agilemind.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UpcomingTask implements Comparable {
    private int daysUntilDue;

    private String projectName;
    private String taskContent;

    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId userStoryId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId projectId;

    // Sort by days until due, then project name, then task name. This is so that it can be easily rendered into a tree
    // structure in front end
    @Override
    public int compareTo(Object o) {
        UpcomingTask otherUpcomingTask = (UpcomingTask) o;
        if (this.daysUntilDue == otherUpcomingTask.getDaysUntilDue()) {
            int projectNameComparison = this.projectName.compareTo(otherUpcomingTask.getProjectName());
            if (projectNameComparison == 0) {
                return this.taskContent.compareTo(otherUpcomingTask.getTaskContent());
            }
            return projectNameComparison;
        }
        return this.daysUntilDue - otherUpcomingTask.getDaysUntilDue();
    }
}
