# User Self Service: Login (legacy code)

Start the server (port 3000, override with env `PORT`):

``` bash
cd legacy-uss
mvn compile exec:java
```

Seed accounts (in-memory H2 database):

| username | password | status |
|---|---|---|
| `alice_verified` | `Correct-Horse_42` | verified |
| `bob_not_verified` | `Battery.Staple+7` | new |

Try it:

``` bash
# 200 + session
curl -i -X POST localhost:3000/uss/login -H 'Content-Type: application/json' \
  -d '{"username":"alice_verified","password":"Correct-Horse_42"}'
# 401: wrong password (the same for an unknown username)
curl -i -X POST localhost:3000/uss/login -d '{"username":"alice_verified","password":"Wrong-Horse_42"}'
# 400: not verified
curl -i -X POST localhost:3000/uss/login -d '{"username":"bob_not_verified","password":"Battery.Staple+7"}'
# 400: invalid syntax
curl -i -X POST localhost:3000/uss/login -d '{"username":"alice"}'
```
