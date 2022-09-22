package bot.dto;

public class Email {
    private final String toAddress;
    private final String fromAddress;
    private final String subject;
    private final String body;
    private final String html;
    private final Long date;

    public Email(String toAddress, String fromAddress, String subject, String body, String html, Long date) {
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
        this.subject = subject;
        this.body = body;
        this.html = html;
        this.date = date;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getHtml() {
        return html;
    }

    public Long getDate() {
        return date;
    }
}
