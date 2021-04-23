package com.example.puthagum;

public class job {
    private String DOJ;
    private String Psts;
    private String Pay;

    public job() {
    }

    public job(String DOJ1, String psts,String pay1) {
        DOJ = DOJ1;
        Psts = psts;
        Pay=pay1;
    }

    public String getDOJ() {
        return DOJ;
    }

    public void setPhoneNumber(String DOJ1) {
        DOJ = DOJ1;
    }

    public String getPsts() {
        return Psts;
    }

    public void setPsts(String psts) {
        Psts = psts;
    }
    public String getPay() {
        return Pay;
    }
}
