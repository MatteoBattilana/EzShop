package it.polito.ezshop.data;

public class UserImpl implements User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;

    public UserImpl(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = "";
        this.surname = "";
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        if (id >= 0) {
            this.id = id;
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        if (username != null && username.length() > 0) {
            this.username = username;
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        if (password != null && password.length() > 0) {
            this.password = password;
        }
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        updateUserRights(role);
    }

    /**
     * Use to update the role of the user
     *
     * @param role the new role of the user
     */
    public void updateUserRights(String role){
        if (role != null && role.length() > 0 && (role.equals("Administrator") || role.equals("Cashier") || role.equals("ShopManager"))) {
            this.role = role;
        }
    }
}
