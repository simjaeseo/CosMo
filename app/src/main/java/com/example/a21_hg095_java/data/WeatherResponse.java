package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {
//    @SerializedName("response")
//    Response response;



        @SerializedName("header")
        HEADER header;

        @SerializedName("body")
        BODY body;

        public BODY getBody() {return body;}


        public class HEADER{
            @SerializedName("resultCode")
            int resultCode;

            @SerializedName("resultMsg")
            String resultMsg;
        }

        public class BODY{
            @SerializedName("dataType")
            String dataType;

            @SerializedName("items")
            ITEMS items;

            public ITEMS getItems() {return items;}


            public class ITEMS{
                List<ITEM> item = new ArrayList<>();
                public List<ITEM> getItem() {return item;}

                public class ITEM{
                    @SerializedName("baseData")
                    int baseData;

                    @SerializedName("baseTime")
                    int baseTime;

                    @SerializedName("category")
                    String category;

                    public int getBaseData() {return baseData;}
                    public int getBaseTime() {return baseTime;}
                    public String getCategory() {return category;}

                }
            }

        }




//
//    @SerializedName("response")
//    Response response;
//
//    public Response getResponse() {return response;}
//
//
//    public class Response{
//        @SerializedName("header")
//        HEADER header;
//
//        @SerializedName("body")
//        BODY body;
//
//        public BODY getBody() {return body;}
//
//
//        public class HEADER{
//            @SerializedName("resultCode")
//            int resultCode;
//
//            @SerializedName("resultMsg")
//            String resultMsg;
//        }
//
//        public class BODY{
//            @SerializedName("dataType")
//            String dataType;
//
//            @SerializedName("items")
//            ITEMS items;
//
//            public ITEMS getItems() {return items;}
//
//
//            public class ITEMS{
//                List<ITEM> item = new ArrayList<>();
//                public List<ITEM> getItem() {return item;}
//
//                public class ITEM{
//                    @SerializedName("baseData")
//                    int baseData;
//
//                    @SerializedName("baseTime")
//                    int baseTime;
//
//                    @SerializedName("category")
//                    String category;
//
//                    public int getBaseData() {return baseData;}
//                    public int getBaseTime() {return baseTime;}
//                    public String getCategory() {return category;}
//
//                }
//            }
//
//        }
//
//    }






}