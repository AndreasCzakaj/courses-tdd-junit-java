For e2e testing you need an SMTP server, e.g.
`docker run -d --name mailhog -p 1025:1025 -p 8025:8025 mailhog/mailhog`
open `http://localhost:8025/#`

test login:
`curl -X POST http://localhost:8080/login -H "Content-Type: application/json"  -d '{"email":"andreas.czakaj@binary-stars.eu","password":"abcdcryptHash123"}`

test signup
`curl -X POST http://localhost:8080/signUp -H "Content-Type: application/json" -d '{"email":"test@example.com","password":"ldkf314AA", "passwordRepeat":"ldkf314AA","acceptedTerms":true}`