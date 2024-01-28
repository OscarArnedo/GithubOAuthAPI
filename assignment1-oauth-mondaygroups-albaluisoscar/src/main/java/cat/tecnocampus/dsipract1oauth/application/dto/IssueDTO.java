package cat.tecnocampus.dsipract1oauth.application.dto;


import cat.tecnocampus.dsipract1oauth.application.LabelsDeserializer;
import cat.tecnocampus.dsipract1oauth.application.LoginDeserializer;
import cat.tecnocampus.dsipract1oauth.application.RepositoryDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDTO {
    private String id;
    private String title;
    private String body;
    private String state;
    @JsonDeserialize(using = LoginDeserializer.class)
    private String user;
    @JsonDeserialize(using = RepositoryDeserializer.class)
    private String repository;
    @JsonDeserialize(using = LabelsDeserializer.class)
    private List<String> labels;
    @JsonDeserialize(using = LoginDeserializer.class)
    private List<String> assignees;

    public IssueDTO(String id, String title, String body, String state, String userLogin, String repository, List<String> labels, List<String> assignees) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.state = state;
        this.user = userLogin;
        this.repository = repository;
        this.labels = labels;
        this.assignees = assignees;
    }
    public IssueDTO() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }

    public String getUser() {
        return user;
    }

    public String getRepository() {
        return repository;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<String> getAssignees() {
        return assignees;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", state='" + state + '\'' +
                ", user='" + user + '\'' +
                ", repository='" + repository + '\'' +
                ", labels=" + labels +
                ", assignees=" + assignees +
                '}';
    }
}
