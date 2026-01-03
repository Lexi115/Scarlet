# Scarlet • Wordle-like RESTful game 💬

<div align="center">
    <img src="https://custom-icon-badges.demolab.com/badge/Java-21-red?style=for-the-badge&labelColor=923434&logo=java&logoColor=white" alt="Java 21">
    <img src="https://custom-icon-badges.demolab.com/github/actions/workflow/status/Lexi115/ProjectScarlet/workflow.yml?style=for-the-badge&labelColor=383838&logo=github&logoColor=white" alt="GitHub Actions Build Status">
    <img src="https://custom-icon-badges.demolab.com/badge/License-MIT-blue?style=for-the-badge&labelColor=1f4d68" alt="License MIT">
</div>

**Scarlet is a REST web application replica of the popular online guessing
game [Wordle](https://www.nytimes.com/games/wordle)**: a word is randomly chosen by the
system at set intervals and players have to guess it, receiving hints along the way.

## Features

- Unlimited tries to guess the word.
- Ability to create a user account to keep track of every win.
- Fast game performance thanks to in-memory caching.

## Setup & Installation

1) Install and run [Docker](https://www.docker.com).
2) Open your terminal and ```cd``` into the project root directory.
3) Execute the ```docker compose up``` command.
4) Enjoy sending HTTP requests to the available endpoints!

## Usage

Here are the main endpoints:

- ```POST /words/guess```: guess the chosen word.
- ```POST /words/randomWord```: force the system to choose a random word.
- ```POST /auth/login```: log into your account.
- ```POST /users```: create an account.
- ```GET /users/{username}```: get information about a certain user.

Check out the API documentation at ```/api-docs``` (or ```/swagger-ui``` for a more user-friendly
interface) for more details.

## Contributing

This project currently isn't open to pull requests.

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit/).
