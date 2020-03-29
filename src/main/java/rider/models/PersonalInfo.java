package rider.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PersonalInfo {
    private Long id;
    private String name;
    private String phoneNumber;
    private String countryCode;
}
