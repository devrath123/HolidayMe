package com.holidayme.data;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 05-07-2017.
 */

public class HolidayCityBean {

    String city,hotelImage,hotel,hotelLocation;
    ArrayList<RoomOptions> roomOptionsArrayList;

    public class RoomOptions{

        String roomType,roomCost;

        public RoomOptions(String roomType, String roomCost) {
            this.roomType = roomType;
            this.roomCost = roomCost;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomCost() {
            return roomCost;
        }

        public void setRoomCost(String roomCost) {
            this.roomCost = roomCost;
        }
    }

}
