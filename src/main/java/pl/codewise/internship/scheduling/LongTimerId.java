package pl.codewise.internship.scheduling;

public class LongTimerId implements TimerId {
    private final Long Id;
    public LongTimerId(Long id) {
        Id = id;
    }

    public Long getId() {
        return Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongTimerId that = (LongTimerId) o;

        return Id.equals(that.Id);
    }

    @Override
    public int hashCode() {
        return Id.hashCode();
    }
}
