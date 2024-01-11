package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.ProjectStatus;
import com.javabean.agilemind.domain.Requirement;
import com.javabean.agilemind.domain.Task;
import com.javabean.agilemind.dto.ProjectCounts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository{
    private MongoOperations mongoOperations;

    public ProjectRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Project> getProjects(ObjectId userId) {
        Query query = new Query(where("ownerId").is(userId));
        return mongoOperations.find(query, Project.class);
    }

    @Override
    public Project getProject(ObjectId projectId) {
        return mongoOperations.findById(projectId, Project.class);
    }

    @Override
    public Project saveProject(Project project) {
        return mongoOperations.save(project);
    }

    @Override
    public ProjectCounts getProjectCounts(ObjectId userId) {
        TypedAggregation<Project> aggregation = newAggregation(Project.class,
                match(where("ownerId").is(userId)),
                group("status").count().as("count"),
                group()
                  .sum("count").as("total")
                        .sum(
                                ConditionalOperators
                                        .when(ComparisonOperators.valueOf("_id").equalToValue("COMPLETED"))
                                        .thenValueOf("count")
                                        .otherwise(0))
                                .as("inProgress")
                        .sum(
                                ConditionalOperators
                                        .when(ComparisonOperators.valueOf("_id").equalToValue("IN_PROGRESS"))
                                        .thenValueOf("count")
                                        .otherwise(0))
                                .as("completed")
        );

        AggregationResults<ProjectCounts> results = mongoOperations.aggregate(aggregation, "project",
                ProjectCounts.class);

        return results.getMappedResults().get(0);
    }

    @Override
    public List<Requirement> getRequirements(ObjectId projectId) {
        Query query = new Query(where("projectId").is(projectId));
        return mongoOperations.find(query, Requirement.class);
    }

    @Override
    public Requirement getRequirement(ObjectId requirementId) {
        return mongoOperations.findById(requirementId, Requirement.class);
    }

    @Override
    public void deleteRequirement(ObjectId requirementId) {
        Query query = new Query(where("_id").is(requirementId));
        mongoOperations.remove(query, Requirement.class);
    }

    @Override
    public Requirement saveRequirement(Requirement requirement) {
        return mongoOperations.save(requirement);
    }
}
