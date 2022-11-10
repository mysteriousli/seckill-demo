package education.mybatis;

public class UserController {
    @MyAutowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }
}
