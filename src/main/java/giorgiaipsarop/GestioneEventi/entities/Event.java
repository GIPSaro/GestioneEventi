package giorgiaipsarop.GestioneEventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private int capacity;
    @ManyToMany(mappedBy = "reservedEvents", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<User> participants = new HashSet<>();

    public Event(String title, String description, String date, String location, int capacity) {
        this.title = title;
        this.description = description;
        this.date = LocalDate.parse(date);
        this.location = location;
        this.capacity = capacity;

    }

    public void addParticipant(User user) {
        this.participants.add(user);
        user.getReservedEvents().add(this);


    }

    public void removeParticipant(User user) {
        this.participants.remove(user);
        user.getReservedEvents().remove(this);

    }

    public void setParticipant(User user) {
    }
}