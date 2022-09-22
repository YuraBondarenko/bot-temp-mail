package bot.service;

import bot.domain.Inbox;
import bot.dto.Email;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiEmailService {

    public static Inbox generateInbox() {
        String response = RequestService.sendRequest("/generate");
        JSONObject json = new JSONObject(response);
        return new Inbox(json.getString("address"), json.getString("token"));
    }

    @Nullable
    public static List<Email> getEmails(Inbox inbox) {
        try {
            String s = RequestService.sendRequest("/auth/" + inbox.getToken());
            JSONObject json = new JSONObject(s);
            if (json.has("token") && json.getString("token").equals("invalid")) {
                return null;
            }
            Email[] emails = new Email[json.getJSONArray("email").length()];
            for (int i = 0; i < emails.length; i++) {
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
            e.printStackTrace();
            return null;
        }
    }
}
