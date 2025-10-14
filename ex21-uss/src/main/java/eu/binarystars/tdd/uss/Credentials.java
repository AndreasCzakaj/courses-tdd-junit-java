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

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min=8, max = 16)
    @Pattern(regexp = "[a-z]+[A-Z]+[0-9]+")
    private String password;
}
