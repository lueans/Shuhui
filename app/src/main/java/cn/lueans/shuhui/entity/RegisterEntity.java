package cn.lueans.shuhui.entity;

/**
 * Created by 24277 on 2017/5/22.
 */

public class RegisterEntity {

    /**
     * ErrCode : -2
     * ErrMsg : 提交的表单信息不正确填写密码
     * Return : null
     * Costtime : 6
     * IsError : true
     * Message :
     */

    private String ErrCode;
    private String ErrMsg;
    private Object Return;
    private String Costtime;
    private boolean IsError;
    private String Message;

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public Object getReturn() {
        return Return;
    }

    public void setReturn(Object Return) {
        this.Return = Return;
    }

    public String getCosttime() {
        return Costtime;
    }

    public void setCosttime(String Costtime) {
        this.Costtime = Costtime;
    }

    public boolean isIsError() {
        return IsError;
    }

    public void setIsError(boolean IsError) {
        this.IsError = IsError;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public String toString() {
        return "RegisterEntity{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", Return=" + Return +
                ", Costtime='" + Costtime + '\'' +
                ", IsError=" + IsError +
                ", Message='" + Message + '\'' +
                '}';
    }
}
