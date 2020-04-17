App Name:
Team Members:

In this file you should include:

Any information you think we should know about your submission
* Is there anything that doesn't work? Why?
* Is there anything that you did that you feel might be unclear? Explain it here.


# Github Api Endpoints

## User Profile
* Get request to `https://api.github.com/users/frazierjoe`
```
{"login":"frazierjoe",
"id":54604023,
"node_id":"MDQ6VXNlcjU0NjA0MDIz",
"avatar_url":"https://avatars0.githubusercontent.com/u/54604023?v=4",
"gravatar_id":"",
"url":"https://api.github.com/users/frazierjoe",
"html_url":"https://github.com/frazierjoe",
"followers_url":"https://api.github.com/users/frazierjoe/followers",
"following_url":"https://api.github.com/users/frazierjoe/following{/other_user}",
"gists_url":"https://api.github.com/users/frazierjoe/gists{/gist_id}",
"starred_url":"https://api.github.com/users/frazierjoe/starred{/owner}{/repo}",
"subscriptions_url":"https://api.github.com/users/frazierjoe/subscriptions",
"organizations_url":"https://api.github.com/users/frazierjoe/orgs",
"repos_url":"https://api.github.com/users/frazierjoe/repos",
"events_url":"https://api.github.com/users/frazierjoe/events{/privacy}",
"received_events_url":"https://api.github.com/users/frazierjoe/received_events",
"type":"User",
"site_admin":false,
"name":"Joe Frazier",
"company":null,
"blog":"",
"location":"St. Louis, MO",
"email":null,
"hireable":null,
"bio":"Currently working towards a B.S. Computer Science at Washington University in St. Louis, MO.",
"public_repos":11,
"public_gists":0,
"followers":5,
"following":11,
"created_at":"2019-08-27T22:01:58Z",
"updated_at":"2020-04-12T01:16:59Z"}
```


## List of user repos 
* Get request to https://api.github.com/user/repos (set authorization header for user)
For authenticated requests set the authorization header like with "token " prepended to the token. Ex.) `xhttp.setRequestHeader("Authorization", "token "+token);`
* Returns a list of repos in the form


## List of any user's public repos
* Get request to `https://api.github.com/users/jonathandbueff/repos`

## User statistics
https://developer.github.com/v3/repos/statistics/

