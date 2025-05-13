package com.example.loudlygmz.domain.enums;

public enum CommunityAction {
    JOIN, LEAVE;

    public String toMessageVerb(){
        return switch(this){
            case JOIN -> "ingresó a";
            case LEAVE -> "salió de";
        };
    }
}
