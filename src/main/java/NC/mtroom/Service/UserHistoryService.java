package NC.mtroom.Service;

import NC.mtroom.Entity.UserHistory;
import NC.mtroom.Repository.UserHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserHistoryService {
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @Autowired
    UserService userService;


    public UserHistory getuserHistory(Long id){
        UserHistory userHistory = userHistoryRepository.findByUserID(id);
        return userHistory;
    }
}
