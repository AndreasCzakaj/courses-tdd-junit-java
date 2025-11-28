package eu.binarystars.tdd.uss;

import eu.binarystars.tdd.uss.mail.MailSenderListImpl;
import eu.binarystars.tdd.uss.service.LoginService;
import eu.binarystars.tdd.uss.service.SignUpService;
import eu.binarystars.tdd.utils.GenericDao;
import eu.binarystars.tdd.utils.GenericDaoMapImpl;

import java.util.Optional;

public class UssTestHelpers {
    public static final String EXISTING_USER_ID = "myid";
    public static final String EXISTING_EMAIL = "existing@test.com";
    public static final String EXISTING_PASSWORD = "aBC321654";
    public static final String EXISTING_PASSWORD_HASH = EXISTING_PASSWORD;

    public static final String NON_EXISTING_EMAIL = "i_do_not_exist@test.com";
    public static final String WRONG_PASSWORD = "aBC321654";

    public static GenericDao<String, User> createUserDao() {
        GenericDaoMapImpl<String, User> out = new GenericDaoMapImpl<>(User::getEmail);
        out.repo.put(EXISTING_EMAIL, createValidUser(EXISTING_USER_ID));
        return out;
    }

    public static User createValidUser(String id) {
        return User.builder()
                .id(id)
                .email(EXISTING_EMAIL)
                .passwordHash(EXISTING_PASSWORD_HASH)
                .acceptedTerms(true)
                .build();
    }

    public static Credentials createCredentialsForExistingUser() {
        return Credentials.builder().email(EXISTING_EMAIL).password(EXISTING_PASSWORD).build();
    }

    public static SignUpData createSignUpDataForExistingUser() {
        return SignUpData.builder()
                .email(EXISTING_EMAIL).password(EXISTING_PASSWORD)
                .passwordRepeat(EXISTING_PASSWORD)
                .acceptedTerms(true)
                .build();
    }

}
