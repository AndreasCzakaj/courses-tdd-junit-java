package eu.binarystars.tdd.uss;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    static final int PASSWORD_MIN = 8;
    static final int PASSWORD_MAX = 16;
    static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]+$";

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;
}
