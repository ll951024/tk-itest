package com.cy.test.longbit.resp;

public class UserInfoResponse {
    /**
     * message : null
     * data : {"uuid":"a123c7daa7b4430d967fdd3a80739efa","token":"Jl8cZIQOHyLIaismJFoijEmTa70XRx5w","auth":"YTEyM2M3ZGFhN2I0NDMwZDk2N2ZkZDNhODA3MzllZmE6Smw4Y1pJUU9IeUxJYWlzbUpGb2lqRW1UYTcwWFJ4NXc="}
     */

    private Object message;
    private DataBean data;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uuid : a123c7daa7b4430d967fdd3a80739efa
         * token : Jl8cZIQOHyLIaismJFoijEmTa70XRx5w
         * auth : YTEyM2M3ZGFhN2I0NDMwZDk2N2ZkZDNhODA3MzllZmE6Smw4Y1pJUU9IeUxJYWlzbUpGb2lqRW1UYTcwWFJ4NXc=
         */

        private String uuid;
        private String token;
        private String auth;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }
    }
}
