package com.example.comicbe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author datdv
 */
public enum ErrorCode {

    SUCCESS(HttpStatus.OK, "IELTS.000", "Success"),
    FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.999", "System error"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "IELTS.404", "Could not find the Id"),
    API_NOT_FOUND(HttpStatus.NOT_FOUND, "IELTS.404", "API Not Found"),
    AUTHORIZATION_FIELD_MISSING(HttpStatus.FORBIDDEN, "IELTS.40011", "Please log in"),
    CAN_NOT_DELETE_COURSE(HttpStatus.BAD_REQUEST,"IELTS.40018","Student is studying"),
    SIGNATURE_NOT_CORRECT(HttpStatus.FORBIDDEN,"IELTS.40001","Signature not correct"),
    EXPIRED(HttpStatus.FORBIDDEN,"IELTS.TOKEN.EXPIRED","Token Expired"),
    UN_SUPPORT_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "IELTS.40020", "Unsupport this file extension"),
    DOB_NOT_NULL(HttpStatus.BAD_REQUEST, "IELTS.DOB.NOT.NULL", "dob not null"),

    //FORMAT DATE EXCEPTION
    FORMAT_DATE_FAILED(HttpStatus.BAD_REQUEST, "IELTS.PARSE.DATE.FAILED", "booking parse date failed"),

    LIMIT_SCORING_WITH_AI_FOR_USER_NOT_PURCHASED(HttpStatus.BAD_REQUEST,"IELTS.LIMIT.SCORING.WITH.AI.FOR.USER.NOT.PURCHASED", "limit scoring with AI for user not purchased"),

    // FILE
    UPLOAD_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.UPLOAD.FILE.ERROR", "upload file error"),
    GEN_LINK_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.GEN.LINK.FILE.ERROR", "upload file error"),
    
    //EXAM LIBRARY
    EXAM_LIBRARY_NAME_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.LIBRARY.NAME.NOT.EMPTY", "library name not empty"),
    EXAM_LIBRARY_CODE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.LIBRARY.CODE.NOT.EMPTY", "library code not empty"),
    EXAM_LIBRARY_NAME_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.LIBRARY.NAME.DUPLICATE", "exam library name duplicate"),
    EXAM_LIBRARY_CODE_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.LIBRARY.CODE.DUPLICATE", "exam library code duplicate"),
    EXAM_LIBRARY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.LIBRARY.NOT.FOUND", "exam library not found"),
    // EXAM
    EXAM_NAME_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.NAME.NOT.EMPTY", "exam name not empty"),
    EXAM_CODE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.CODE.NOT.EMPTY", "exam code not empty"),
    EXAM_TITLE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.TITLE.NOT.EMPTY", "exam title not empty"),
    EXAM_SHORT_DESCRIPTION_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.SHORT.DESCRIPTION.NOT.EMPTY", "exam short description not empty"),
    EXAM_PACKAGE_NOT_NULL(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.SHORT.DESCRIPTION.NOT.EMPTY", "exam packageId not null"),
    EXAM_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.TITLE.DUPLICATE", "exam duplicate"),
    EXAM_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.NOT.FOUND", "exam not found"),
    EXAM_AVATAR_LINK_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.AVATAR.LINK.NOT.EMPTY", "exam avatar link not empty"),
    EXAM_TYPE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.EXAM.TYPE.NOT.EMPTY", "exam type not empty"),

    // EXAM CATEGORY
    EXAM_CATEGORY_TITLE_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.CATEGORY.TITLE.DUPLICATE", "exam category title duplicate"),
    EXAM_CATEGORY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.CATEGORY.NOT.FOUND", "exam category not found"),

    //EXAM SOURCE
    EXAM_SOURCE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.SOURCE.NOT.FOUND", "exam source not found"),
    EXAM_SOURCE_TITLE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.SOURCE.TITLE.NOT.EMPTY", "exam source title not empty"),
    EXAM_SOURCE_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.SOURCE.DUPLICATE", "exam source duplicate"),

    //QUESTION PACKAGE
    QUESTION_PACKAGE_TITLE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PACKAGE.TITLE.NOT.EMPTY", "package title not empty"),
    QUESTION_PACKAGE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PACKAGE.NOT.FOUND", "package not found"),

    //QUESTION PACKAGE VOCABULARY
    VOCABULARY_CONTENT_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.VOCABULARY.CONTENT.DUPLICATE", "vocabulary content duplicate"),
    VOCABULARY_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.VOCABULARY.NOT.FOUND", "vocabulary not found"),

    //NEWS
    NEWS_TITLE_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.NEWS.TITLE.DUPLICATE", "news title duplicate"),
    NEWS_TITLE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.NEWS.TITLE.NOT.EMPTY", "news title not empty"),
    NEWS_AVATAR_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.NEWS.AVATAR.NOT.EMPTY", "news avatar not empty"),
    NEWS_STATUS_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.NEWS.STATUS.NOT.EMPTY", "news status not empty"),
    NEWS_SHORT_DESCRIPTION_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.NEWS.SHORT.DESCRIPTION.NOT.EMPTY", "news status not empty"),
    NEWS_CONTENT_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.NEWS.CONTENT.NOT.EMPTY", "news content not empty"),

    NEWS_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.NEWS.NOT.FOUND", "news not found"),

    // PRODUCT
    PRODUCT_NAME_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.NAME.DUPLICATE", "product name duplicate"),
    PRODUCT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.NOT.FOUND", "product not found"),
    PRODUCT_NAME_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.NAME.NOT.EMPTY", "product name not empty"),
    PRODUCT_PRICE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.PRICE.NOT.EMPTY", "product price not empty"),
    PRODUCT_DAYS_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.DAYS.NOT.EMPTY", "product days not empty"),

    //DISCOUNT
    DISCOUNT_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.DISCOUNT.DUPLICATE", "discount duplicate"),
    DISCOUNT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.DISCOUNT.NOT.FOUND", "discount not found"),
    DISCOUNT_TYPE_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.TYPE.NOT.SUPPORT", "discount type not support"),
    DISCOUNT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.NOT.AVAILABLE", "discount not available"),
    DISCOUNT_HOLD_AVAILABLE(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.HOLD.AVAILABLE", "discount hold available"),
    DISCOUNT_HOLD_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.HOLD.NOT.AVAILABLE", "discount hold not available"),
    DISCOUNT_HOLD_EXPIRE(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.HOLD.EXPIRE", "discount hold expire"),
    DISCOUNT_CODE_USED(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.CODE.USED", "discount code used"),
    DISCOUNT_CODE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.DISCOUNT.CODE.NOT.EMPTY", "discount code not empty"),
    FILE_TYPE_NOT_SUPPORT(HttpStatus.BAD_REQUEST, "IELTS.FILE.TYPE.NOT.SUPPORT", "file type not support"),
    DELETE_FILE_TYPE_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.FILE.TYPE.NOT.EMPTY", "file type not empty"),
    DELETE_FILE_NAME_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.FILE.NAME.NOT.EMPTY", "file name not empty"),
    DELETE_FILE_ENTITY_NAME_NOT_EMPTY(HttpStatus.BAD_REQUEST, "IELTS.FILE.ENTITY.NAME.NOT.EMPTY", "file entity name not empty"),
    DELETE_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.DELETE.FILE.ERROR", "delete file error"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "IELTS.USER.NOT.FOUND", "user not found"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "IELTS.USER.ALREADY.EXISTS", "user already exists"),
    EMAIL_TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "IELTS.EMAIL.TEMPLATE.NOT.FOUND", "email template not found"),
    SENT_EMAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SENT.EMAIL.ERROR", "sent email error"),
    EMAIL_TEMPLATE_NOT_VALID(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EMAIL.TEMPLATE.NOT.VALID", "email template not valid"),
    EMAIL_TEMPLATE_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EMAIL.TEMPLATE.DUPLICATE", "email template duplicate"),
    EMAIL_CONFIRM_TIME_RANGE_OVER(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EMAIL.CONFIRM.TIME.RANGE.OVER", "email confirm time range over"),
    EMAIL_ACTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EMAIL.ACTION.NOT.FOUND", "email action not found"),
    SEND_EMAIL_TEMPLATE_CODE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SEND.EMAIL.TEMPLATE.CODE.NOT.EMPTY", "template code not empty"),
    SEND_EMAIL_TO_ADDRESS_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SEND.EMAIL.TO.ADDRESS.NOT.EMPTY", "to address not empty"),
    SEND_EMAIL_SUBJECT_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SEND.EMAIL.SUBJECT.NOT.EMPTY", "subject not empty"),
    SEND_EMAIL_CONTENT_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SEND.EMAIL.CONTENT.NOT.EMPTY", "content not empty"),

    PAYMENT_TRANSACTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PAYMENT.TRANSACTION.NOT.FOUND", "payment transaction not found"),
    PAYMENT_TRANSACTION_INVALID_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PAYMENT.TRANSACTION.INVALID.STATUS", "payment transaction invalid status"),
    PAYMENT_TRANSACTION_INVALID_USER(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PAYMENT.TRANSACTION.INVALID.USER", "payment transaction invalid user"),
    PAYMENT_CONFIG_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PAYMENT.CONFIG.NOT.FOUND", "payment config not found"),
    PAYMENT_CONFIG_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PAYMENT.CONFIG.DUPLICATE", "payment config duplicate"),
    VERIFY_CAPTCHA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.VERIFY.CAPTCHA.ERROR", "verify captcha error"),
    EMAIL_NOT_MATCH(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.VERIFY.EMAIL.NOT.MATCH", "verify email not match"),
    REFRESH_TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED, "IELTS.REFRESH.TOKEN.EXPIRE", "refresh token expire"),
    DEVICE_LIMIT(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.DEVICE.LIMIT", "device limit"),
    OLD_PASS_NOT_MATCH(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.OLD.PASS.NOT.MATCH", "user old pass not match"),
    CONFIRM_PASS_NOT_MATCH(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.CONFIRM.PASS.NOT.MATCH", "user confirm pass not match"),
    INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "IELTS.INVALID.REQUEST.PARAMETER", "invalid request parameter"),
    VERIFY_TOKEN_GOOGLE_FAILED(HttpStatus.UNAUTHORIZED, "IELTS.INVALID.VERIFY.GOOGLE.TOKEN", "invalid request verify google token"),
    VERIFY_TOKEN_FACEBOOK_FAILED(HttpStatus.UNAUTHORIZED, "IELTS.INVALID.VERIFY.FACEBOOK.TOKEN", "invalid request verify facebook token"),

    // USER
    USER_FULL_NAME_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.USER.FULL.NAME.NOT.EMPTY", "user fullName not empty"),
    USER_USER_NAME_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.USER.USERNAME.NOT.EMPTY", "username not empty"),
    USER_AVATAR_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.USER.USERNAME.NOT.EMPTY", "username not empty"),
    USER_EMAIL_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.USER.EMAIL.NOT.EMPTY", "email not empty"),
    USER_ROLE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.USER.ROLE.NOT.EMPTY", "roles not empty"),
    USERNAME_OR_PASSWORD_NOT_CORRECT(HttpStatus.UNAUTHORIZED, "IELTS.USERNAME.OR.PASSWORD.NOT.CORRECT", "username or password is not correct"),
    USER_LOCKED(HttpStatus.UNAUTHORIZED, "IELTS.USER.LOCKED", "user locked"),
    USER_REQUIRED_CONFIRM_EMAIL(HttpStatus.UNAUTHORIZED, "IELTS.USER.REQUIRED.CONFIRM.EMAIL", "user required confirm email"),

    PRODUCT_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.PRODUCT.ID.NOT.EMPTY", "productId not empty"),

    //EXAM CATEGORY
    EXAM_CATEGORY_TITLE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.CATEGORY.TITLE.NOT.EMPTY", "category title not empty"),
    EXAM_CATEGORY_CODE_NOT_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.EXAM.CATEGORY.CODE.NOT.EMPTY", "category code not empty"),

    SUBMIT_ANSWER_AUDIO_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.SUBMIT.ANSWER.FAILED", "submit answer failed"),
    QUESTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "IELTS.QUESTION.NOT.FOUND", "question not found"),;



    private final HttpStatus status;
    private final String code;
    private final String message;

    private final static Map<String, ErrorCode> lookup = new HashMap<>();

    static {
        EnumSet.allOf(ErrorCode.class).forEach(item -> lookup.put(item.code(), item));
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }

    public static ErrorCode lookupForCode(String code) {
        return StringUtils.hasText(code) ? lookup.get(code) : null;
    }
}