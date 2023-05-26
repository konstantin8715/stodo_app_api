package xyz.stodo.factory;

import org.springframework.stereotype.Component;
import xyz.stodo.entity.Task;
import xyz.stodo.payload.dto.TaskResponseDto;

@Component
public class TaskResponseDtoFactory {
    
    public TaskResponseDto makeTaskResponseDto(Task task) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(task.getId());
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDone(task.isDone());
        taskResponseDto.setCreatedAt(task.getCreatedAt());
        taskResponseDto.setDeadlineDate(task.getDeadlineDate());

        return taskResponseDto;
    }
}
