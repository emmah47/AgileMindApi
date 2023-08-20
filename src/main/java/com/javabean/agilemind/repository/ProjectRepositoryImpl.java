package com.javabean.agilemind.repository;

import com.javabean.agilemind.domain.Project;
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
    public List<Project> getProjects(String login) {
        Query query = new Query(Criteria.where("owner").is(login));
        return mongoOperations.find(query, Project.class);
    }

    @Override
    public Project saveProject(Project project) {
        return mongoOperations.save(project);
    }
}
