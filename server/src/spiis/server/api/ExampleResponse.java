package spiis.server.api;

public class ExampleResponse {
    private Long id;
    private String name;
    private int subjectCount;

    public ExampleResponse(Long id, String name, int subjectCount) {
        this.id = id;
        this.name = name;
        this.subjectCount = subjectCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }
}
