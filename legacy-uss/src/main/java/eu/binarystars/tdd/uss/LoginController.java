package eu.binarystars.tdd.uss;

public class LoginController {
    private final UserSelfService service;

    public LoginController(final UserSelfService service) {
        this.service = service;
    }

    public HttpResult action(final Object body) {
        // the body is untrusted input: the service validates it
        return ControllerUtils.respond(200, () -> service.login(body));
    }
}
