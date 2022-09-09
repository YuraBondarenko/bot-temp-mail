package service;

import dto.Email;
import dto.Inbox;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static service.RequestService.sendRequest;

public class EmailService {
    public List<Email> getEmails(Inbox inbox) {
        try {
            String s = sendRequest("/auth/" + inbox.getToken());
            JSONObject json = new JSONObject(s);
            if (json.has("token") && json.getString("token").equals("invalid")) {
                return null;
            }

            Email[] emails = new Email[json.getJSONArray("email").length()];
            for(int i = 0; i < emails.length; i++) {
                JSONObject obj = json.getJSONArray("email").getJSONObject(i);
                emails[i] = new Email(
                        obj.getString("to"),
                        obj.getString("from"),
                        obj.getString("subject"),
                        obj.getString("body"),
                        obj.has("html") ? obj.getString("html") : null,
                        obj.getLong("date")
                );
            }
            return Arrays.stream(emails).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public Inbox generateInbox() {
        String response = sendRequest("/generate");
        JSONObject json = new JSONObject(response);
        return new Inbox(json.getString("address"), json.getString("token"));
    }
}
