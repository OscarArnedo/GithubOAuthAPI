package cat.tecnocampus.dsipract1oauth.application.dto;

import cat.tecnocampus.dsipract1oauth.application.LoginDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RepositoryDTO {
    private String id;
    private String name;
    private String full_name;
    @JsonProperty("private")
    private boolean privateRepo;
    private String git_url;
    @JsonDeserialize(using = LoginDeserializer.class)
    private String owner;

    public RepositoryDTO(String id, String name, String full_name, boolean privateRepo, String git_url, String owner) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.privateRepo = privateRepo;
        this.git_url = git_url;
        this.owner = owner;
    }

    public RepositoryDTO() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFull_name() {
        return full_name;
    }

    public boolean isPrivateRepo() {
        return privateRepo;
    }

    public String getGit_url() {
        return git_url;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", privateRepo=" + privateRepo +
                ", git_url='" + git_url + '\'' +
                ", owner='" + owner + '\'' +
                "}";
    }
}

