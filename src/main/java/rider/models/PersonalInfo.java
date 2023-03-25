package rider.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonalInfo {
    private Long id;
    private String name;
    private String phoneNumber;
    private String countryCode;

    public abstract boolean isNull();
}
