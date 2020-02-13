package dashboard.services;

public interface SmsService {

    Object sendSms(String phone, String otpCode);
}
