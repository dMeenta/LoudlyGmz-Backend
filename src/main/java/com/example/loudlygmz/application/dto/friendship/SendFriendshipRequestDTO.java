package com.example.loudlygmz.application.dto.friendship;

import lombok.Data;

@Data
public class SendFriendshipRequestDTO {
    private String senderUsername;
    private String receiverUsername;
}
