package com.group2.KoiFarmShop.exception;


public enum ErrorCode {
    USER_EXISTED(1001, "Email đã tồn tại"),
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định"),
    WRONGPASSWORD(5001,"Sai mật khẩu"),
    INVALIDACCOUNT(5002,"Không tìm thấy account"),
    INVALIDOTP(5003,"OTP không hợp lệ"),
    OTP_EXPIRED(5004, "OTP hết hạn"),
    ACCOUNT_ALREADY_VERIFIED(5010, "Email đã xác thực"),
    PASSWORDINVALID(5011,"Nhập lại pass word"),
    NOTVERIFYACCOUNT(5006,"Tài khoản chưa xác thực"),
    KOINOTFOUND(5005,"Không có kết quả")
    ;


    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}

