package it.polito.ezshop.data;

public class UserImpl implements User {
    private int mId;
    private String mUsername;
    private String mPassword;
    private String mRole;

    public UserImpl(int id, String username, String password, String role) {
        mId = id;
        mUsername = username;
        mPassword = password;
        mRole = role;
    }

    @Override
    public Integer getId() {
        return mId;
    }

    @Override
    public void setId(Integer id) {
        if (id >= 0) {
            mId = id;
        }
    }

    @Override
    public String getUsername() {
        return mUsername;
    }

    @Override
    public void setUsername(String username) {
        if (username != null && username.length() > 0) {
            mUsername = username;
        }
    }

    @Override
    public String getPassword() {
        return mPassword;
    }

    @Override
    public void setPassword(String password) {
        if (password != null && password.length() > 0) {
            mPassword = password;
        }
    }

    @Override
    public String getRole() {
        return mRole;
    }

    @Override
    public void setRole(String role) {
        if (role != null && role.length() > 0 && (role.equals("Administrator") || role.equals("Cashier") || role.equals("ShopManager"))) {
            mRole = role;
        }
    }

    public User clone(){
        return new UserImpl(
                mId,
                mUsername,
                mPassword,
                mRole
        );
    }
}
