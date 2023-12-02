package online.ptsports.PTSports.Config;

public class AppConstants {
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE="10";
    public static final String SORT_CATEGORIES_BY = "categoryId";
    public static final String SORT_PRODUCTS_BY = "id";
    public static final String SORT_USERS_BY = "userId";
    public static final String SORT_ORDERS_BY = "totalAmount";
    public static final String SORT_DIR = "asc";
    public static final Integer ADMIN_ID = 101;
    public static final Integer USER_ID = 102;

    // Thời gian hết hạn của token (5 phút)
    public static final long PASSWORD_RESET_TOKEN_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút tính bằng mili giây

}
