package cn.lueans.shuhui.entity;

/**
 * Created by 24277 on 2017/5/22.
 */

public class LoginEntity {

    /**
     * ErrCode : 0
     * ErrMsg : 0
     * Return : {"Id":147647,"NickName":null,"Email":"2427770136@qq.com","Phone":null,"RegFromType":2,"Avatar":"/Content/images/touxi.jpg"}
     * Costtime : 6
     * IsError : false
     * Message :
     */

    private String ErrCode;
    private String ErrMsg;
    private ReturnBean Return;
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

    public ReturnBean getReturn() {
        return Return;
    }

    public void setReturn(ReturnBean Return) {
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
        return "LoginEntity{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", Return=" + Return +
                ", Costtime='" + Costtime + '\'' +
                ", IsError=" + IsError +
                ", Message='" + Message + '\'' +
                '}';
    }

    public static class ReturnBean {
        /**
         * Id : 147647
         * NickName : null
         * Email : 2427770136@qq.com
         * Phone : null
         * RegFromType : 2
         * Avatar : /Content/images/touxi.jpg
         */

        private int Id;
        private Object NickName;
        private String Email;
        private Object Phone;
        private int RegFromType;
        private String Avatar;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public Object getNickName() {
            return NickName;
        }

        public void setNickName(Object NickName) {
            this.NickName = NickName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public Object getPhone() {
            return Phone;
        }

        public void setPhone(Object Phone) {
            this.Phone = Phone;
        }

        public int getRegFromType() {
            return RegFromType;
        }

        public void setRegFromType(int RegFromType) {
            this.RegFromType = RegFromType;
        }

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        @Override
        public String toString() {
            return "ReturnBean{" +
                    "Id=" + Id +
                    ", NickName=" + NickName +
                    ", Email='" + Email + '\'' +
                    ", Phone=" + Phone +
                    ", RegFromType=" + RegFromType +
                    ", Avatar='" + Avatar + '\'' +
                    '}';
        }
    }
}
