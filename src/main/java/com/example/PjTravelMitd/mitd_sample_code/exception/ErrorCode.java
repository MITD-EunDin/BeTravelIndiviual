package com.example.PjTravelMitd.mitd_sample_code.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZE_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_REQUIRED(1003, "Password required", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1004, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    USERNAME_REQUIRED(1005, "Username required", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
    TOUR_NOT_EXISTED(1010, "Tour not existed", HttpStatus.NOT_FOUND),
    BOOKING_NOT_FOUND(1011, "Booking not found", HttpStatus.NOT_FOUND),
    PAYMENT_AMOUNT_EXCEED(1012, "Payment amount exceed", HttpStatus.BAD_REQUEST),
    PAYMENT_ALREADY_COMPLETED(1013, "Payment already completed", HttpStatus.BAD_REQUEST),
    INVALID_BOOKING_STATUS(1014, "Invalid booking status", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_AMOUNT(1015, "Invalid payment amount", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PAYMENT_AMOUNT(1016, "Insufficient payment amount", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_DEPOSIT_AMOUNT(1017, "Insufficient deposit amount", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(1018, "Notification not found", HttpStatus.BAD_REQUEST),
    INVALID_ROLE(1019, "Invalid role", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_INFO(1020, "Invalid order info", HttpStatus.BAD_REQUEST),
    INVALID_AMOUNT(1021, "Invalid amount", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_EXISTED(1022, "Schedule not existed", HttpStatus.BAD_REQUEST),
    BOOKING_LIMIT_EXCEEDED(1023, "Booking limit exceeded", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_COUNT(1024, "Invalid image count", HttpStatus.BAD_REQUEST),
    INVALID_TOUR_ID(1025, "Invalid tour ID", HttpStatus.BAD_REQUEST),
    INVALID_RATING(1026, "Rating must be between 1 and 5", HttpStatus.BAD_REQUEST),
    INVALID_COMMENT(1027, "Comment cannot be empty", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND(1028, "Payment not found", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_CHECKSUM(1029, "Invalid payment checksum", HttpStatus.BAD_REQUEST),
    PAYMENT_FAILED(1030, "Payment failed", HttpStatus.BAD_REQUEST),
    INVALID_BOOKING_ID(1031, "Invalid bookingId", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1032, "Email existed", HttpStatus.BAD_REQUEST),
    FIREBASE_ERROR(1033, "Firebase error", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1034, "Invalid email", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}