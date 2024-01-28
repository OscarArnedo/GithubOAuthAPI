package cat.tecnocampus.dsipract1oauth.api;

import cat.tecnocampus.dsipract1oauth.application.ApplicationService;
import cat.tecnocampus.dsipract1oauth.application.dto.IssueDTO;
import cat.tecnocampus.dsipract1oauth.application.dto.RepositoryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ApplicationAPI {
    private String accessToken;
    WebClient webClient;
    private ApplicationService applicationService;

    public ApplicationAPI(WebClient webClient, ApplicationService applicationService) {
        this.webClient = webClient;
        this.applicationService = applicationService;
    }


    //To redirect the browser to a given uri, you need to return a found status and add the "location" header to the call
    @GetMapping("/redirect")
    ResponseEntity redirectExample() throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI("http://www.tecnocampus.cat/")).build();
    }

    // Example to get the user repositories from gitHub (you need to provide a real token and also return the specified
    // repositories attributes
    @GetMapping("/doNotCallMe")
    public ResponseEntity getRepositories() throws URISyntaxException {
        ResponseEntity<String> response;
        response = webClient.get()
                .uri("https://api.github.com/user/repos")
                .header("Authorization", "Bearer " + "token")
                .retrieve()
                .toEntity(String.class)
                .block();

        return response;
    }
    @GetMapping("/getRepos")
    public ResponseEntity<String> getRepos() {
       return applicationService.getRepos();
    }

    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestParam String code){
        return applicationService.getToken(code);
    }

    @GetMapping("/getIssues")
    public ResponseEntity<String> getIssues(){
        return applicationService.getIssues();
    }

    @PostMapping("/createIssue")
    public ResponseEntity<String> createIssue(@RequestBody IssueDTO issue){
        return applicationService.createIssue(issue);
    }
}
