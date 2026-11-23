package todoapp.dto.response;

public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;

    public TodoResponse( Long id, String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
