package com.notesapp.backend;

import com.notesapp.backend.api.auth.data.RegisterRequest;
import com.notesapp.backend.api.auth.interfaces.AuthService;
import com.notesapp.backend.api.notes.data.NoteRequest;
import com.notesapp.backend.api.notes.interfaces.NoteService;
import com.notesapp.backend.entities.user.User;
import com.notesapp.backend.entities.user.interfaces.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}


	@Bean
	public CommandLineRunner createSampleData(AuthService authService,
											  UserRepository userRepository,
											  NoteService noteService) {
		return args -> {
			User user = createUser(authService, userRepository);
			User secondUser = createSecondUser(userRepository);

			createSampleNotes(user, noteService);
			createSampleNotes(secondUser, noteService);
		};
	}

	private static final String[] sampleNotes = new String[]{"First note of user %s",
			"Second note of user %s", "I like trains %s", "Lorem ipsum %s", "Argumentum %s bebechum",
			"A reaaaaaalllyyyy looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong %s nooooooooooooooooooooooooooooooooooooooooooooooooooooooooote\n"};

	private static final String CORRECT_PASSWORD = "Abc123456!";

	private static User createUser(AuthService authService, UserRepository userRepository) {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.email("user123@mail.pl")
				.firstName("User")
				.lastName("Userowski")
				.password(CORRECT_PASSWORD)
				.build();

		authService.register(registerRequest);

		return userRepository.findByEmail(registerRequest.email()).orElseThrow();
	}

	private static User createSecondUser(UserRepository userRepository) {
		User user = User.builder()
				.email("2user123@mail.pl")
				.firstName("Szymonek")
				.lastName("Bolec")
				.password(CORRECT_PASSWORD)
				.build();

		return userRepository.save(user);
	}

	private static void createSampleNotes(User user, NoteService noteService) {
		int count = sampleNotes.length;

		for (int i = 0; i < count; i++) {
			NoteRequest noteRequest = NoteRequest.builder()
					.title(String.format("Note no. %d", i))
					.text(String.format(sampleNotes[i], user.getEmail()))
					.build();
			noteService.save(noteRequest, user);
		}


	}
}
