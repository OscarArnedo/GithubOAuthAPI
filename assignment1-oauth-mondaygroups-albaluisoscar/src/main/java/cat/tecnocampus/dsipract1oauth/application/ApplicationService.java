package cat.tecnocampus.dsipract1oauth.application;

import cat.tecnocampus.dsipract1oauth.application.dto.IssueDTO;
import cat.tecnocampus.dsipract1oauth.application.dto.RepositoryDTO;
import cat.tecnocampus.dsipract1oauth.configuration.GithubOauth2Config;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ApplicationService {
    private String accessToken;
    private WebClient webClient;
    private GithubOauth2Config githubOauth2Config;
    private IssueDTO issue;

    private String request;

    public ApplicationService(WebClient webClient, GithubOauth2Config githubOauth2Config) {
        this.webClient = webClient;
        this.githubOauth2Config = githubOauth2Config;
    }

    public ResponseEntity<String> getToken(String code){
        ResponseEntity<String> response;
        response = webClient.post()
                .uri(githubOauth2Config.getTokenUri()
                        +"?client_id="+githubOauth2Config.getClientId()
                        +"&client_secret="+githubOauth2Config.getClientSecret()
                        +"&code="+code)
                .retrieve()
                .toEntity(String.class)
                .block();
        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            this.accessToken = extractAccessToken(response.getBody().toString());
        }

        switch (request){
            case "repos":
                return webClient.get()
                        .uri("http://localhost:8080/getRepos")
                        .retrieve()
                        .toEntity(String.class)
                        .block();
            case "issues":
                return webClient.get()
                        .uri("http://localhost:8080/getIssues")
                        .retrieve()
                        .toEntity(String.class)
                        .block();
            case "createIssue":
                return webClient.post()
                        .uri("http://localhost:8080/createIssue")
                        .body(issue, IssueDTO.class)
                        .retrieve()
                        .toEntity(String.class)
                        .block();
            default:
                return null;
        }
    }

    public ResponseEntity<String> getRepos(){
        this.request = "repos";
        if (accessToken == null) {
            //Pedimos permiso al usuario para acceder a sus repositorios
            return getAuthorization();
        } else {
            return ResponseEntity.ok(getRepositories().toString());
        }
    }

    public ResponseEntity<String> getIssues(){
        this.request = "issues";
        if (accessToken == null) {
            return getAuthorization();
        } else {
            return ResponseEntity.ok(getIssuesList().toString());
        }
    }

    private List<RepositoryDTO> getRepositories(){
        ResponseEntity<List<RepositoryDTO>> response;
        response = webClient.get()
                .uri("https://api.github.com/user/repos")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntityList(RepositoryDTO.class)
                .block();

        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    private List<IssueDTO> getIssuesList(){
        ResponseEntity<List<IssueDTO>> response;
        response = webClient.get()
                .uri("https://api.github.com/repos/OscarArnedo/Patron-Observador/issues")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntityList(IssueDTO.class)
                .block();

        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    private String extractAccessToken(String responseBody) {
        // Analizar el cuerpo de la respuesta para extraer el access_token
        String[] parts = responseBody.split("&");
        for (String part : parts) {
            if (part.startsWith("access_token=")) {
                return part.substring("access_token=".length());
            }
        }
        // Manejar el caso en que no se encontró el access_token
        return "Access token no encontrado en la respuesta";
    }

    public ResponseEntity<String> createIssue(IssueDTO issueDTO){
        this.request = "createIssue";
        if (accessToken == null) {
            return getAuthorization();
        } else {
            return ResponseEntity.ok(postCreateIssue());
        }
    }

    private String postCreateIssue(){
        ResponseEntity<IssueDTO> response;
        response = webClient.post()
                .uri("https://api.github.com/repos/OscarArnedo/REPO/issues")
                .header("Authorization", "Bearer " + accessToken)
                .body(issue, IssueDTO.class)
                .retrieve()
                .toEntity(IssueDTO.class)
                .block();

        if (response != null && response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    private ResponseEntity<String> getAuthorization(){
        return webClient.get()
                .uri(githubOauth2Config.getAuthorizationUri()+"?client_id="+githubOauth2Config.getClientId()+"&scope="+githubOauth2Config.getScope())
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}

