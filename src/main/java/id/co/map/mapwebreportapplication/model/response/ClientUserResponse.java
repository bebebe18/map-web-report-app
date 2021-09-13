package id.co.map.mapwebreportapplication.model.response;

import id.co.map.mapwebreportapplication.model.User;

/**
 * @author Awie on 10/31/2019
 */
public class ClientUserResponse extends ClientResponse {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
