package CollectionsTest;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class User {
    String username;
    Integer age;
    String gender;
    BigDecimal balance;
}