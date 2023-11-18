package online.ptsports.PTSports.Service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
