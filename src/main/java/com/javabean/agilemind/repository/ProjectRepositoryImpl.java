package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Project;
import com.javabean.agilemind.domain.Requirement;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository{
    private MongoOperations mongoOperations;

    public ProjectRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Project> getProjects(ObjectId userId) {
        Query query = new Query(Criteria.where("owner").is(userId));
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
    public List<Requirement> getRequirements(ObjectId projectId) {
        Query query = new Query(Criteria.where("projectId").is(projectId));
        return mongoOperations.find(query, Requirement.class);
    }

    @Override
    public Requirement getRequirement(ObjectId requirementId) {
        return mongoOperations.findById(requirementId, Requirement.class);
    }

    @Override
    public void deleteRequirement(ObjectId requirementId) {
        Query query = new Query(Criteria.where("_id").is(requirementId));
        mongoOperations.remove(query, Requirement.class);
    }

    @Override
    public Requirement saveRequirement(Requirement requirement) {
        return mongoOperations.save(requirement);
    }
}
