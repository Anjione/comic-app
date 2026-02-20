package com.example.comicbe.constant;

/**
 * @author datdv
 */
public interface AppConstant {

    interface TEMPLATE_EMAIL {
        String REGISTER_USER = "REGISTER_USER";

        String RESET_PASSWORD = "RESET_PASSWORD";

        String SAVE_USER = "SAVE_USER";
    }

    interface O2Constants {
        String CLIEN_ID = "client_id";
        String CLIENT_SECRET = "client_secret";
        String GRANT_TYPE_PASSWORD = "password";
        String AUTHORIZATION_CODE = "authorization_code";
        String REFRESH_TOKEN = "refresh_token";
        String IMPLICIT = "implicit";
        String SCOPE_READ = "read";
        String SCOPE_WRITE = "write";
        String TRUST = "openid";
        int ACCESS_TOKEN_VALIDITY_MILLISECONDS = 1 * 60 * 60;
        int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;
        int ACCESS_TOKEN_VALIDITY__SUPER_MILLISECONDS = 3 * 60 * 60;
        String HEADER_STRING = "Authorization";
        String TOKEN_PREFIX = "Bearer ";
        //String USER_NAME = "user_name";
        String USER_NAME = "user_name";
        String EMAIL = "email";
        String PASS_DEFAULT = "123456a@";

        String CLIENT_CREDENTIALS = "client_credentials";
    }

    interface TOKEN {
        String USER_ID = "userId";
        String FULL_NAME = "fullName";
        String USER_NAME = "username";
        String EMAIL = "email";

        String ACCESS_TOKEN = "access_token";
    }

    interface ResourceServer {
        final String RESOURCE_ID = "resource";
    }

    interface ACTIVE {
        boolean ACTIVE_STATUS = true;
        boolean INACTIVE_STATUS = false;
    }

    interface statusSendFile {
        Integer SUCCESS = 1;
        Integer ERROR = 0;
    }

    interface deletedStatus {
        Integer ACTIVE = 1;
        Integer INACTIVE = 0;
    }

    interface MAIL {
        String user = "aeantsoftcu@gmail.com";
    }

    interface principalKey {
        String USER_NAME = "username";
        String USER_ID = "userId";
        String FULL_NAME = "fullName";
        String HEADER_TENANT_ID = "X-TenantId";
    }

    interface sendMail {
        String successPayPal = "success";
    }

    interface RESPONSE {
        String SUCCESS = "SUCCESS";
        String FAILED = "FAILED";
    }

    interface DATE {
        String FORMAT_SQLSERVER_SHORT = "yyyy-MM-dd";
        String FORMAT_DATE_RESPONSE_ALL = "yyyy-MM-dd hh:mm:ss aa";

        String FORMAT_DATE_RESPONSE = "yyyy-MM-dd hh:mm:ss";

        String FORMAT_DATE_RESPONSE_24H = "yyyy-MM-dd HH:mm:ss";
        String FORMAT_TIME_RESPONSE = "KK:mm a";
        String TIMEZONE_ICT = "GMT+7";
        String TIMEZONE_ICT_USA_TEXAS = "GMT-6";
        String FORMAT_CODE = "yyyyMMddHHmmssSSS";
        String FORMAT_MM_DD = "MM-dd";
    }

    interface PAGING {
        Integer PAGE_NUM_DEFAULT = 1;
        Integer PAGE_SIZE_DEFAULT = 10;
        Integer PAGE_SIZE_FOR_NULL = 99999;
    }

    interface IMAGE_ACTION {
        String NOT_RESIZE = "NOT_RESIZE";

        String RESIZE = "RESIZE";
    }

    interface SYSTEM_STATUS{
        Integer ACTIVE = 1;
        Integer INACTIVE = 0;
    }

    interface TEMPLATE_CODE_EMAIL_DAYS {
        String DAY_ONE_NOT_PURCHASED = "DAY_ONE_NOT_PURCHASED";
        String DAY_TWO_NOT_PURCHASED = "DAY_TWO_NOT_PURCHASED";
        String DAY_THREE_NOT_PURCHASED = "DAY_THREE_NOT_PURCHASED";
        String DAY_FOUR_NOT_PURCHASED = "DAY_FOUR_NOT_PURCHASED";
        String DAY_FIVE_NOT_PURCHASED = "DAY_FIVE_NOT_PURCHASED";
        String DAY_SIX_NOT_PURCHASED = "DAY_SIX_NOT_PURCHASED";
        String DAY_SEVEN_NOT_PURCHASED = "DAY_SEVEN_NOT_PURCHASED";
        String DAY_ONE_PURCHASED = "DAY_ONE_PURCHASED";
        String DAY_TWO_PURCHASED = "DAY_TWO_PURCHASED";
        String DAY_THREE_PURCHASED = "DAY_THREE_PURCHASED";
        String DAY_FOUR_PURCHASED = "DAY_FOUR_PURCHASED";
        String DAY_FIVE_PURCHASED = "DAY_FIVE_PURCHASED";
        String DAY_SIX_PURCHASED = "DAY_SIX_PURCHASED";
        String DAY_SEVEN_PURCHASED = "DAY_SEVEN_PURCHASED";
    }

    interface ANSWER_CONSTANT {
        String NOT_ANSWER = "NOT ANSWER";
    }

    interface AI_CONSTANT {
        String SYSTEM_PROMPT = """
        You are an expert IELTS Speaking examiner with 10+ years of experience, strictly following official IELTS Speaking Band Descriptors.
        
        Your evaluation must be:
        - Strict and realistic (not lenient)
        - Based ONLY on the actual spoken response
        - Following official IELTS scoring criteria
        
        You MUST respond with ONLY a valid JSON object. No explanations before or after the JSON.
        """;

        String SYSTEM_PROMPT_INTERNAL = """
        You are a certified IELTS Writing examiner with over 15 years of experience.
        
        CRITICAL CSV OUTPUT REQUIREMENTS:
        ================================
        
        1. YOU MUST RETURN EXACTLY 2 LINES:
           - Line 1: CSV HEADER (column names)
           - Line 2: CSV DATA (values)
        
        2. NO TEXT BEFORE OR AFTER THE CSV
           - No explanations like "Here is the CSV:"
           - No markdown formatting (no ```csv or ```)
           - Start directly with the header line
        
        3. CSV STRUCTURE RULES:
           - Use comma (,) as delimiter
           - Use semicolon (;) to separate multiple items within a cell
           - Wrap text containing commas in double quotes
           - Escape quotes within text by doubling them ("")
           - Keep all numeric values as decimals (e.g., 6.5, not 6½)
        
        4. REQUIRED COLUMNS (in exact order):
           status, score, word_count, task_response, coherence_and_cohesion, 
           lexical_resource, grammatical_range_and_accuracy, grammar_error_count, 
           grammar_error_examples, grammar_penalty, vocab_error_count, 
           vocab_error_examples, vocab_penalty, task_error_examples, task_penalty, 
           coherence_error_examples, coherence_penalty, total_penalties, 
           word_count_penalty, corrections, suggestions, sample_answer
        
        5. NEVER LEAVE FIELDS EMPTY:
           - If no errors: use 0 for counts, 0.0 for penalties, "No errors" for examples
           - If no issues: use "N/A" or descriptive text
        
        6. ERROR EXAMPLES FORMAT:
           - Use semicolon to separate multiple errors
           - Format: "error → correction; error2 → correction2"
           - Example: "'I am go' → 'I go'; 'people is' → 'people are'"
        
        7. SCORING RULES:
           - All scores: 0.0 to 9.0 (decimals: 5.0, 5.5, 6.0, etc.)
           - score = average of 4 criteria (rounded to nearest 0.5)
           - Penalties: negative decimals (e.g., -0.5, -1.0)
           - Counts: integers (e.g., 3, 5, 10)
        
        8. WORD COUNT PENALTIES:
           Task 1 (Report/Letter): Minimum 150 words
           - 130-149 words: -0.5 penalty
           - 100-129 words: -1.0 penalty
           - < 100 words: -1.5 penalty
           
           Task 2 (Essay): Minimum 250 words
           - 220-249 words: -0.5 penalty
           - 180-219 words: -1.0 penalty
           - < 180 words: -1.5 penalty
        
        9. PENALTY CALCULATION:
           - Grammar errors: -0.1 to -0.2 per error (max -2.0)
           - Vocabulary errors: -0.1 to -0.2 per error (max -2.0)
           - Task response issues: -0.5 to -1.5
           - Coherence issues: -0.3 to -1.0
           - total_penalties = sum of all penalties
        
        EXAMPLE CSV OUTPUT:
        ==================
        status,score,word_count,task_response,coherence_and_cohesion,lexical_resource,grammatical_range_and_accuracy,grammar_error_count,grammar_error_examples,grammar_penalty,vocab_error_count,vocab_error_examples,vocab_penalty,task_error_examples,task_penalty,coherence_error_examples,coherence_penalty,total_penalties,word_count_penalty,corrections,suggestions,sample_answer
        success,6.5,287,6.5,6.0,6.5,6.0,5,"'I am go to school' → 'I go to school'; 'people is happy' → 'people are happy'; 'have many problem' → 'have many problems'",-1.0,3,"overuse of 'very'; limited academic vocabulary; repetitive use of 'good'",-0.6,"Incomplete conclusion paragraph; Some examples lack depth",-0.5,"Limited use of cohesive devices; Repetitive paragraph openings",-0.4,-2.5,0.0,"Grammar corrections: Change 'I am go' to 'I go' throughout. Use plural forms correctly. Vocabulary improvements: Replace 'very good' with 'excellent' or 'outstanding'. Use more sophisticated vocabulary like 'moreover', 'consequently' instead of basic linking words.","1) Develop each main idea with 2-3 supporting sentences. 2) Use a wider range of complex sentence structures. 3) Improve paragraph transitions with advanced cohesive devices. 4) Expand vocabulary with topic-specific academic words. 5) Ensure conclusion fully addresses the question.","In the contemporary era, the debate surrounding environmental protection has become increasingly prominent. Many argue that individual actions are insufficient to address global ecological challenges, while others maintain that personal responsibility remains crucial. This essay will examine both perspectives before presenting a balanced conclusion. [Continue with 250+ words demonstrating Band 7-8 features...]"
        
        RESPOND WITH EXACTLY 2 LINES OF CSV NOW.
        """;;
    }

}