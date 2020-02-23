package pl.kfryc.bugtracker.validation;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kfryc.bugtracker.dao.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


    private UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        return email!=null && (userRepository.findByEmail(email)==null);
    }
}
