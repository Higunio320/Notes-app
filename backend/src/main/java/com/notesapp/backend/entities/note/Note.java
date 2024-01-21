package com.notesapp.backend.entities.note;

import com.notesapp.backend.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_DATE_NULL;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_DATE_PAST;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TEXT_BLANK;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TEXT_MAX_SIZE;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TEXT_SIZE_MSG;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TITLE_BLANK;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TITLE_MAX_SIZE;
import static com.notesapp.backend.entities.note.constants.NoteConstants.NOTE_TITLE_SIZE_MSG;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = NOTE_TITLE_BLANK)
    @Size(max = NOTE_TITLE_MAX_SIZE, message = NOTE_TITLE_SIZE_MSG)
    private String title;

    @NotBlank(message = NOTE_TEXT_BLANK)
    @Size(max = NOTE_TEXT_MAX_SIZE, message = NOTE_TEXT_SIZE_MSG)
    private String text;

    @ManyToOne
    @ToString.Exclude
    @NotNull
    private User user;

    @PastOrPresent(message = NOTE_DATE_PAST)
    @NotNull(message = NOTE_DATE_NULL)
    private LocalDateTime lastEdit;
    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Note note = (Note) object;
        return getId() != null && Objects.equals(id, note.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
