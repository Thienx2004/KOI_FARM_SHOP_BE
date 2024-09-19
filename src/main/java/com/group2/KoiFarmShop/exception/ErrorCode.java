package com.group2.KoiFarmShop.exception;


public enum ErrorCode {
    USER_EXISTED(1001, "Email đã tồn tại"),
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định"),
    WRONGPASSWORD(5001,"Sai mật khẩu"),
    INVALIDACCOUNT(5002,"Không tìm thấy account"),
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

