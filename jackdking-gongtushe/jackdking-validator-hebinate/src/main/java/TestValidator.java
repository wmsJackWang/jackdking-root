import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TestValidator
 * @Description TODO
 * @Author jackdking
 * @Date 26/07/2022 5:53 下午
 * @Version 2.0
 **/
public class TestValidator {
    public static void main(String[] args) {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        TemporaryContactRequest test = new TemporaryContactRequest();
        test.setTemporaryContactPhoneNumber("12121212");
        test.setTemporaryContactCreateOn("34534534534");
        Set<ConstraintViolation<TemporaryContactRequest>> result = validator.validate(test);

        System.out.println(result.size());

    }


    static class TemporaryContactRequest {

        /*
         * 临时联系人手机号 不超过11 个字符，不能为空
         */
        @NotBlank(message = "临时联系人手机号不能为空")
        String temporaryContactPhoneNumber;

        /*
         * 临时联系人添加时间 时间戳，不能为空
         */
        @Pattern(regexp = "^[0-9]*$", message = "临时联系人添加时间不能存在非数字字符")
        @NotBlank(message = "临时联系人添加时间不能为空")
        String temporaryContactCreateOn;

        public void setTemporaryContactPhoneNumber(String temporaryContactPhoneNumber) {
            this.temporaryContactPhoneNumber = temporaryContactPhoneNumber;
        }

        public String getTemporaryContactPhoneNumber() {
            return temporaryContactPhoneNumber;
        }

        public String getTemporaryContactCreateOn() {
            return temporaryContactCreateOn;
        }

        public void setTemporaryContactCreateOn(String temporaryContactCreateOn) {
            this.temporaryContactCreateOn = temporaryContactCreateOn;
        }
    }
}