package eu.binarystars.tdd.uss;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static eu.binarystars.tdd.uss.Credentials.*;

@Getter
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class SignUpData {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN)
    private String password;

    @NotNull
    @NotEmpty
    @Size(min=PASSWORD_MIN, max = PASSWORD_MAX)
    @Pattern(regexp = PASSWORD_PATTERN)
    private String passwordRepeat;

    @NotNull
    @AssertTrue
    private Boolean acceptedTerms;
}
