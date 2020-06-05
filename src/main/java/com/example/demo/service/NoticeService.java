package com.example.demo.service;

import com.example.demo.domain.Notice;
import com.example.demo.domain.Type;
import com.example.demo.domain.Users;
import com.example.demo.domain.chat.ChatRoom;
import com.example.demo.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UsersService usersService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping
    public void addCommentNotice(Long id, Users user, Users user1, LocalDateTime registerTime) {
        //user가 작성자인 id게시글에 user1이 registerTime에 댓글을 달았다.
        Notice notice = new Notice();
        notice.setType(Type.Comment);
        notice.setUser(user);
        notice.setContent(user1.getName() + "이 게시물에 댓글을 등록했습니다.");
        notice.setLink("/posts/read/" + id);
        notice.setTime(registerTime);
        noticeRepository.save(notice);
        simpMessagingTemplate.convertAndSend("/topic/" + user.getName(),"notice");
    }
    @MessageMapping
    public void addFollowNotice(Users user1, Users user2) {
        //user1이 user2를 팔로우
        Notice notice = new Notice();
        notice.setType(Type.Follow);
        notice.setUser(user2);
        notice.setContent(user1.getName() + "님이 팔로우하였습니다.");
        notice.setLink("/users/myPage/" + user2.getId());
        notice.setTime(LocalDateTime.now());
        noticeRepository.save(notice);
        simpMessagingTemplate.convertAndSend("/topic/" + user2.getName(),"notice");
    }

    @MessageMapping
    public void addMessageNotice(ChatRoom chatRoom, Users sender, String receiver, LocalDateTime time) {
        //sender가 receiver에게 chatRoom에서 time에 메세지 전송
        Notice notice = new Notice();
        notice.setType(Type.Message);
        notice.setUser(usersService.findByName(receiver));
        notice.setTime(time);
        notice.setContent(sender.getName() + "님이 메세지를 보냈습니다.");
        notice.setLink("/personalChat/" + chatRoom.getId());
        noticeRepository.save(notice);
        simpMessagingTemplate.convertAndSend("/topic/" + receiver,"message");
    }
}
