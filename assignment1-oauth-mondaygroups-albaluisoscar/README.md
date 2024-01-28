# Assignment 1: Oauth 2.0

We are going to program a very small REST application that will be a very basic and reduced interface for GitHub.
Basically it needs to:

* The user authorizes your application to manage the repositories on GitHub
* List all the repositories (public and private) of the authenticated user
* List all the issues in this repository
* Create new issues in this repository

### Repositories

When listing repositories you only need to show:

* id
* name
* full name
* whether it is private
* git url
* owner's name

### Issues

When listing issues you need to show:

* id
* title
* body
* state
* a list with the name of all labels
* the user's login
* a list with the name of all assignees
* full name of the repository it belongs to

## Some help and information

### Oauth

Recall than in Oauth there are three different actors:

* Client: your web application
* Server: gitHub
* Owner: the user on behalf of whom we are accessing repositories and issues

### Steps you need to do

1. First of all, before you begin to code: Register your application in GitHub and obtain the Client ID and the Client Secret. In your GitHub page, go to your
   profile, select settings, on the left go to "Oauth applications" under "Developer settings" label.
    1. set the Authorization callback URL to your application (for example http://localhost:8080/getToken)
1. When you don't have the token (the first time the user access the application) you need to:
    1. Call to GitHub to get the authorization code (first call in the example file oauthCalls.http). GitHub will ask the user to authenticate and to authorize your application.
       In the call, set the scope to: repo (it will access to read and **write** user's repos)
    1. Once the user authorizes you (the client), it will redirect to your application (localhost), concretely to the url 
    you provided (for example http://localhost:8080/getToken). In the redirection url, you'll get the authorization code as a request parameter called "code". 
2. Now, exchange the authorization code for the token. You'll need to call GitHub again (second call in the example file oauthCalls.http) to get the token. 
   You'll need to provide the authorization code and the client secret. The response's body will contain the token.
1. Now, you can get the repos from GitHub providing the token in the header of the request (third call in the example file oauthCalls.http)
1. Once, you are able to list the repos, extend your application to read and create issues in this repository.

### This repository
* Configuration: you'll find two files. One that crates a bean with the REST client and another that reads the GitHub configuration
from the application.yml file.
* api/ApplicationAPI: a RestController with an example of how to redirect the browser to another page and a (non-working) example to 
call the GitHub api to get all the respositories. It doesn't work because it doesn't have the token, otherwise it would
* resources/oauthCalls example calls of how to get the repositories by hand, that is, calling yourself the GitHub api

### Useful links:

* GitHub documentation on how to create a new Oauth application to access their API: https://docs.github.com/en/developers/apps/building-oauth-apps/creating-an-oauth-app
* GitHub documentation on how to get the authorization code: https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps
* You may also want to read about GitHub scopes (kind of permissions that can be granted to applications): https://docs.github.com/en/developers/apps/building-oauth-apps/scopes-for-oauth-apps#available-scopes
* A tutorial for the Spring's REST client (WebClient) https://reflectoring.io/spring-webclient/ 
* API GitHub: 
    * List repositories for authenticated user: https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-the-authenticated-user
    * List repository issues: https://docs.github.com/en/rest/issues/issues?apiVersion=2022-11-28#list-repository-issues
    * Create repository issues: https://docs.github.com/en/rest/issues/issues?apiVersion=2022-11-28#create-an-issue
