package dto;

import java.util.Date;
import java.util.List;

public class Inbox {
    private final String address;
    private final String token;
    private List<Email> email;

    private Date lastDate;

    public Inbox(String address, String token) {
        this.address = address;
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public String getToken() {
        return token;
    }

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(List<Email> email) {
        this.email = email;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
