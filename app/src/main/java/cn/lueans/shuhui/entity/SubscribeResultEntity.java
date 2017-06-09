package cn.lueans.shuhui.entity;

/**
 *订阅操作的返回结果
 * Created by 24277 on 2017/6/5.
 */

public class SubscribeResultEntity {

    /**
     * ErrCode :
     * ErrMsg :
     * Return : true
     * Costtime : 47
     * IsError : false
     * Message : 备注Return:操作结果,BookId:49
     */

    private String ErrCode;
    private String ErrMsg;
    private boolean Return;
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

    public boolean isReturn() {
        return Return;
    }

    public void setReturn(boolean Return) {
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
        return "SubscribeResultEntity{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", Return=" + Return +
                ", Costtime='" + Costtime + '\'' +
                ", IsError=" + IsError +
                ", Message='" + Message + '\'' +
                '}';
    }
}
