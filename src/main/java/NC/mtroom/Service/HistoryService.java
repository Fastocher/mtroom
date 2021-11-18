package NC.mtroom.Service;

import NC.mtroom.Entity.History;
import NC.mtroom.Repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public History getHistory(Integer HistoryID){
        History history = historyRepository.findByHistoryID(HistoryID);
        return history;
    }

}
