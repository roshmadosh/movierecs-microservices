package link.hiroshiprojects.movierecs.gatewayservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AppUser {
    private String firstName;
    private String lastName;
    private String userId;

    public static class Builder {
        private String firstName;
        private String lastName;
        private String userId;

        public Builder() {}

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }
       public AppUser build() {
           return new AppUser(this);
       }
    }

    private AppUser(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userId = builder.userId;
    }

}
