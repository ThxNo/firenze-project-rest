package firenze.project.rest.example.domain;

public class Account {
    private String accountName;

    public Account(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
